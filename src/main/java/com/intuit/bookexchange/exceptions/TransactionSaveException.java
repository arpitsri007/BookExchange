package com.intuit.bookexchange.exceptions;

public class TransactionSaveException extends RuntimeException{
    public TransactionSaveException(String msg) {
        super(msg);
    }
}
