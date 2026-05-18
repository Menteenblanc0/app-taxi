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
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Zona)) return false;
        Zona otra = (Zona) obj;
        return this.zonaId.equals(otra.zonaId);
    }

    @Override
    public int hashCode() {
        return zonaId.hashCode();
    }

    @Override
    public String toString() {
        return "Zona[" +zonaId+" - "+ zonaNombre+"]";
    }
}
