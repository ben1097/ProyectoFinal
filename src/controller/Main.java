package controller;

import model.*;
import java.util.*;

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
            System.out.println("13. Eliminar cliente");
            System.out.println("14. Mostrar cliente");
            System.out.println("15. Mostrar todos los clientes");
            System.out.println("16. Mostrar todos los vehiculos");
            System.out.println("17. Buscar cliente o vehículo");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> {
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
                }

                case 2 -> {
                    System.out.print("Cédula del cliente: ");
                    String ced = sc.nextLine();
                    Cliente c = parqueadero.buscarClientePorCedula(ced);
                    if (c == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    System.out.print("Tipo de vehículo (1=Auto, 2=Moto, 3=Camión): ");
                    int tipo;
                    try {
                        tipo = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Tipo inválido.");
                        break;
                    }

                    System.out.print("Placa: ");
                    String placa = sc.nextLine();
                    System.out.print("Color: ");
                    String color = sc.nextLine();
                    System.out.print("Modelo: ");
                    int modelo;
                    try {
                        modelo = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Modelo inválido.");
                        break;
                    }

                    Vehiculo v = switch (tipo) {
                        case 1 -> new Automovil(modelo, color, placa);
                        case 2 -> new Moto(modelo, color, placa);
                        case 3 -> new Camion(modelo, color, placa);
                        default -> null;
                    };

                    if (v != null) {
                        v.setPropietario(c);
                        parqueadero.registrarVehiculo(c.getCedula(), v);
                        System.out.println("Vehículo registrado.");
                    } else {
                        System.out.println("Tipo de vehículo no reconocido.");
                    }
                }

                case 3 -> {
                    System.out.print("Placa del vehículo temporal: ");
                    String placaTemp = sc.nextLine();
                    Vehiculo temporal = new VehiculoTemporal(placaTemp);

                    if (parqueadero.ingresarVehiculoTemporal(temporal)) {
                        System.out.println("Vehículo temporal ingresado.");
                    } else {
                        System.out.println("No se pudo ingresar (sin espacio disponible).");
                    }
                }

                case 4 -> {
                    System.out.print("Cédula del cliente: ");
                    String cedCliente = sc.nextLine();
                    Cliente clienteIng = parqueadero.buscarClientePorCedula(cedCliente);

                    if (clienteIng == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    System.out.print("Placa del vehículo registrado: ");
                    String placaReg = sc.nextLine();
                    Vehiculo vehiculoCliente = clienteIng.getVehiculos()
                            .stream()
                            .filter(v -> v.getPlaca().equalsIgnoreCase(placaReg))
                            .findFirst().orElse(null);

                    if (vehiculoCliente == null) {
                        System.out.println("El cliente no tiene un vehículo con esa placa.");
                    } else {
                        if (parqueadero.ingresarVehiculoTemporal(vehiculoCliente)) {
                            System.out.println("Vehículo registrado ingresado al parqueadero.");
                        } else {
                            System.out.println("No hay espacio disponible.");
                        }
                    }
                }

                case 5 -> {
                    System.out.print("Placa del vehículo: ");
                    String placaSalida = sc.nextLine();
                    Vehiculo vSalida = buscarPorPlaca(parqueadero, placaSalida);

                    if (vSalida != null) {
                        System.out.print("Horas de estadía (puede incluir decimales): ");
                        double horas;
                        try {
                            horas = Double.parseDouble(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Horas inválidas.");
                            break;
                        }
                        double tarifa = parqueadero.obtenerTarifa(vSalida);
                        double total = parqueadero.registrarSalida(vSalida, horas);
                        Factura.generarFacturaTemporal(parqueadero, vSalida, horas, tarifa);
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                }

                case 6 -> {
                    System.out.print("Placa del vehículo: ");
                    String placaM = sc.nextLine();
                    Vehiculo vM = buscarPorPlaca(parqueadero, placaM);
                    if (vM != null) {
                        System.out.print("Meses de membresía: ");
                        int meses;
                        try {
                            meses = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Cantidad inválida.");
                            break;
                        }
                        Cliente cli = vM.getPropietario();
                        if (cli != null && parqueadero.registrarMembresia(vM, cli, meses)) {
                            Membresia m = parqueadero.getMembresiaDeVehiculo(vM);
                            if (m != null) {
                                Factura.generarFacturaMembresia(parqueadero, m);
                            }
                        }
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                }

                case 7 -> parqueadero.mostrarReporteIngresosPorTipo();

                case 8 -> parqueadero.mostrarVehiculosEnParqueadero();

                case 9 -> {
                    System.out.print("Ingrese la cédula del cliente: ");
                    String cedHist = sc.nextLine();
                    parqueadero.mostrarHistorialVehiculosCliente(cedHist);
                }

                case 10 -> {
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
                }

                case 11 -> {
                    System.out.print("Ingrese la placa del vehículo a actualizar: ");
                    String placaMod = sc.nextLine();
                    System.out.print("Nuevo color: ");
                    String nuevoColor = sc.nextLine();
                    System.out.print("Nuevo modelo: ");
                    int nuevoModelo;
                    try {
                        nuevoModelo = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Modelo inválido.");
                        break;
                    }
                    if (parqueadero.actualizarVehiculo(placaMod, nuevoColor, nuevoModelo)) {
                        System.out.println("Vehículo actualizado con éxito.");
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                }

                case 12 -> parqueadero.mostrarClientesConMembresiasActivas();

                case 13 -> {
                    System.out.print("Ingrese la cédula del cliente a eliminar: ");
                    String cedulaEliminar = sc.nextLine();
                    if (parqueadero.eliminarCliente(cedulaEliminar)) {
                        System.out.println("Cliente eliminado con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar el cliente (puede no existir o tener vehículos dentro del parqueadero).");
                    }
                }

                case 14 -> {
                    System.out.print("Ingrese la cédula del cliente a mostrar: ");
                    String cedulaMostrar = sc.nextLine();
                    parqueadero.mostrarCliente(cedulaMostrar);
                }

                case 15 -> parqueadero.mostrarTodosLosClientes();

                case 16 -> parqueadero.mostrarTodosLosVehiculosRegistrados();

                case 17 -> {
                    System.out.println("Buscar por: ");
                    System.out.println("1. Cliente por cédula");
                    System.out.println("2. Cliente por nombre");
                    System.out.println("3. Cliente por teléfono");
                    System.out.println("4. Vehículo por placa");
                    System.out.print("Opción: ");
                    int subop = Integer.parseInt(sc.nextLine());
                    switch (subop) {
                        case 1 -> {
                            System.out.print("Cédula: ");
                            String ced = sc.nextLine();
                            Cliente c = parqueadero.buscarClientePorCedula(ced);
                            if (c != null) parqueadero.mostrarCliente(c.getCedula());
                            else System.out.println("Cliente no encontrado.");
                        }
                        case 2 -> {
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            List<Cliente> encontrados = parqueadero.buscarClientesPorNombre(nombre);
                            if (encontrados.isEmpty()) System.out.println("No se encontraron clientes.");
                            else encontrados.forEach(cli -> parqueadero.mostrarCliente(cli.getCedula()));
                        }
                        case 3 -> {
                            System.out.print("Teléfono: ");
                            String tel = sc.nextLine();
                            Cliente c = parqueadero.buscarClientePorTelefono(tel);
                            if (c != null) parqueadero.mostrarCliente(c.getCedula());
                            else System.out.println("Cliente no encontrado.");
                        }
                        case 4 -> {
                            System.out.print("Placa: ");
                            String placa = sc.nextLine();
                            Vehiculo v = parqueadero.buscarVehiculoPorPlaca(placa);
                            if (v != null) {
                                System.out.println("Placa: " + v.getPlaca() + ", Modelo: " + v.getModelo() + ", Color: " + v.getColor());
                                if (v.getPropietario() != null) {
                                    System.out.println("Propietario: " + v.getPropietario().getNombre());
                                }
                            } else {
                                System.out.println("Vehículo no encontrado.");
                            }
                        }
                        default -> System.out.println("Opción no válida.");
                    }
                }

                case 0 -> {
                    System.out.println("Saliendo...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static Vehiculo buscarPorPlaca(Parqueadero p, String placa) {
        return p.getVehiculos().stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst().orElse(null);
    }
}