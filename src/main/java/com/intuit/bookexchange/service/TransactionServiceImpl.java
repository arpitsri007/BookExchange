package com.intuit.bookexchange.service;


import com.intuit.bookexchange.entity.TransactionEntity;
import com.intuit.bookexchange.exceptions.InvalidTransaction;
import com.intuit.bookexchange.model.Transaction;
import com.intuit.bookexchange.repository.ITransactionRepository;
import com.intuit.bookexchange.util.RequestStatus;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    public void addTransactionRequest(@NonNull Integer borrowerId, @NonNull Integer lenderUserId,
                                      @NonNull Integer bookId, @NonNull RequestStatus requestStatus) {


        Optional<TransactionEntity> transactionEntityOptional;
        transactionEntityOptional = transactionRepository.findByBorrowerIdAndBookId(borrowerId, bookId);
        if (transactionEntityOptional.isPresent()) {
            throw new InvalidTransaction("Request is already made for this book from this borrower");
        }


        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setBorrowerId(borrowerId);
        transactionEntity.setLenderId(lenderUserId);
        transactionEntity.setBookId(bookId);
        transactionEntity.setRequestStatus(requestStatus);

        transactionRepository.save(transactionEntity);
    }

    @Override
    public boolean updateTransactionRequest(@NonNull Integer transactionId,
                                            @NonNull RequestStatus requestStatus) {

        Optional<TransactionEntity> transactionEntityOptional;
        transactionEntityOptional = transactionRepository.findByTransactionId(transactionId);
        if (!transactionEntityOptional.isPresent()) {
            throw new InvalidTransaction();
        }
        TransactionEntity transactionEntity = transactionEntityOptional.get();

        transactionEntity.setRequestStatus(requestStatus);

        try {
            transactionRepository.save(transactionEntity);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<Transaction> getPendingTransactionListForLender(Integer lenderId) {

        List<Transaction> transactionList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = transactionRepository.findByLenderId(lenderId);

        for(TransactionEntity transactionEntity: transactionEntityList) {
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionEntity, transaction);
            transactionList.add(transaction);
        }

        return transactionList;
    }

    @Override
    public Transaction getTransaction(Integer transactionId) {

        Optional<TransactionEntity> transactionEntity;

        transactionEntity = transactionRepository.findByTransactionId(transactionId);

        if (transactionEntity == null) {
            throw new InvalidTransaction();
        }

        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionEntity, transaction);

        return transaction;
    }

    @Override
    public Boolean updateTransaction(Transaction transaction, RequestStatus reject) {

        Optional<TransactionEntity> transactionEntityOptional;

        transactionEntityOptional = transactionRepository.findByTransactionId(transaction.getTransactionId());

        if (!transactionEntityOptional.isPresent()) {
            throw new InvalidTransaction();
        }
        TransactionEntity transactionEntity = transactionEntityOptional.get();
        transactionEntity.setRequestStatus(RequestStatus.REJECT);

        try {
            transactionRepository.save(transactionEntity);
        } catch (Exception ex) {
            return false;
        }
        return true;

    }

}
