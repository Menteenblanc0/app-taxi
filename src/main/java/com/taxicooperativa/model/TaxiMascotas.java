package com.taxicooperativa.model;

public class TaxiMascotas extends TipoServicio{
    public TaxiMascotas() {
        super("MASCOTAS", "Taxi habilitado para mascotas");
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 5000+(distancia*6000);
    }
}
