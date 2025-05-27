package model;

public class VehiculoTemporal extends Vehiculo {

    public VehiculoTemporal(String placa) {
        super(placa, "N/A", 0); // Sin color ni modelo definidos
    }

    @Override
    public double pagarPorHora(double horas, double tarifa) {
        return horas * tarifa;
    }
}
