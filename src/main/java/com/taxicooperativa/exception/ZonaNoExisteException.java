package com.taxicooperativa.exception;

public class ZonaNoExisteException extends RuntimeException {
    public ZonaNoExisteException(String mensaje) {
        super(mensaje);
    }
}