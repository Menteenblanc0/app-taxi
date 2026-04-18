package com.taxicooperativa.model;

public class Zona {
    private String zonaId;
    private String zonaNombre;

    public Zona(String zonaId,String zonaNombre) {
        this.zonaId=zonaId;
        this.zonaNombre=zonaNombre;
    }

    public String getZonaId() {
        return zonaId;
    }

    public String getZonaNombre() {
        return zonaNombre;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
