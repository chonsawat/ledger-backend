package com.example.app.demo.Error;

public class ErrorRestResponseException extends RuntimeException {
    public ErrorRestResponseException (String message) {
        super(message);
    }
}
