package com.example.fcmtest.exception;

public class NoReceiverException extends RuntimeException{

    public NoReceiverException() {
    }

    public NoReceiverException(String message) {
        super(message);
    }
}
