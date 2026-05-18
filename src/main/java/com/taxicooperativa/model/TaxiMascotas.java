package com.taxicooperativa.model;

public class TaxiMascotas extends TipoServicio{
    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*6000);
    }
}
