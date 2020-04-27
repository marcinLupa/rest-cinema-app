package com.cinema.application.service;

public interface EmailService {
    void send(String recipient, String title, String message);
}
