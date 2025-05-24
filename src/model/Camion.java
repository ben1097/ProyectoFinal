package model;

public class Camion extends Vehiculo {
    public Camion(int modelo, String color, String placa ) {
        super (placa, color, modelo);
    }
    @Override
    public double pagarPorHora(int horas, double tarifa) {
        return horas * tarifa;
    }

}
