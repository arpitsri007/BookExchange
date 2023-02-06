package com.intuit.bookexchange.exceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
