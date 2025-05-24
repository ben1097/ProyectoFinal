package model;

import java.time.LocalDate;

/**
 * Representa una membresía adquirida por un cliente para un vehículo específico.
 */
public class Membresia {
    private Vehiculo vehiculo;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;

    public Membresia(Vehiculo vehiculo, Cliente cliente, int meses, double montoMensual) {
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusMonths(meses);
        this.monto = meses * montoMensual;
    }
    // Verifica si la membresía está activa actualmente.
// Es decir, si la fecha actual está dentro del período de vigencia.

    public boolean estaActiva() {
        return LocalDate.now().isBefore(fechaFin);
    }

    // Getters y Setters

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
