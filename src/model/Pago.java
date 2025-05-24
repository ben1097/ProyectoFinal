package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pago {
    private Vehiculo vehiculo;
    private LocalDateTime fechaHora;
    private double monto;
    private String tipoPago; // "Membresia" o "Temporal"

    public Pago(Vehiculo vehiculo, double monto, String tipoPago) {
        this.vehiculo = vehiculo;
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.fechaHora = LocalDateTime.now();
    }

    // Getters
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipoPago() {
        return tipoPago;
    }
}

