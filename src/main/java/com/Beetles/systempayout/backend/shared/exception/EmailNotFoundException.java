package com.Beetles.systempayout.backend.shared.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("Email: "+email+", não encontrado");
    }
}
