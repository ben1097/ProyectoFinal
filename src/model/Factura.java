package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Factura {
    public static void generarFacturaTemporal(Parqueadero parqueadero, Vehiculo v, double horas, double tarifaPorHora) {
        LocalDateTime ahora = LocalDateTime.now();
        double monto = v.pagarPorHora(horas, tarifaPorHora);

        System.out.println("========== FACTURA TEMPORAL ==========");
        System.out.println(parqueadero.getDatosParqueadero());
        System.out.println("Fecha de salida: " + ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Vehículo: " + v.getClass().getSimpleName());
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Color: " + v.getColor());
        System.out.println("Modelo: " + v.getModelo());
        System.out.println("Propietario: " + (v.getPropietario() != null ? v.getPropietario().getNombre() : "Desconocido"));
        System.out.println("Horas: " + horas + " | Tarifa por hora: $" + tarifaPorHora);
        System.out.println("Total a pagar: $" + monto);
        System.out.println("======================================");
    }

    public static void generarFacturaMembresia(Parqueadero parqueadero, Membresia m) {
        System.out.println("========== FACTURA MEMBRESÍA ==========");
        System.out.println(parqueadero.getDatosParqueadero());
        System.out.println("Cliente: " + m.getCliente().getNombre());
        System.out.println("Vehículo: " + m.getVehiculo().getClass().getSimpleName() + " | Placa: " + m.getVehiculo().getPlaca());
        System.out.println("Fecha de inicio: " + m.getFechaInicio());
        System.out.println("Fecha de fin: " + m.getFechaFin());
        System.out.println("Total pagado: $" + m.getMonto());
        System.out.println("=======================================");
    }
}