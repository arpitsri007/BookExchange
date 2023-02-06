package com.intuit.bookexchange.controller;

import com.intuit.bookexchange.VO.BookSearchVO;
import com.intuit.bookexchange.VO.BookVO;
import com.intuit.bookexchange.VO.UserBookStoreVO;
import com.intuit.bookexchange.VO.UserBookUpdateVO;
import com.intuit.bookexchange.exceptions.InvalidAccessException;
import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.model.User;
import com.intuit.bookexchange.model.UserBookMap;
import com.intuit.bookexchange.service.IBookService;
import com.intuit.bookexchange.service.ITransactionService;
import com.intuit.bookexchange.service.IUserBookService;
import com.intuit.bookexchange.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserBookService userBookService;

    @Autowired
    private ITransactionService transactionService;

    // Registered user can use this API to add books
    @PostMapping("/addUserBookItem")
    public ResponseEntity<String> addUserBookItem(@RequestBody UserBookStoreVO request) {

        Integer userId = request.getUserId();

        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setTitle(request.getTitle());
        book.setLanguage(request.getLanguage());


        try {
            Book addedBook = bookService.addBook(book);
            userBookService.addUserBook(userId, addedBook.getBookId());
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Book entity created", HttpStatus.CREATED);
    }


    // Registered user can use this API to update listed books - whether exchangable/ non- exchangable
    @PostMapping("/updateBookExchangeStatus")
    public ResponseEntity<String> updateBookItems(@RequestBody UserBookUpdateVO request) {

        Integer updateReqBookId = request.getBookId();
        Integer userId = request.getUserId();
        Boolean isExchangeable = request.getIsExchangeable();

        User user;

        try {
            user = userService.searchUser(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Couldn't find User", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user == null) {
            return new ResponseEntity<>("UserId is invalid", HttpStatus.BAD_REQUEST);
        }

        // check if books belong to same user
        List<UserBookMap> userBookMapList = userBookService.searchUserBookIds(userId);
        List<Integer> userBookIdsList = userBookMapList.stream().map(id -> id.getBookId()).collect(Collectors.toList());

        if (userBookIdsList.contains(updateReqBookId)) {
            userBookService.updateBookExchangeStatus(userId, updateReqBookId, isExchangeable);
        } else {
            return new ResponseEntity<>("Book doesn't belong to this user", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Book entity updated", HttpStatus.ACCEPTED);
    }

    // GUEST USER use this API to view all available exchangable & non-exchangable both books
    @GetMapping("/searchBooks")
    public List<UserBookMap> searchAllBooks(@RequestBody BookSearchVO bookSearchRequest) {
        // borrowerId - is must - will be coming from UI
        // bookSearchRequest can be empty- search all books
        // bookSearchRequest - can have bookId then search for only that book in UserBookMapping tb.

        List<UserBookMap> userBookMapList = userBookService.searchRequestedBooks(bookSearchRequest);
        return userBookMapList;
    }

    @GetMapping("/searchBookbyUserId/{userid}")
    public List<UserBookMap> searchBookofUser(@PathVariable("userid") Integer userId) {

        List<UserBookMap> userBookMapList = userBookService.searchUserBookIds(userId);
        return userBookMapList;

    }

}
