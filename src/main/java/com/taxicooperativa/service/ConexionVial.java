package com.taxicooperativa.service;

public class ConexionVial {
    private double distanciaKm;
    private boolean habilitada;


    public ConexionVial(){

    }
    
    public void habilitar() {
        habilitada = true;
    }

    public void desabilitado(){
        habilitada = false;
    }

    public boolean estaHabilitada(){
        return habilitada;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }
}
