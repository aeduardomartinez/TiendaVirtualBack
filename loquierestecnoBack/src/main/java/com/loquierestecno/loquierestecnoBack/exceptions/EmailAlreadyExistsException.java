package com.loquierestecno.loquierestecnoBack.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String mensaje) {
        super(mensaje);
    }
}