package com.taxicooperativa.model;

public class TaxiBaul extends TipoServicio{

    public TaxiBaul() {
        super("BAUL", "Taxi con maletero ampliado");
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*4000);
    }
}
