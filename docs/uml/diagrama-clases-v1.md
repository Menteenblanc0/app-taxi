# Descripción de Casos de Uso — Sistema de Gestión de Taxis

---

## CU-01 · Registrar solicitud

| Campo | Detalle |
|---|---|
| **Actor principal** | Operador |
| **Precondición** | El operador está autenticado en el sistema. Las zonas de origen y destino existen en la red vial. |
| **Postcondición** | La solicitud queda registrada con estado `EN_ESPERA` en la cola del sistema. |

**Flujo principal**
1. Operador ingresa zona de origen, destino y tipo de servicio.
2. Sistema valida que los datos estén completos y las zonas existan.
3. Sistema crea la solicitud con fecha/hora actual y estado `EN_ESPERA`.
4. Sistema encola la solicitud y confirma el registro.

**Flujos alternos**
- Zona no existe → lanza `ZonaNoExisteException`, se notifica al operador.
- Datos incompletos → lanza `SolicitudInvalidaException`.

---

## CU-02 · Listar solicitudes en espera

| Campo | Detalle |
|---|---|
| **Actor principal** | Operador |
| **Precondición** | El operador está autenticado. |
| **Postcondición** | El sistema muestra la cola de solicitudes ordenada por llegada. |

**Flujo principal**
1. Operador solicita ver la cola de espera.
2. Sistema consulta `GestorSolicitudes` y retorna la lista en orden FIFO.
3. Se muestra id, zona origen/destino, tipo de servicio y hora de registro.

**Flujos alternos**
- Cola vacía → sistema informa que no hay solicitudes pendientes.

---

## CU-03 · Atender solicitud

| Campo | Detalle |
|---|---|
| **Actor principal** | Operador |
| **Precondición** | Existe al menos una solicitud `EN_ESPERA`. Hay conductores registrados en el sistema. |
| **Postcondición** | La solicitud pasa a `EN_ATENCION` con conductor, tarifa y tiempo estimado asignados. |

**Flujo principal**
1. Operador solicita atender la siguiente solicitud.
2. Sistema extrae la solicitud al frente de la cola.
3. Sistema invoca «include» CU-04 (Asignar conductor).
4. Sistema invoca «include» CU-05 (Calcular tarifa y ruta).
5. Sistema actualiza estado a `EN_ATENCION` y muestra resumen.

**Flujos alternos**
- Sin conductor disponible → notifica operador, solicitud vuelve a cola.
- Sin conectividad vial → lanza `SinConectividadVialException`.

---

## CU-04 · Asignar conductor

| Campo | Detalle |
|---|---|
| **Actor principal** | Sistema (invocado desde CU-03) |
| **Precondición** | Solicitud válida con tipo de servicio definido. |
| **Postcondición** | Se retorna un conductor disponible y habilitado para el tipo de servicio. |

**Flujo principal**
1. `AsignadorConductor` filtra conductores disponibles.
2. Verifica que el conductor esté habilitado para el tipo de servicio.
3. Verifica que exista ruta habilitada hacia la zona de origen.
4. Retorna el primer conductor válido encontrado.

**Flujos alternos**
- Conductor no habilitado para el tipo → lanza `ConductorNoHabilitadoException`.
- Sin ruta habilitada → lanza `SinConectividadVialException`.

---

## CU-05 · Calcular tarifa y ruta

| Campo | Detalle |
|---|---|
| **Actor principal** | Sistema (invocado desde CU-03) |
| **Precondición** | Existe ruta habilitada entre origen y destino. |
| **Postcondición** | Se calcula la distancia, tarifa estimada y tiempo de llegada. |

**Flujo principal**
1. `RedVial` calcula la distancia en km entre origen y destino.
2. `TipoServicio.calcularTarifa(distancia)` retorna la tarifa estimada.
3. Sistema estima tiempo de llegada según distancia.

**Flujos alternos**
- No hay ruta habilitada → lanza `SinConectividadVialException`.

---

## CU-06 · Cancelar solicitud

| Campo | Detalle |
|---|---|
| **Actor principal** | Operador |
| **Precondición** | La solicitud existe y está en estado `EN_ESPERA` o `EN_ATENCION`. |
| **Postcondición** | Solicitud pasa a `CANCELADA` con motivo registrado. Conductor queda disponible nuevamente. |

**Flujo principal**
1. Operador selecciona la solicitud e ingresa un motivo.
2. Sistema valida que la solicitud pueda cancelarse.
3. Sistema actualiza estado a `CANCELADA` y libera al conductor asignado.
4. Sistema registra la cancelación en el historial.

**Flujos alternos**
- Solicitud ya `FINALIZADA` → lanza `CancelacionInvalidaException`.
- Motivo vacío → sistema solicita justificación obligatoria.

---

## CU-07 · Finalizar servicio

| Campo | Detalle |
|---|---|
| **Actor principal** | Operador |
| **Precondición** | La solicitud está `EN_ATENCION` con conductor asignado. |
| **Postcondición** | Solicitud pasa a `FINALIZADA`. Conductor disponible. Registro guardado en historial y persistido. |

**Flujo principal**
1. Operador indica finalización del servicio.
2. Sistema actualiza estado a `FINALIZADA`.
3. Sistema libera al conductor y lo marca disponible.
4. Sistema guarda el registro en historial (include persistencia).

**Flujos alternos**
- Error al guardar → notifica fallo de persistencia; el estado lógico sí se actualiza.

---

## CU-08 · Reportar cierre vial

| Campo | Detalle |
|---|---|
| **Actor principal** | Conductor |
| **Precondición** | La conexión vial reportada existe en el sistema. |
| **Postcondición** | La conexión queda deshabilitada. Futuras asignaciones no usarán esa ruta. |

**Flujo principal**
1. Conductor reporta cierre indicando zona origen y destino de la conexión.
2. Sistema busca la `ConexionVial` correspondiente en `RedVial`.
3. Sistema la deshabilita y confirma el cambio.

**Flujos alternos**
- Conexión no existe → lanza `ZonaNoExisteException`.
- Conexión ya deshabilitada → sistema informa que ya está cerrada.
