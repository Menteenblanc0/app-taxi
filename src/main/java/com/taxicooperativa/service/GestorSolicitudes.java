package com.taxicooperativa.service;

import com.taxicooperativa.exception.CancelacionInvalidaException;
import com.taxicooperativa.exception.SolicitudInvalidaException;
import com.taxicooperativa.model.Conductor;
import com.taxicooperativa.model.EstadoSolicitud;
import com.taxicooperativa.model.Solicitud;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorSolicitudes {

    private Queue<Solicitud> cola;
    private List<Solicitud> historial;

    public GestorSolicitudes() {
        this.cola = new LinkedList<>();
        this.historial = new ArrayList<>();
    }

    public void recibirSolicitud(Solicitud s) {
        if (s == null) {
            throw new SolicitudInvalidaException("La solicitud no puede ser nula");
        }
        cola.add(s);
    }

    public Solicitud siguienteSolicitud() {
        if (cola.isEmpty()) {
            throw new SolicitudInvalidaException("No hay solicitudes en espera");
        }
        return cola.poll();
    }

    public void cancelarSolicitud(String id, String motivo) {
        for (Solicitud s : cola) {
            if (s.getId().equals(id)) {
                s.cancelar(motivo);
                if (s.getConductorAsignado() != null) {
                    s.getConductorAsignado().setDisponible(true);
                }
                cola.remove(s);
                historial.add(s);
                return;
            }
        }

        for (Solicitud s : historial) {
            if (s.getId().equals(id)) {
                if (s.getEstadoSolicitud() == EstadoSolicitud.FINALIZADA) {
                    throw new CancelacionInvalidaException(
                            "No se puede cancelar una solicitud ya finalizada."
                    );
                }
                if (s.getEstadoSolicitud() == EstadoSolicitud.CANCELADA) {
                    throw new CancelacionInvalidaException(
                            "La solicitud ya está cancelada."
                    );
                }
                s.cancelar(motivo);
                if (s.getConductorAsignado() != null) {
                    s.getConductorAsignado().setDisponible(true);
                }
                return;
            }
        }
        throw new SolicitudInvalidaException(
                "No se encontró la solicitud con id: " + id
        );
    }

    public void finalizarSolicitud(Solicitud s) {
        s.finalizar();
        if (s.getConductorAsignado() != null) {
            s.getConductorAsignado().setDisponible(true);
        }
        historial.add(s);
    }

    public List<Solicitud> listarEnEspera() {
        return new ArrayList<>(cola);
    }

    public List<Solicitud> getHistorial() {
        return historial;
    }

    public boolean colaVacia() {
        return cola.isEmpty();
    }

    public void devolverSolicitud(Solicitud s) {
        ((LinkedList<Solicitud>) cola).addFirst(s); // la pone de vuelta al frente
    }

}