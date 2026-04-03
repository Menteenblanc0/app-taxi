# Descripción de Casos de Uso — Sistema de Gestión de Taxis

## CU-01: Registrar Solicitud de Servicio

**Actor:** Usuario del sistema  
**Descripción:** Permite registrar una nueva solicitud de taxi indicando zona de origen, zona de destino, tipo de servicio y fecha/hora.  
**Precondiciones:** Las zonas de origen y destino deben existir en el sistema.  
**Flujo principal:**
1. El usuario ingresa zona origen, zona destino, tipo de servicio y fecha/hora.
2. El sistema valida que las zonas existan.
3. El sistema crea la solicitud y la agrega a la cola de espera (FIFO).
4. El sistema confirma el registro.

**Excepciones:** `ZonaNoExisteException`, `SolicitudInvalidaException`

---

## CU-02: Listar Cola de Espera

**Actor:** Usuario del sistema  
**Descripción:** Muestra todas las solicitudes pendientes ordenadas por orden de llegada (FIFO).  
**Precondiciones:** Ninguna.  
**Flujo principal:**
1. El usuario solicita ver la cola de espera.
2. El sistema muestra todas las solicitudes en orden de llegada con su información.

---

## CU-03: Cancelar Solicitud en Cola

**Actor:** Usuario del sistema  
**Descripción:** Permite cancelar una solicitud que aún se encuentra en la cola de espera, requiriendo una justificación.  
**Precondiciones:** La solicitud debe estar en estado pendiente.  
**Flujo principal:**
1. El usuario selecciona la solicitud a cancelar e ingresa una justificación.
2. El sistema verifica que la solicitud esté en cola.
3. El sistema cancela la solicitud y registra la justificación.

**Excepciones:** `CancelacionInvalidaException`

---

## CU-04: Asignar Conductor a Solicitud

**Actor:** Sistema (automático)  
**Descripción:** Asigna automáticamente el primer conductor disponible, habilitado para el tipo de servicio y con conectividad vial hacia la zona destino.  
**Precondiciones:** Debe haber al menos una solicitud en cola y un conductor disponible habilitado.  
**Flujo principal:**
1. El sistema toma la primera solicitud de la cola.
2. El sistema busca un conductor disponible habilitado para el tipo de servicio.
3. El sistema verifica conectividad vial desde la zona del conductor hasta la zona destino.
4. El sistema asigna el conductor y calcula la tarifa estimada.

**Excepciones:** `ConductorNoHabilitadoException`, `SinConectividadVialException`

---

## CU-05: Calcular Tarifa Estimada

**Actor:** Sistema (automático)  
**Descripción:** Calcula la tarifa estimada de un servicio según la distancia entre zonas y el tipo de taxi asignado (base $5.000 COP).  
**Precondiciones:** La solicitud debe tener un conductor asignado.  
**Flujo principal:**
1. El sistema obtiene la distancia entre zona origen y destino.
2. El sistema invoca `calcularTarifa(distancia)` sobre el `TipoServicio` correspondiente.
3. El sistema retorna la tarifa calculada.

---

## CU-06: Cerrar Servicio

**Actor:** Usuario del sistema  
**Descripción:** Cierra un servicio en curso, ya sea por finalización exitosa o por cancelación del usuario.  
**Precondiciones:** El servicio debe estar en estado activo (conductor asignado).  
**Flujo principal:**
1. El usuario selecciona el servicio activo y elige el motivo de cierre.
2. El sistema registra el estado final (COMPLETADO o CANCELADO), tiempos y tarifa.
3. El sistema libera al conductor.

---

## CU-07: Consultar Historial de Solicitudes

**Actor:** Usuario del sistema  
**Descripción:** Muestra el historial de todas las solicitudes con su estado final, conductor asignado, tiempos y tarifa.  
**Precondiciones:** Ninguna.  
**Flujo principal:**
1. El usuario solicita ver el historial.
2. El sistema muestra todas las solicitudes cerradas con información completa.

---

## CU-08: Reportar Cierre Vial

**Actor:** Conductor  
**Descripción:** Permite a un conductor reportar un cierre vial en una ruta específica, lo que tiene efecto inmediato en las asignaciones futuras.  
**Precondiciones:** La ruta debe existir en el grafo de conectividad.  
**Flujo principal:**
1. El conductor reporta el cierre de una ruta entre dos zonas.
2. El sistema deshabilita esa conexión en el grafo de conectividad vial.
3. Las asignaciones futuras ya no considerarán esa ruta como disponible.
