package com.intuit.bookexchange.exceptions;

public class InvalidTransaction extends RuntimeException{

    public InvalidTransaction(String msg) {
        super(msg);
    }

    public InvalidTransaction() {

    }
}
