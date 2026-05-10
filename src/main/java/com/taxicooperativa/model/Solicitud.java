package com.taxicooperativa.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Solicitud {

    private EstadoSolicitud estadoSolicitud;
    private String id;
    private LocalDateTime fechaHora;
    private int tarifaEstimada;
    private Conductor conductor;

    //Constructo de la solicitud, se inicializa como EN_ESPERA hasta que se reciba un conductor
    public Solicitud(String id, LocalDateTime fechaHora, int tarifaEstimada){
        this.id = id;
        this.tarifaEstimada = tarifaEstimada;

        this.fechaHora = LocalDateTime.now();
        estadoSolicitud = EstadoSolicitud.EN_ESPERA;
        //convierte a un formato mas bonito
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        fechaHora.format(formato);
    }

    public void asignarConductor(Conductor conductor){
        this.conductor = conductor;
        estadoSolicitud = EstadoSolicitud.EN_ATENCION;
    }

    public void cancelar(String motivo){
        estadoSolicitud = EstadoSolicitud.CANCELADA;
    }

    public void finalizar(){
        estadoSolicitud = EstadoSolicitud.FINALIZADA;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getId() {
        return id;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public int getTiempoEstimado() {
        return tarifaEstimada;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tarifaEstimada = tiempoEstimado;
    }

    public Conductor getConductor() {
        return conductor;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
