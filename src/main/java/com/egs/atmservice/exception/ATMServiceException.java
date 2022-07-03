package com.egs.atmservice.exception;

public class ATMServiceException extends Exception {
    public ATMServiceException(String message) {
        super(message);
    }

    public ATMServiceException() {
        super();
    }
}
