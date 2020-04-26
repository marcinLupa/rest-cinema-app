package com.cinema.infrastructure.controller;

import com.cinema.application.exceptions.MovieServiceException;
import com.cinema.application.exceptions.SecurityServiceException;
import com.cinema.application.exceptions.TicketServiceException;
import com.cinema.application.exceptions.ValidatorException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(MovieServiceException.class)
    public String handleMovieServiceException(MovieServiceException e) {
        return "MOVIE SERVICE EXCEPTION: " +  e.getMessage();
    }
    @ExceptionHandler(ValidatorException.class)
    public String handleValidatorException(ValidatorException e) {
        return "VALIDATOR EXCEPTION: " +  e.getMessage();
    }
    @ExceptionHandler(TicketServiceException.class)
    public String handleTicketServiceException(TicketServiceException e) {
        return "TICKET SERVICE EXCEPTION: " +  e.getMessage();
    }
    @ExceptionHandler(SecurityServiceException.class)
    public String handleSecurityServiceException(SecurityServiceException e) {
        return "SECURITY SERVICE EXCEPTION: " +  e.getMessage();
    }

}
