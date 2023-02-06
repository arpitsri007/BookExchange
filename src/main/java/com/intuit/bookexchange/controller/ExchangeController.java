package com.intuit.bookexchange.controller;

import com.intuit.bookexchange.VO.BookExchangeRequestVO;
import com.intuit.bookexchange.VO.TransactionVO;
import com.intuit.bookexchange.model.Transaction;
import com.intuit.bookexchange.service.IBookService;
import com.intuit.bookexchange.service.ITransactionService;
import com.intuit.bookexchange.service.IUserBookService;
import com.intuit.bookexchange.service.IUserService;
import com.intuit.bookexchange.util.ActivityType;
import com.intuit.bookexchange.util.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IUserBookService userBookService;

    @Autowired
    private IUserService userService;


    @GetMapping("/hi")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    // search book entities which are supposed to be exchanged
    @PostMapping("/requestBookExchange")
    public ResponseEntity<String> bookExchangeRequest(@RequestBody BookExchangeRequestVO bookExchangeRequest) {

        Integer borrowerReqBookId = bookExchangeRequest.getBorrowerReqBookId();
        Integer borrowerId = bookExchangeRequest.getBorrowerId();
        Integer lenderId = bookExchangeRequest.getLenderId();

        transactionService.addTransactionRequest(borrowerId, lenderId, borrowerReqBookId, RequestStatus.CREATED);

        return new ResponseEntity<>("Exchange Request placed Successfully", HttpStatus.ACCEPTED);
    }


    @GetMapping("/pendingExchangeRequest")
    public ResponseEntity<List<Transaction>> pendingExchangeRequest(@RequestBody BookExchangeRequestVO bookExchangeRequestVO) {

        List<Transaction> pendingTransactionListForLender = transactionService.getPendingTransactionListForLender(bookExchangeRequestVO.getLenderId());

        return new ResponseEntity<>(pendingTransactionListForLender, HttpStatus.OK);
    }


    @PostMapping("/exchangeItem")
    public ResponseEntity<String> exchangeBook(@RequestBody BookExchangeRequestVO bookRequest) {

        Integer lenderId = bookRequest.getLenderId();
        Integer borrowerId = bookRequest.getBorrowerId();
        Integer borrowerReqBookId = bookRequest.getBorrowerReqBookId();
        Integer lenderReqBookId = bookRequest.getLenderReqBookId();
        Integer transactionId = bookRequest.getPendingTransactionId();

        if (lenderReqBookId != null) {
            /*complete req in transaction table*/
            // books that borrower wants to sell and lender took it
            transactionService.addTransactionRequest(lenderId, borrowerId, lenderReqBookId, RequestStatus.APPROVED);

            // books that borrower wants to take and lender has given it
            transactionService.updateTransactionRequest(transactionId, RequestStatus.APPROVED);

            /*Change ownership of book in UserBookMap table*/
            userBookService.updateBookOwnerShip(borrowerId, lenderId, lenderReqBookId, borrowerReqBookId, ActivityType.EXCHANGE);

            /*Increase reward point for both the users*/
            userService.updateUserRewardPoint(borrowerId, 1);
            userService.updateUserRewardPoint(lenderId, 1);

        } else {
            /* else if lender has book which borrower is asking for
             approve the request for borrower and mark the entry for borrowerId-lenderId as APPROVED.
             change ownership of asked bookId in userBookMap table
             Increase reward point for lender and deduct reward point from borrower */
            transactionService.updateTransactionRequest(transactionId, RequestStatus.APPROVED);

            // ownership change for lender book only
            userBookService.updateBookOwnerShip(borrowerId, lenderId, lenderReqBookId, borrowerReqBookId, ActivityType.BORROW);

            // reward point logic
            userService.updateUserRewardPoint(borrowerId, -1);
            userService.updateUserRewardPoint(lenderId, 1);
        }

        return new ResponseEntity<>("Book is exchanged successfully", HttpStatus.OK);
    }


    @PostMapping("/reject")
    public ResponseEntity<String> rejectRequest(@RequestBody TransactionVO transactionVO) {

        Transaction transaction = transactionService.getTransaction(transactionVO.getTransactionId());

        transactionService.updateTransaction(transaction, RequestStatus.REJECT);

        return new ResponseEntity<>("Request is rejected", HttpStatus.OK);

    }


}
