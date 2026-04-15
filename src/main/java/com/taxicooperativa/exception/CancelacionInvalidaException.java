package com.taxicooperativa.exception;

public class CancelacionInvalidaException extends RuntimeException {
    public CancelacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}