package com.taxicooperativa.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Solicitud {

    private String id;
    private Zona origen;
    private Zona destino;
    private TipoServicio tipoServicio;
    private LocalDateTime fechaHora;
    private EstadoSolicitud estadoSolicitud;
    private Conductor conductorAsignado;
    private double tarifaEstimada;
    private int tiempoEstimado;

    //Constructor de la solicitud, se inicializa como EN_ESPERA hasta que se reciba un conductor
    public Solicitud(String id, Zona origen, Zona destino, TipoServicio tipoServicio){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.tipoServicio = tipoServicio;
        this.fechaHora = LocalDateTime.now();
        estadoSolicitud = EstadoSolicitud.EN_ESPERA;
        this.tarifaEstimada = 0;
        this.tiempoEstimado = 0;
    }

    public void asignarConductor(Conductor conductor, double tarifa, int tiempoMin){
        this.conductorAsignado = conductor;
        this.tarifaEstimada= tarifa;
        this.tiempoEstimado= tiempoMin;
        this.estadoSolicitud = EstadoSolicitud.EN_ATENCION;
    }

    public void cancelar(String motivo){
        this.estadoSolicitud = EstadoSolicitud.CANCELADA;
    }

    public void finalizar(){
        this.estadoSolicitud = EstadoSolicitud.FINALIZADA;
    }

    public String getId() {
        return id;
    }

    public Zona getOrigen() {
        return origen;
    }

    public Zona getDestino() {
        return destino;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Conductor getConductorAsignado() {
        return conductorAsignado;
    }

    public double getTarifaEstimada() {
        return tarifaEstimada;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Solicitud[" + id + "]" +
                " | " + origen + " → " + destino +
                " | " + tipoServicio.getNombre() +
                " | " + estadoSolicitud +
                " | " + fechaHora.format(formato) +
                (conductorAsignado != null ? " | Conductor: " + conductorAsignado.getNombre() : "") +
                (tarifaEstimada > 0 ? " | Tarifa: $" + String.format("%.0f", tarifaEstimada) : "");
    }
}
