package com.taxicooperativa.ui;

import com.taxicooperativa.model.*;
import com.taxicooperativa.persistence.PersistenceManager;
import com.taxicooperativa.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static RedVial red;
    static GestorSolicitudes gestor;
    static AsignadorConductor asignador;
    static Operador operador;
    static int contadorSolicitudes = 1;

    public static void main(String[] args) {

        inicializarSistema();

        boolean activo = true;
        while (activo) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1": registrarSolicitud();     break;
                case "2": listarEnEspera();          break;
                case "3": atenderSolicitud();        break;
                case "4": cancelarSolicitud();       break;
                case "5": finalizarServicio();       break;
                case "6": reportarCierreVial();      break;
                case "7": verHistorial();            break;
                case "8": verConductores();          break;
                case "0": activo = false;            break;
                default:
                    System.out.println("Opción no válida.");
            }
            if (activo) pausa();
        }

        System.out.println("Sistema cerrado. ¡Hasta luego!");
        scanner.close();
    }

    // ═══════════════════════════════════════
    // INICIALIZACIÓN
    // ═══════════════════════════════════════

    static void inicializarSistema() {

        // cargar red vial desde archivo o crear datos de prueba
        red = PersistenceManager.cargarRedVial();
        if (red.getZonas().isEmpty()) {
            Zona z1 = new Zona("Z1", "Norte");
            Zona z2 = new Zona("Z2", "Este");
            Zona z3 = new Zona("Z3", "Oeste");
            Zona z4 = new Zona("Z4", "Sur");

            red.agregarZona(z1); red.agregarZona(z2);
            red.agregarZona(z3); red.agregarZona(z4);

            //Esto fue pensado en un sistema de vias de doble carril con la probabilidad de que una via se cierre, pero la otra siga abierta

            red.agregarConexion(new ConexionVial(z1, z2, 5.0));
            red.agregarConexion(new ConexionVial(z2, z1, 5.0));

            red.agregarConexion(new ConexionVial(z1, z3, 8.0));
            red.agregarConexion(new ConexionVial(z3, z1, 8.0));

            red.agregarConexion(new ConexionVial(z3, z4, 4.0));
            red.agregarConexion(new ConexionVial(z4, z3, 4.0));

            red.agregarConexion(new ConexionVial(z4, z1, 9.0));
            red.agregarConexion(new ConexionVial(z1, z4, 9.0));

            red.agregarConexion(new ConexionVial(z2, z4, 6.0));
            red.agregarConexion(new ConexionVial(z4, z2, 6.0));

            red.agregarConexion(new ConexionVial(z2, z3, 7.0));
            red.agregarConexion(new ConexionVial(z3, z2, 7.0));

            PersistenceManager.guardarRedVial(red);
            System.out.println("Red vial inicializada con datos de prueba.");
        }

        // cargar conductores o crear datos de prueba
        asignador = new AsignadorConductor();
        List<Conductor> conductores = PersistenceManager.cargarConductores();
        if (conductores.isEmpty()) {
            TipoServicio estandar  = TipoServicioFactory.crear("ESTANDAR");
            TipoServicio baul      = TipoServicioFactory.crear("BAUL");
            TipoServicio mascotas  = TipoServicioFactory.crear("MASCOTAS");


            //Aclaracion: La informacion de los conductores fue suministrada por mis amigos
            conductores = Arrays.asList(
                    new Conductor("Iza Jaimes",  "20077725", "123456",
                            Arrays.asList(estandar, baul)),
                    new Conductor("Orlando Vazques",     "11236789", "M1lf34",
                            Arrays.asList(estandar, mascotas)),
                    new Conductor("Cristiano Rolando",   "10458965", "VIH067",
                            Arrays.asList(baul, mascotas)),
                    new Conductor("Haru Urara",    "11301167", "NGU113",
                            Arrays.asList(estandar))
            );
            PersistenceManager.guardarConductores(conductores);
            System.out.println("Conductores inicializados con datos de prueba.");
        }
        for (Conductor c : conductores) asignador.agregarConductor(c);

        gestor   = new GestorSolicitudes();
        operador = new Operador("Operador Principal", "OP01", gestor, asignador, red);

        System.out.println("Sistema listo.\n");
    }

    // ═══════════════════════════════════════
    // MENÚ
    // ═══════════════════════════════════════

    static void mostrarMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   COOPERATIVA DE TAXIS — MULTIZONA   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Registrar solicitud              ║");
        System.out.println("║  2. Listar solicitudes en espera     ║");
        System.out.println("║  3. Atender siguiente solicitud      ║");
        System.out.println("║  4. Cancelar solicitud               ║");
        System.out.println("║  5. Finalizar servicio               ║");
        System.out.println("║  6. Reportar cierre vial             ║");
        System.out.println("║  7. Ver historial                    ║");
        System.out.println("║  8. Ver conductores                  ║");
        System.out.println("║  0. Salir                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Opción: ");
    }

    // ═══════════════════════════════════════
    // CASOS DE USO
    // ═══════════════════════════════════════

    // CU-01
    static void registrarSolicitud() {
        System.out.println("\n--- Registrar solicitud ---");
        System.out.println("Zonas disponibles:");
        for (Zona z : red.getZonas()) System.out.println("  " + z);

        System.out.print("ID zona origen: ");
        String origenId = scanner.nextLine().trim();
        System.out.print("ID zona destino: ");
        String destinoId = scanner.nextLine().trim();

        Zona origen  = buscarZona(origenId);
        Zona destino = buscarZona(destinoId);

        if (origen == null || destino == null) {
            System.out.println("Error: zona no encontrada.");
            return;
        }

        System.out.println("Tipos de servicio: ESTANDAR / BAUL / MASCOTAS");
        System.out.print("Tipo de servicio: ");
        String tipo = scanner.nextLine().trim();

        try {
            TipoServicio servicio = TipoServicioFactory.crear(tipo);
            String id = "SOL-" + String.format("%03d", contadorSolicitudes++);
            operador.registrarSolicitud(id, origen, destino, servicio);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // CU-02
    static void listarEnEspera() {
        System.out.println("\n--- Solicitudes en espera ---");
        operador.listarSolicitudesEnEspera();
    }

    // CU-03
    static void atenderSolicitud() {
        System.out.println("\n--- Atender siguiente solicitud ---");
        if (gestor.colaVacia()) {
            System.out.println("No hay solicitudes en espera.");
            return;
        }
        try {
            operador.atenderSolicitud();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // CU-04
    static void cancelarSolicitud() {
        System.out.println("\n--- Cancelar solicitud ---");
        System.out.print("ID de la solicitud: ");
        String id = scanner.nextLine().trim();
        System.out.println("Motivo: ");
        String motivo = scanner.nextLine().trim();
        if (motivo.isEmpty()) {
            System.out.println("Error: el motivo es obligatorio.");
            return;
        }
        try {
            operador.cancelarSolicitud(id, motivo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // CU-05
    static void finalizarServicio() {
        System.out.println("\n--- Finalizar servicio ---");

        // buscar solicitudes EN_ATENCION en el historial
        List<Solicitud> enAtencion = new ArrayList<>();
        for (Solicitud s : gestor.getHistorial()) {
            if (s.getEstadoSolicitud() == EstadoSolicitud.EN_ATENCION) {
                enAtencion.add(s);
            }
        }

        if (enAtencion.isEmpty()) {
            System.out.println("No hay solicitudes en atención.");
            return;
        }

        System.out.println("Solicitudes en atención:");
        for (Solicitud s : enAtencion) System.out.println("  " + s);

        System.out.print("ID de la solicitud a finalizar: ");
        String id = scanner.nextLine().trim();

        for (Solicitud s : enAtencion) {
            if (s.getId().equals(id)) {
                operador.finalizarServicio(s);
                return;
            }
        }
        System.out.println("No se encontró la solicitud.");
    }

    // CU-06
    static void reportarCierreVial() {
        System.out.println("\n--- Reportar cierre vial ---");
        System.out.println("Zonas disponibles:");
        for (Zona z : red.getZonas()) System.out.println("  " + z);
        System.out.print("ID zona origen de la conexión: ");
        String origenId  = scanner.nextLine().trim();
        System.out.print("ID zona destino de la conexión: ");
        String destinoId = scanner.nextLine().trim();
        Zona origen  = buscarZona(origenId);
        Zona destino = buscarZona(destinoId);
        if (origen == null || destino == null) {
            System.out.println("Error: zona no encontrada.");
            return;
        }
        try {
            operador.reportarCierreVial(origen, destino);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // CU-07
    static void verHistorial() {
        System.out.println("\n--- Historial de solicitudes ---");
        List<Solicitud> historial = gestor.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("El historial está vacío.");
            return;
        }
        for (Solicitud s : historial) System.out.println(s);
    }

    // CU-08
    static void verConductores() {
        System.out.println("\n--- Conductores registrados ---");
        for (Conductor c : asignador.getConductores()) {
            System.out.println(c);
        }
    }

    // ═══════════════════════════════════════
    // UTILIDADES
    // ═══════════════════════════════════════

    static Zona buscarZona(String id) {
        for (Zona z : red.getZonas()) {
            if (z.getZonaId().equalsIgnoreCase(id)) return z;
        }
        return null;
    }

    static void pausa() {
        System.out.print("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }
}