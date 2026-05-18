package com.taxicooperativa.model;

public class ConexionVial {
    private double distanciaKm;
    private boolean habilitada;
    private Zona origen;
    private Zona destino;


    public ConexionVial(Zona origen, Zona destino,double distanciaKm){
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.habilitada= true;
    }

    public Zona getOrigen() {
        return origen;
    }

    public Zona getDestino() {
        return destino;
    }

    public void habilitar() {
        habilitada = true;
    }

    public void deshabilitar(){
        habilitada = false;
    }

    public boolean estaHabilitada(){
        return habilitada;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ConexionVial)) return false;
        ConexionVial otra = (ConexionVial) obj;
        return this.origen.equals(otra.origen) && this.destino.equals(otra.destino);
    }

    @Override
    public int hashCode() {
        return 31 * origen.hashCode() + destino.hashCode();
    }

    @Override
    public String toString() {
        return origen + " --> " + destino + " | " + distanciaKm +" Km" +" | " + (habilitada ? "Habilitada" : "Cerrada");
    }
}