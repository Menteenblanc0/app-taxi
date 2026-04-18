package com.taxicooperativa.model;

public class TipoMascotas extends TipoServicio{
    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*6000);
    }
}
