package com.taxicooperativa.model;

import java.util.ArrayList;
import java.util.List;

public class Conductor {
    private String nombre;
    private String cedula;
    private String placa;
    private List<TipoServicio> tiposHabilitados;
    private boolean disponible = true;

    public Conductor(String nombre, String cedula, String placa,List<TipoServicio> tiposHabilitados){
        this.nombre = nombre;
        this.cedula = cedula;
        this.placa = placa;
        this.tiposHabilitados = tiposHabilitados;
    }

    public void estaDisponible(boolean d){
        disponible = d;
    }

    public boolean estaHabilitadoPara(TipoServicio t){
        boolean sw = false;
        for (TipoServicio tipoServicios : tiposHabilitados) {
            if (tipoServicios == t){
                sw = true;
                break;
            }
        }
        return sw;
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
}
