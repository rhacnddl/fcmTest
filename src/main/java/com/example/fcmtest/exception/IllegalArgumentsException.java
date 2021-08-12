package com.example.fcmtest.exception;

public class IllegalArgumentsException extends RuntimeException{

    public IllegalArgumentsException() {
    }

    public IllegalArgumentsException(String message) {
        super(message);
    }
}
