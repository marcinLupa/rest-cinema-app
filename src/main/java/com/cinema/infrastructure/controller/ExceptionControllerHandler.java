package com.cinema.infrastructure.controller;

import com.cinema.application.exceptions.MovieServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(MovieServiceException.class)
    public String handleMovieServiceException(MovieServiceException e) {
        return "MOVIE SERVICE EXCEPTION: " +  e.getMessage();
    }

}
