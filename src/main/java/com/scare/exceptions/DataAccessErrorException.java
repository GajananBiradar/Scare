package com.scare.exceptions;

public class DataAccessErrorException extends RuntimeException {
    public DataAccessErrorException(String message) {
        super(message);
    }
}