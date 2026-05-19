package com.taxicooperativa.service;

import com.taxicooperativa.exception.ConductorNoHabilitadoException;
import com.taxicooperativa.exception.SinConectividadVialException;
import com.taxicooperativa.model.Conductor;
import com.taxicooperativa.model.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class AsignadorConductor {

    private List<Conductor> conductores;

    public AsignadorConductor(){
        this.conductores = new ArrayList<>();
    }

    public void agregarConductor(Conductor c) {
        conductores.add(c);
    }

    public Conductor asignar(Solicitud solicitud, RedVial red) {
        if (!red.existeRuta(solicitud.getOrigen(), solicitud.getDestino())) {
            throw new SinConectividadVialException(
                    "No hay ruta habilitada entre "
                    + solicitud.getOrigen() +" y "
                    + solicitud.getDestino()
            );
        }

        for (Conductor c: conductores) {
            if (c.estaDisponible() &&
            c.estaHabilitadoPara(solicitud.getTipoServicio())) {
                double distancia = red.calcularDistancia(
                        solicitud.getOrigen(), solicitud.getDestino()
                );
                double tarifa = solicitud.getTipoServicio().calcularTarifa(distancia);
                int tiempoEstimado = (int) Math.ceil(distancia * 3); // se estima 3 min por km

                c.setDisponible(false);
                solicitud.asignarConductor(c,tarifa,tiempoEstimado);
                return c;
            }
        }

        throw new ConductorNoHabilitadoException(
                "No hay conductores disponibles para el servicio "
                        + solicitud.getTipoServicio().getNombre()
        );
    }

    public List<Conductor> getConductores() {
        return conductores;
    }

}
