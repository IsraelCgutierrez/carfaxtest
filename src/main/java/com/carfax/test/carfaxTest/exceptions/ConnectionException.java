package com.carfax.test.carfaxTest.exceptions;

public class ConnectionException extends Exception {
    public ConnectionException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
