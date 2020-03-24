package com.cinema.infrastructure.exceptions;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
