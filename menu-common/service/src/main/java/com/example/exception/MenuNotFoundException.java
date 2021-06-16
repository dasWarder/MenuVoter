package com.example.exception;

public class MenuNotFoundException extends EntityNotFoundException {

    public MenuNotFoundException(String message) {
        super(message);
    }

    public MenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
