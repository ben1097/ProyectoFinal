package model;

public class Moto extends Vehiculo {
    public Moto(int modelo, String color, String placa) {
        super (placa, color, modelo);
    }
    @Override
    public double pagarPorHora(double horas, double tarifa) {
        return horas * tarifa;
    }

}
