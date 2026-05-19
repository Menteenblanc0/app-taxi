package com.taxicooperativa.service;

import com.taxicooperativa.model.TaxiBaul;
import com.taxicooperativa.model.TaxiEstandar;
import com.taxicooperativa.model.TaxiMascotas;
import com.taxicooperativa.model.TipoServicio;

public class TipoServicioFactory {

    public static TipoServicio crear(String tipo){
        switch (tipo.toUpperCase()) {
            case "ESTANDAR": return new TaxiEstandar();
            case "BAUL": return new TaxiBaul();
            case "MASCOTAS": return new TaxiMascotas();
            default: throw new IllegalArgumentException("Tipo de servicio no reconocido: " + tipo);
        }
    }

}
