package model;

public class Automovil extends Vehiculo {
    public Automovil(int modelo, String color, String placa) {
        super (placa, color, modelo);
    }
    @Override
    public double pagarPorHora(double horas, double tarifa) {
        return horas * tarifa;
    }

}
