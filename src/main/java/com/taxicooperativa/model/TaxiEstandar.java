package com.taxicooperativa.model;

public class TaxiEstandar extends TipoServicio{

    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*2500);
    }
}
