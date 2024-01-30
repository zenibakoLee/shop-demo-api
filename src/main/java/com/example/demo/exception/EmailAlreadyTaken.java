package com.example.demo.exception;

public class EmailAlreadyTaken extends RuntimeException {
    public EmailAlreadyTaken(String email) {
        super("email has already been taken: " + email);
    }
}
