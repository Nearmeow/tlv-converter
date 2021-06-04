package com.mariakh.exception;

public class ConverterException extends Exception {

    private final String message;

    public ConverterException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
