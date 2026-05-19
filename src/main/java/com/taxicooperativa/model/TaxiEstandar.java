package com.taxicooperativa.model;

public class TaxiEstandar extends TipoServicio{

    public TaxiEstandar() {
        super("ESTANDAR", "Servicio estandar de taxi");
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*2500);
    }
}
