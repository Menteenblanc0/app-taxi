package com.taxicooperativa.service;

import com.taxicooperativa.model.Zona;

import java.util.List;

public class RedVial {

    private List<Zona> zonas;
    private List<ConexionVial> conexiones;

    public void agregarZona(Zona z){
        zonas.add(z);
    }

    public void agregarConexion(ConexionVial c){
        conexiones.add(c);
    }

    public boolean existeRuta(Zona o, Zona d) {

    }
}
