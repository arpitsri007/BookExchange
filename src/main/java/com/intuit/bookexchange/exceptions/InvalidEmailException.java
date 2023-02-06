package com.intuit.bookexchange.exceptions;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String s) {
        super(s);
    }
}
