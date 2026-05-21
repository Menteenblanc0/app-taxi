package com.taxicooperativa.persistence;

import com.taxicooperativa.model.*;
import com.taxicooperativa.service.RedVial;
import com.taxicooperativa.service.TipoServicioFactory;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {

    private static final String ARCHIVO_CONDUCTORES = "data/conductores.txt";
    private static final String ARCHIVO_RED_VIAL = "data/red_vial.txt";
    private static final String ARCHIVO_SOLICITUDES = "data/solicitudes.txt";

    public static void guardarConductores(List<Conductor> conductores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CONDUCTORES))) {
            for (Conductor c : conductores) {
                StringBuilder tipos = new StringBuilder();
                for (TipoServicio t : c.getTiposHabilitados()) {
                    if (tipos.length() > 0) tipos.append(",");
                    tipos.append(t.getNombre());
                }
                bw.write(c.getNombre()+";"
                +c.getCedula()+";"
                +c.getPlaca()+";"
                +c.estaDisponible()+";"
                +tipos);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar conductores: " + e.getMessage());
        }
    }

    public static List<Conductor> cargarConductores() {
        List<Conductor> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_CONDUCTORES);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                String nombre = partes[0];
                String cedula = partes[1];
                String placa = partes[2];
                boolean disponible = Boolean.parseBoolean(partes[3]);
                String[] tipoArr = partes[4].split(",");

                List<TipoServicio> tipos = new ArrayList<>();
                for (String t : tipoArr) {
                    tipos.add(TipoServicioFactory.crear(t));
                }

                Conductor c = new Conductor(nombre,cedula,placa,tipos);
                c.setDisponible(disponible);
                lista.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar conductores: "+ e.getMessage());
        }
        return lista;
    }

    public static void guardarRedVial(RedVial red) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RED_VIAL))) {
            for (ConexionVial c : red.getConexiones()) {

                bw.write(c.getOrigen().getZonaId() + ";" +
                        c.getOrigen().getZonaNombre() + ";" +
                        c.getDestino().getZonaId() + ";" +
                        c.getDestino().getZonaNombre() + ";" +
                        c.getDistanciaKm() + ";" +
                        c.estaHabilitada());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar red vial: " + e.getMessage());
        }
    }

    public static RedVial cargarRedVial() {
        RedVial red = new RedVial();
        File archivo = new File(ARCHIVO_RED_VIAL);
        if (!archivo.exists()) return red;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(";");
                Zona origen  = new Zona(p[0], p[1]);
                Zona destino = new Zona(p[2], p[3]);
                double distancia  = Double.parseDouble(p[4]);
                boolean habilitada = Boolean.parseBoolean(p[5]);

                ConexionVial c = new ConexionVial(origen, destino, distancia);
                if (!habilitada) c.deshabilitar();

                red.agregarZona(origen);
                red.agregarZona(destino);
                red.agregarConexion(c);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar red vial: " + e.getMessage());
        }
        return red;
    }

    public static void guardarSolicitudes(List<Solicitud> solicitudes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_SOLICITUDES))) {
            for (Solicitud s : solicitudes) {
                // formato: id;origenId;destinoId;tipoServicio;estado;fechaHora;cedulaConductor;tarifa;tiempo
                String conductor = s.getConductorAsignado() != null ?
                        s.getConductorAsignado().getCedula() : "NINGUNO";
                bw.write(s.getId() + ";" +
                        s.getOrigen().getZonaId() + ";" +
                        s.getDestino().getZonaId() + ";" +
                        s.getTipoServicio().getNombre() + ";" +
                        s.getEstadoSolicitud() + ";" +
                        s.getFechaHora() + ";" +
                        conductor + ";" +
                        s.getTarifaEstimada() + ";" +
                        s.getTiempoEstimado());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar solicitudes: " + e.getMessage());
        }
    }

}
