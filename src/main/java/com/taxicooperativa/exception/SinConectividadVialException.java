package com.taxicooperativa.exception;

public class SinConectividadVialException extends RuntimeException {
    public SinConectividadVialException(String mensaje) {
        super(mensaje);
    }
}