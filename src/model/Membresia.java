package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa una membresía adquirida por un cliente para un vehículo específico.
 */
public class Membresia {
    private Vehiculo vehiculo;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;

    public Membresia(Cliente cliente, Vehiculo vehiculo, LocalDateTime inicio, LocalDateTime fin, double monto) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaInicio = inicio.toLocalDate();
        this.fechaFin = fin.toLocalDate();
        this.monto = monto;
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
