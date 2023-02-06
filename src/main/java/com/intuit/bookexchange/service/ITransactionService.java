package com.intuit.bookexchange.service;

import com.intuit.bookexchange.model.Transaction;
import com.intuit.bookexchange.util.RequestStatus;

import java.util.List;

public interface ITransactionService {

    void addTransactionRequest(Integer borrowerId, Integer lenderUserId, Integer bookId, RequestStatus requestStatus);

    boolean updateTransactionRequest(Integer transactionId, RequestStatus requestStatus);

    List<Transaction> getPendingTransactionListForLender(Integer lenderId);

    Transaction getTransaction(Integer transactionId);

    Boolean updateTransaction(Transaction transaction, RequestStatus reject);
}
