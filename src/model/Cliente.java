package model;

import java.util.ArrayList;
import java.util.List;

public class  Cliente {
    private String nombre;
    private String cedula;
    private String telefono;
    private String correo;
    private List<Vehiculo> vehiculos = new ArrayList<>();



    public Cliente(String nombre, String cedula, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.cedula = cedula;
        this.correo = correo;

    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    // metodos

    // Agrega un vehículo a la lista de vehículos del cliente.
    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    // Elimina un vehículo del cliente según la placa.
// Si el cliente tiene varios vehículos, solo se quita el que coincide con la placa.
    public void eliminarVehiculo(String placa) {

        vehiculos.removeIf(v -> v.getPlaca().equals(placa));
    }



}
