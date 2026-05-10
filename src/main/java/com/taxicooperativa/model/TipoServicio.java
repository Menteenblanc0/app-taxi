package com.taxicooperativa.model;

public abstract class TipoServicio {
    private String nombre;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    // funcion abstracta con la que cada subclase calculara su tarifa
    public abstract double calcularTarifa(double distancia);
}
