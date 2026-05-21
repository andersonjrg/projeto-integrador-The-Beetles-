package com.Beetles.systempayout.backend.shared.exception;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(UUID id) {
        super("Id: "+id+", não encontrado");
    }
}
