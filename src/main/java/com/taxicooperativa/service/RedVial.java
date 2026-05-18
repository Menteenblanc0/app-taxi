package com.taxicooperativa.service;

import com.taxicooperativa.exception.SinConectividadVialException;
import com.taxicooperativa.exception.ZonaNoExisteException;
import com.taxicooperativa.model.ConexionVial;
import com.taxicooperativa.model.Zona;

import java.util.ArrayList;
import java.util.List;

public class RedVial {

    private List<Zona> zonas;
    private List<ConexionVial> conexiones;

    public RedVial() {
        this.zonas = new ArrayList<>();
        this.conexiones = new ArrayList<>();
    }

    public void agregarZona(Zona z) {
        if (!zonas.contains(z)) {
            zonas.add(z);
        }
    }

    public void agregarConexion(ConexionVial c) {
        if (!conexiones.contains(c)) {
            conexiones.add(c);
        }
    }

    public boolean existeRuta(Zona origen, Zona destino) {
        for (ConexionVial c : conexiones) {
            if (c.getOrigen().equals(origen) &&
                    c.getDestino().equals(destino) &&
                    c.estaHabilitada()) {
                return true;
            }
        }
        return false;
    }

    public double calcularDistancia(Zona origen, Zona destino) {
        for (ConexionVial c : conexiones) {
            if (c.getOrigen().equals(origen) &&
                    c.getDestino().equals(destino) &&
                    c.estaHabilitada()) {
                return c.getDistanciaKm();
            }
        }
        throw new SinConectividadVialException(
                "No hay ruta habilitada entre "
                        + origen + " y "
                        + destino);
    }

    public void deshabilitarConexion(Zona origen, Zona destino) {
        for (ConexionVial c : conexiones) {
            if (c.getOrigen().equals(origen) && c.getDestino().equals(destino)) {
                c.deshabilitar();
                return;
            }
        }
        throw new ZonaNoExisteException(
                "No existe conexion entre "
                        + origen + " y "
                        + destino
        );
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public List<ConexionVial> getConexiones() {
        return conexiones;
    }

}