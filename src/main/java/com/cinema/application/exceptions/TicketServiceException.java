package com.cinema.application.exceptions;

public class TicketServiceException  extends RuntimeException{
    public TicketServiceException(String message) {
        super(message);
    }
}
