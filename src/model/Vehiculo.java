package model;

public abstract class Vehiculo {
    private String color;
    private String placa;
    private int modelo;
    private Cliente propietario;

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }


    public Vehiculo(String placa, String color, int modelo){
        this.placa = placa;
        this.color = color;
        this.modelo = modelo;

    }
    // Este metodo lo implementan las subclases.
// Sirve para calcular cuánto debe pagar un vehículo según las horas y la tarifa.
    public abstract double pagarPorHora(double horas, double tarifa);



    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public int getModelo() {
        return modelo;
    }
    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

}