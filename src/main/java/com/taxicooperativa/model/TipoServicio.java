package com.taxicooperativa.model;

public abstract class TipoServicio {
    private String nombre;
    private String descripcion;

    public TipoServicio(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    // funcion abstracta con la que cada subclase calculara su tarifa
    public abstract double calcularTarifa(double distancia);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TipoServicio)) return false;
        TipoServicio otro = (TipoServicio) obj;
        return this.nombre.equals(otro.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
