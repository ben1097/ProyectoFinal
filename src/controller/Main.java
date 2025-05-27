package controller;

import model.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero(5, 5, 2, 1000, 2000, 3000);

        while (true) {
            System.out.println("\n==== MENÚ PARQUEADERO ====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar vehículo");
            System.out.println("3. Ingresar vehículo temporal");
            System.out.println("4. Ingresar vehículo registrado");
            System.out.println("5. Salida de vehículo");
            System.out.println("6. Registrar membresía");
            System.out.println("7. Mostrar reporte ingresos");
            System.out.println("8. Mostrar vehículos actuales");
            System.out.println("9. Ver historial de vehículos por cliente");
            System.out.println("10. Actualizar cliente");
            System.out.println("11. Actualizar vehículo");
            System.out.println("12. Ver clientes con membresías activas o próximas a vencer");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Cédula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    Cliente cliente = new Cliente(nombre, cedula, tel, correo);
                    parqueadero.agregarCliente(cliente);
                    System.out.println("Cliente registrado.");
                    break;

                case 2:
                    System.out.print("Cédula del cliente: ");
                    String ced = sc.nextLine();
                    Cliente c = parqueadero.buscarClientePorCedula(ced);
                    if (c == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }
                    System.out.print("Tipo de vehículo (1=Auto, 2=Moto, 3=Camión): ");
                    int tipo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Placa: ");
                    String placa = sc.nextLine();
                    System.out.print("Color: ");
                    String color = sc.nextLine();
                    System.out.print("Modelo: ");
                    int modelo = sc.nextInt();
                    sc.nextLine();

                    Vehiculo v = null;
                    if (tipo == 1) v = new Automovil(modelo, color, placa);
                    if (tipo == 2) v = new Moto(modelo, color, placa);
                    if (tipo == 3) v = new Camion(modelo, color, placa);

                    if (v != null) {
                        v.setPropietario(c);
                        parqueadero.registrarVehiculo(c.getCedula(), v);
                        System.out.println("Vehículo registrado.");
                    }
                    break;

                case 3:
                    System.out.print("Placa del vehículo temporal: ");
                    String placaTemp = sc.nextLine();
                    Vehiculo temporal = new VehiculoTemporal(placaTemp);

                    if (parqueadero.ingresarVehiculoTemporal(temporal)) {
                        System.out.println("Vehículo temporal ingresado.");
                    } else {
                        System.out.println("No se pudo ingresar (sin espacio disponible).");
                    }
                    break;

                case 4:
                    System.out.print("Cédula del cliente: ");
                    String cedCliente = sc.nextLine();
                    Cliente clienteIng = parqueadero.buscarClientePorCedula(cedCliente);

                    if (clienteIng == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    System.out.print("Placa del vehículo registrado: ");
                    String placaReg = sc.nextLine();

                    Vehiculo vehiculoCliente = null;
                    for (Vehiculo ve : clienteIng.getVehiculos()) {
                        if (ve.getPlaca().equalsIgnoreCase(placaReg)) {
                            vehiculoCliente = ve;
                            break;
                        }
                    }

                    if (vehiculoCliente == null) {
                        System.out.println("El cliente no tiene un vehículo con esa placa.");
                    } else {
                        if (parqueadero.ingresarVehiculoTemporal(vehiculoCliente)) {
                            System.out.println("Vehículo registrado ingresado al parqueadero.");
                        } else {
                            System.out.println("No hay espacio disponible.");
                        }
                    }
                    break;

                case 5:
                    System.out.print("Placa del vehículo: ");
                    String placaSalida = sc.nextLine();
                    Vehiculo vSalida = buscarPorPlaca(parqueadero, placaSalida);
                    if (vSalida != null) {
                        System.out.print("Horas de estadía (puede incluir decimales): ");
                        double horas = sc.nextDouble(); // Cambiado a double
                        sc.nextLine();
                        double total = parqueadero.registrarSalida(vSalida, horas);
                        Factura.generarFacturaTemporal(parqueadero, vSalida, horas, parqueadero.obtenerTarifa(vSalida));
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;

                case 6:
                    System.out.print("Placa del vehículo: ");
                    String placaM = sc.nextLine();
                    Vehiculo vM = buscarPorPlaca(parqueadero, placaM);
                    if (vM != null) {
                        System.out.print("Meses de membresía: ");
                        int meses = sc.nextInt();
                        sc.nextLine();
                        Cliente cli = vM.getPropietario();
                        if (cli != null && parqueadero.registrarMembresia(vM, cli, meses)) {
                            Membresia m = parqueadero.getMembresiaDeVehiculo(vM);
                            if (m != null) {
                                Factura.generarFacturaMembresia(parqueadero, m);
                            }
                        }
                    }
                    break;

                case 7:
                    parqueadero.mostrarReporteIngresosPorTipo();
                    break;

                case 8:
                    parqueadero.mostrarVehiculosEnParqueadero();
                    break;

                case 9:
                    System.out.print("Ingrese la cédula del cliente: ");
                    String cedHist = sc.nextLine();
                    parqueadero.mostrarHistorialVehiculosCliente(cedHist);
                    break;

                case 10:
                    System.out.print("Ingrese la cédula del cliente a actualizar: ");
                    String cedMod = sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    String nuevoTel = sc.nextLine();
                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = sc.nextLine();
                    if (parqueadero.actualizarCliente(cedMod, nuevoNombre, nuevoTel, nuevoCorreo)) {
                        System.out.println("Cliente actualizado con éxito.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 11:
                    System.out.print("Ingrese la placa del vehículo a actualizar: ");
                    String placaMod = sc.nextLine();
                    System.out.print("Nuevo color: ");
                    String nuevoColor = sc.nextLine();
                    System.out.print("Nuevo modelo: ");
                    int nuevoModelo = sc.nextInt();
                    sc.nextLine();
                    if (parqueadero.actualizarVehiculo(placaMod, nuevoColor, nuevoModelo)) {
                        System.out.println("Vehículo actualizado con éxito.");
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;

                case 12:
                    parqueadero.mostrarClientesConMembresiasActivas();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    System.exit(0);
            }
        }
    }

    private static Vehiculo buscarPorPlaca(Parqueadero p, String placa) {
        for (Vehiculo v : p.vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }
}
