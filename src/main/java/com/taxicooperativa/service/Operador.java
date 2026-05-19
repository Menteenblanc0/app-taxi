package com.taxicooperativa.service;

import com.taxicooperativa.model.Conductor;
import com.taxicooperativa.model.Solicitud;
import com.taxicooperativa.model.Zona;
import com.taxicooperativa.model.TipoServicio;
import com.taxicooperativa.persistence.PersistenceManager;

import java.util.List;

public class Operador {
    private String nombre;
    private String id;
    private GestorSolicitudes gestor;
    private AsignadorConductor asignador;
    private RedVial red;

    public Operador(String nombre, String id, GestorSolicitudes gestor, AsignadorConductor asignador, RedVial red) {
        this.nombre   = nombre;
        this.id       = id;
        this.gestor   = gestor;
        this.asignador = asignador;
        this.red      = red;
    }

    public void registrarSolicitud(String idSolicitud, Zona origen, Zona destino, TipoServicio tipo) {
        Solicitud s = new Solicitud(idSolicitud, origen, destino, tipo);
        gestor.recibirSolicitud(s);
        System.out.println("Solicitud registrada: " + s);
    }

    public void listarSolicitudesEnEspera() {
        List<Solicitud> lista = gestor.listarEnEspera();
        if (lista.isEmpty()) {
            System.out.println("No hay solicitudes en espera.");
            return;
        }
        System.out.println("--- Solicitudes en espera (" + lista.size() + ") ---");
        for (Solicitud s : lista) {
            System.out.println(s);
        }
    }

    public void atenderSolicitud() {
        Solicitud s = gestor.siguienteSolicitud();
        try {
            Conductor c = asignador.asignar(s, red);
            gestor.getHistorial().add(s);
            System.out.println("Solicitud atendida:");
            System.out.println("  " + s);
            System.out.println("  Conductor asignado: " + c);
            PersistenceManager.guardarSolicitudes(gestor.getHistorial());
        } catch (Exception e) {
            gestor.devolverSolicitud(s);
            throw e;
        }
    }

    public void cancelarSolicitud(String id, String motivo){
        gestor.cancelarSolicitud(id, motivo);
        System.out.println("Solicitud " + id + " cancelada. Motivo: " + motivo);
        PersistenceManager.guardarSolicitudes(gestor.getHistorial());
    }

    public void finalizarServicio(Solicitud s) {
        gestor.finalizarSolicitud(s);
        System.out.println("Servicio finalizado: " + s);
        PersistenceManager.guardarSolicitudes(gestor.getHistorial());
    }

    public void reportarCierreVial(Zona origen, Zona destino) {
        red.deshabilitarConexion(origen, destino);
        System.out.println("Cierre vial registrado: " + origen + " → " + destino);
        PersistenceManager.guardarRedVial(red);
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }
}