package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Parqueadero {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();
    private List<Membresia> membresias = new ArrayList<>();
    private List<Vehiculo> vehiculos = new ArrayList<>();

    private int espaciosMoto;
    private int espaciosAuto;
    private int espaciosCamion;
    private double tarifaMoto;
    private double tarifaAuto;
    private double tarifaCamion;

    private double ingresosTotales;
    private String nombre = "Parqueadero sas";
    private String direccion = "Calle 123 #45-67";
    private String telefono = "3100000000";

    public Parqueadero() {}

    public Parqueadero(int espaciosMoto, int espaciosAuto, int espaciosCamion,
                       double tarifaMoto, double tarifaAuto, double tarifaCamion) {
        this.espaciosMoto = espaciosMoto;
        this.espaciosAuto = espaciosAuto;
        this.espaciosCamion = espaciosCamion;
        this.tarifaMoto = tarifaMoto;
        this.tarifaAuto = tarifaAuto;
        this.tarifaCamion = tarifaCamion;
    }

    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    public String getDatosParqueadero() {
        return nombre + "\nDirección: " + direccion + "\nTeléfono: " + telefono;
    }

    public Membresia getMembresiaDeVehiculo(Vehiculo v) {
        for (Membresia m : membresias) {
            if (m.getVehiculo() != null && m.getVehiculo().equals(v)) {
                return m;
            }
        }
        return null;
    }

    public boolean actualizarCliente(String cedula, String nuevoNombre, String nuevoTelefono, String nuevoCorreo) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            cliente.setTelefono(nuevoTelefono);
            cliente.setCorreo(nuevoCorreo);
            return true;
        }
        return false;
    }

    public boolean actualizarVehiculo(String placa, String nuevoColor, int nuevoModelo) {
        for (Cliente cliente : clientes) {
            for (Vehiculo v : cliente.getVehiculos()) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    v.setColor(nuevoColor);
                    v.setModelo(nuevoModelo);
                    return true;
                }
            }
        }
        return false;
    }

    public void agregarCliente(Cliente cliente) {
        if (buscarClientePorCedula(cliente.getCedula()) == null) {
            clientes.add(cliente);
        } else {
            System.out.println("El cliente ya existe con esa cédula.");
        }
    }

    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equalsIgnoreCase(cedula)) return c;
        }
        return null;
    }
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        List<Cliente> encontrados = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(c);
            }
        }
        return encontrados;
    }
    public Cliente buscarClientePorTelefono(String telefono) {
        for (Cliente c : clientes) {
            if (c.getTelefono().equalsIgnoreCase(telefono)) {
                return c;
            }
        }
        return null;
    }


    public boolean eliminarCliente(String cedula) {
        // Buscar el cliente por cédula
        Cliente clienteAEliminar = buscarClientePorCedula(cedula);
        if (clienteAEliminar == null) {
            return false;
        }

        for (Vehiculo v : clienteAEliminar.getVehiculos()) {
          if (getVehiculos().contains(v)) {
                System.out.println("No se puede eliminar el cliente porque tiene vehículos dentro del parqueadero.");
                return false;
            }
        }


        return clientes.remove(clienteAEliminar);
    }
    public void mostrarCliente(String cedula) {
        Cliente c = buscarClientePorCedula(cedula);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("Nombre: " + c.getNombre());
        System.out.println("Cédula: " + c.getCedula());
        System.out.println("Teléfono: " + c.getTelefono());
        System.out.println("Correo: " + c.getCorreo());
        System.out.println("Vehículos registrados:");
        for (Vehiculo v : c.getVehiculos()) {
            System.out.println("  - " + v.getClass().getSimpleName() + " | Placa: " + v.getPlaca() + " | Modelo: " + v.getModelo() + " | Color: " + v.getColor());
        }
    }

    public void mostrarTodosLosClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println("-------------------------------------------------");
            System.out.println("Nombre: " + c.getNombre());
            System.out.println("Cédula: " + c.getCedula());
            System.out.println("Teléfono: " + c.getTelefono());
            System.out.println("Correo: " + c.getCorreo());
            System.out.println("Vehículos registrados:");
            if (c.getVehiculos().isEmpty()) {
                System.out.println("  (No tiene vehículos registrados)");
            } else {
                for (Vehiculo v : c.getVehiculos()) {
                    System.out.println("  - " + v.getClass().getSimpleName() + " | Placa: " + v.getPlaca() + " | Modelo: " + v.getModelo() + " | Color: " + v.getColor());
                }
            }
        }
        System.out.println("-------------------------------------------------");
    }


    public boolean registrarVehiculo(String cedulaCliente, Vehiculo vehiculo) {
        Cliente cliente = buscarClientePorCedula(cedulaCliente);
        if (cliente != null) {
            cliente.agregarVehiculo(vehiculo);
            return true;
        }
        return false;
    }

    public boolean ingresarVehiculoTemporal(Vehiculo vehiculo) {
        if (espacioDisponible(vehiculo) && !vehiculos.contains(vehiculo)) {
            vehiculos.add(vehiculo);
            reducirEspacio(vehiculo);
            return true;
        }
        return false;
    }
    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        for (Cliente c : clientes) {
            for (Vehiculo v : c.getVehiculos()) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    return v;
                }
            }
        }
        return null;
    }

    public void mostrarTodosLosVehiculosRegistrados() {
        boolean hayVehiculos = false;
        for (Cliente c : clientes) {
            for (Vehiculo v : c.getVehiculos()) {
                if (!hayVehiculos) {
                    System.out.println("==== Vehículos registrados ====");
                    hayVehiculos = true;
                }
                System.out.println("Placa: " + v.getPlaca() + " | Modelo: " + v.getModelo() +
                                   " | Color: " + v.getColor() + " | Tipo: " + v.getClass().getSimpleName() +
                                   " | Propietario: " + c.getNombre() + " (" + c.getCedula() + ")");
            }
        }
        if (!hayVehiculos) {
            System.out.println("No hay vehículos registrados.");
        }
    }

    public boolean espacioDisponible(Vehiculo v) {
        if (v instanceof Moto) return espaciosMoto > 0;
        if (v instanceof Automovil || v instanceof VehiculoTemporal) return espaciosAuto > 0;
        if (v instanceof Camion) return espaciosCamion > 0;
        return false;
    }

    public void reducirEspacio(Vehiculo v) {
        if (v instanceof Moto) espaciosMoto--;
        else if (v instanceof Automovil || v instanceof VehiculoTemporal) espaciosAuto--;
        else if (v instanceof Camion) espaciosCamion--;
    }

    public void liberarEspacio(Vehiculo v) {
        if (v instanceof Moto) espaciosMoto++;
        else if (v instanceof Automovil || v instanceof VehiculoTemporal) espaciosAuto++;
        else if (v instanceof Camion) espaciosCamion++;
    }

    public double registrarSalida(Vehiculo vehiculo, double horas) {
        double tarifa = obtenerTarifa(vehiculo);
        double monto = vehiculo.pagarPorHora(horas, tarifa);
        pagos.add(new Pago(vehiculo, monto, "Temporal"));
        liberarEspacio(vehiculo);
        vehiculos.remove(vehiculo);
        return monto;
    }

    public double obtenerTarifa(Vehiculo v) {
        if (v instanceof Moto || v instanceof VehiculoTemporal) return tarifaMoto;
        if (v instanceof Automovil) return tarifaAuto;
        if (v instanceof Camion) return tarifaCamion;
        return 0;
    }

    public boolean registrarMembresia(Vehiculo v, Cliente c, int opcion) {
        int meses;
        double precio;

        switch (opcion) {
            case 1: meses = 1; precio = 10000; break;
            case 2: meses = 3; precio = 27000; break;
            case 3: meses = 6; precio = 50000; break;
            default:
                System.out.println("Opción inválida para membresía.");
                return false;
        }

        if (v == null || c == null) {
            System.out.println("Vehículo o cliente no pueden ser nulos.");
            return false;
        }

        if (!espacioDisponible(v)) {
            System.out.println("No hay espacio disponible para este tipo de vehículo.");
            return false;
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusMonths(meses);
        Membresia m = new Membresia(c, v, inicio, fin, precio);
        membresias.add(m);
        pagos.add(new Pago(v, precio, "Membresia"));
        reducirEspacio(v);
        ingresosTotales += precio;
        return true;
    }



    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("=== LISTA DE CLIENTES ===");
        for (Cliente c : clientes) {
            System.out.println("Nombre: " + c.getNombre() + ", Cédula: " + c.getCedula() +
                               ", Tel: " + c.getTelefono() + ", Correo: " + c.getCorreo());
        }
    }

    public void mostrarVehiculosRegistrados() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        System.out.println("=== VEHÍCULOS REGISTRADOS ===");
        for (Vehiculo v : vehiculos) {
            System.out.println(v.getClass().getSimpleName() + " - Placa: " + v.getPlaca() +
                               ", Color: " + v.getColor() + ", Modelo: " + v.getModelo() +
                               ", Propietario: " + (v.getPropietario() != null ? v.getPropietario().getNombre() : "Temporal"));
        }
    }

    public void mostrarVehiculosEnParqueadero() {
        System.out.println("=== Vehículos actualmente en el parqueadero ===");
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en este momento.");
            return;
        }

        for (Vehiculo v : vehiculos) {
            String tipo = v.getClass().getSimpleName();
            String propietario = (v.getPropietario() != null) ? v.getPropietario().getNombre() : "Temporal";
            System.out.println("Tipo: " + tipo + " | Placa: " + v.getPlaca() + " | Color: " + v.getColor() +
                               " | Modelo: " + v.getModelo() + " | Propietario: " + propietario);
        }
    }

    public void mostrarClientesConMembresiasActivas() {
        System.out.println("=== Clientes con membresías activas o próximas a vencer ===");
        boolean alguno = false;
        for (Membresia m : membresias) {
            if (m.estaActiva()) {
                Cliente cliente = m.getCliente();
                Vehiculo vehiculo = m.getVehiculo();
                LocalDate fin = m.getFechaFin();
                long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fin);

                System.out.print("Cliente: " + cliente.getNombre() +
                        " | Vehículo: " + vehiculo.getPlaca() +
                        " (" + vehiculo.getClass().getSimpleName() + ")");

                if (diasRestantes <= 7) {
                    System.out.println(" --> ¡Por vencer en " + diasRestantes + " días!");
                } else {
                    System.out.println(" --> Vigente hasta " + fin);
                }
                alguno = true;
            }
        }
        if (!alguno) {
            System.out.println("No hay clientes con membresías activas.");
        }
    }

    public void mostrarReporteIngresosPorTipo() {
        double totalMembresias = 0;
        double totalTemporales = 0;

        for (Pago p : pagos) {
            if ("Membresia".equalsIgnoreCase(p.getTipoPago())) {
                totalMembresias += p.getMonto();
            } else if ("Temporal".equalsIgnoreCase(p.getTipoPago())) {
                totalTemporales += p.getMonto();
            }
        }

        System.out.println("=== Reporte de Ingresos ===");
        System.out.println("Ingresos por membresías: $" + totalMembresias);
        System.out.println("Ingresos por pagos temporales: $" + totalTemporales);
        System.out.println("Total general: $" + (totalMembresias + totalTemporales));
    }

    public double calcularTotalIngresos() {
        double total = 0;
        for (Pago p : pagos) {
            total += p.getMonto();
        }
        return total;
    }

    public String generarFacturaTemporalTexto(Vehiculo vSalida, double horas) {
        double tarifa = obtenerTarifa(vSalida);
        double monto = vSalida.pagarPorHora(horas, tarifa);
        return "Factura Temporal\n" +
               "Vehículo: " + vSalida.getPlaca() + "\n" +
               "Horas: " + horas + "\n" +
               "Monto a pagar: $" + monto;
    }

    public void mostrarHistorialVehiculosCliente(String cedula) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        List<Vehiculo> vehiculos = cliente.getVehiculos();
        if (vehiculos == null || vehiculos.isEmpty()) {
            System.out.println("Este cliente no ha registrado vehículos.");
        } else {
            System.out.println("Vehículos registrados por el cliente:");
            for (Vehiculo v : vehiculos) {
                System.out.println("- " + v.getClass().getSimpleName() +
                                   ", Placa: " + v.getPlaca() +
                                   ", Modelo: " + v.getModelo() +
                                   ", Color: " + v.getColor());
            }
        }
    }
}