package com.taxicooperativa.model;

public class TaxiBaul extends TipoServicio{

    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*4000);
    }
}
