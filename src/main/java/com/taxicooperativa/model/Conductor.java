package com.taxicooperativa.model;

import java.util.ArrayList;
import java.util.List;

public class Conductor {
    private String nombre;
    private String cedula;
    private String placa;
    private List<TipoServicio> tiposHabilitados;
    private boolean disponible;

    public Conductor(String nombre, String cedula, String placa,List<TipoServicio> tiposHabilitados){
        this.nombre = nombre;
        this.cedula = cedula;
        this.placa = placa;
        this.tiposHabilitados = tiposHabilitados;
        this.disponible = true;
    }

    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }

    public boolean estaDisponible(){
        return disponible;
    }

    public boolean estaHabilitadoPara(TipoServicio tipo){
        for (TipoServicio t : tiposHabilitados) {
            if (t.equals(tipo)) return true;
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPlaca() {
        return placa;
    }

    @Override
    public String toString() {
        return "Conductor[" + cedula + " - " + nombre +
                " | Placa: " + placa +
                " | " +
                (disponible ? "Disponible" : "Ocupado") +
                "]";
    }

    public List<TipoServicio> getTiposHabilitados() {
        return tiposHabilitados;
    }
}
