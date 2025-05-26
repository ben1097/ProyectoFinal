package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Parqueadero {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();
    private int espaciosMoto;
    private int espaciosAuto;
    private int espaciosCamion;
    private double tarifaMoto;
    private double tarifaAuto;
    private double tarifaCamion;
    private int CapacidadMaxima;
    public ArrayList<Vehiculo> vehiculos;


    private String nombre = "Parqueadero sas";
    private String direccion = "Calle 123 #45-67";
    private String telefono = "3100000000";

    public String getDatosParqueadero() {
        return nombre + "\nDirección: " + direccion + "\nTeléfono: " + telefono;
    }
    // Busca y devuelve la membresía asociada a un vehículo, si existe
    public Membresia getMembresiaDeVehiculo(Vehiculo vehiculo) {
        for (Membresia m : membresias) {
            if (m.getVehiculo().equals(vehiculo)) {
                return m;
            }
        }
        return null;
    }

    // Muestra todos los vehículos de un cliente y si tienen membresía activa o vencida
    public void mostrarHistorialVehiculosCliente(String cedulaCliente) {
        Cliente cliente = buscarClientePorCedula(cedulaCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("=== Vehículos del cliente: " + cliente.getNombre() + " ===");

        if (cliente.getVehiculos().isEmpty()) {
            System.out.println("Este cliente no tiene vehículos registrados.");
            return;
        }

        for (Vehiculo v : cliente.getVehiculos()) {
            System.out.print("Placa: " + v.getPlaca() + " | Tipo: " + v.getClass().getSimpleName() +
                    " | Color: " + v.getColor() + " | Modelo: " + v.getModelo());

            // Verificar si tiene membresía activa o vencida
            Membresia m = getMembresiaDeVehiculo(v);
            if (m != null) {
                if (m.estaActiva()) {
                    System.out.println(" | Membresía: ACTIVA (hasta " + m.getFechaFin() + ")");
                } else {
                    System.out.println(" | Membresía: VENCIDA (venció el " + m.getFechaFin() + ")");
                }
            } else {
                System.out.println(" | Sin membresía.");
            }
        }
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



    private Parqueadero(int capacidadMaxima) {
        this.CapacidadMaxima = capacidadMaxima;
        this.vehiculos = new ArrayList<>();
    }
    public Parqueadero() {
        this.vehiculos = new ArrayList<>();
    }

    private List<Membresia> membresias = new ArrayList<>();

    public Parqueadero(int espaciosMoto, int espaciosAuto, int espaciosCamion,
                       double tarifaMoto, double tarifaAuto, double tarifaCamion) {
        this.espaciosMoto = espaciosMoto;
        this.espaciosAuto = espaciosAuto;
        this.espaciosCamion = espaciosCamion;
        this.tarifaMoto = tarifaMoto;
        this.tarifaAuto = tarifaAuto;
        this.tarifaCamion = tarifaCamion;
        this.vehiculos = new ArrayList<>();
    }

    // metodos

    // Agrega un cliente nuevo a la lista.
// No se hace validación de duplicados aún.
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
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



    //Calcula y muestra el total de ingresos separados por tipo de pago: membresias y pago temporales
    public void mostrarReporteIngresosPorTipo() {
        double totalMembresias = 0;
        double totalTemporales = 0;

        for (Pago p : pagos) {
            if (p.getTipoPago().equalsIgnoreCase("Membresia")) {
                totalMembresias += p.getMonto();
            } else if (p.getTipoPago().equalsIgnoreCase("Temporal")) {
                totalTemporales += p.getMonto();
            }
        }

        System.out.println("=== Reporte de Ingresos ===");
        System.out.println("Ingresos por membresías: $" + totalMembresias);
        System.out.println("Ingresos por pagos temporales: $" + totalTemporales);
        System.out.println("Total general: $" + (totalMembresias + totalTemporales));
    }


    // Busca un cliente por su cédula.
    // Si lo encuentra, lo retorna; si no, devuelve null.
    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) return c;
        }
        return null;
    }
    // Intenta asociar un vehículo a un cliente existente.
    // Retorna true si el cliente existe y se agrega el vehículo.
    public boolean registrarVehiculo(String cedulaCliente, Vehiculo vehiculo) {
        Cliente cliente = buscarClientePorCedula(cedulaCliente);
        if (cliente != null) {
            cliente.agregarVehiculo(vehiculo);
            return true;
        }
        return false;
    }

    // Permite el ingreso temporal de un vehículo al parqueadero.
    // Solo se admite si hay espacio disponible según el tipo.
    public boolean ingresarVehiculoTemporal(Vehiculo vehiculo) {
        if (espacioDisponible(vehiculo) && !vehiculos.contains(vehiculo)) {
            vehiculos.add(vehiculo);
            reducirEspacio(vehiculo);
            return true;
        }
        return false;
    }


    // Verifica si hay espacio libre para el tipo de vehículo que se quiere ingresar.
    public boolean espacioDisponible(Vehiculo v) {
        if (v instanceof Moto) return espaciosMoto > 0;
        if (v instanceof Automovil || v instanceof VehiculoTemporal) return espaciosAuto > 0;
        if (v instanceof Camion) return espaciosCamion > 0;
        return false;
    }

    // Resta un espacio disponible según el tipo de vehículo que ingresó.
    public void reducirEspacio(Vehiculo v) {
        if (v instanceof Moto) espaciosMoto--;
        if (v instanceof Automovil || v instanceof VehiculoTemporal) espaciosAuto--;
        if (v instanceof Camion) espaciosCamion--;
    }

    // Cuando un vehículo sale, se calcula lo que debe pagar, se registra el pago y se libera el espacio.
    public double registrarSalida(Vehiculo vehiculo, double horas) {
        double tarifa = obtenerTarifa(vehiculo);
        double monto = vehiculo.pagarPorHora(horas, tarifa);
        pagos.add(new Pago(vehiculo, monto, "Temporal"));
        liberarEspacio(vehiculo);
        vehiculos.remove(vehiculo);
        return monto;
    }

    // Retorna la tarifa por hora según el tipo de vehículo.
    public double obtenerTarifa(Vehiculo v) {
        if (v instanceof Moto || v instanceof VehiculoTemporal) return tarifaMoto;
        if (v instanceof Automovil) return tarifaAuto;
        if (v instanceof Camion) return tarifaCamion;
        return 0;
    }


    // Suma un espacio libre cuando un vehículo se retira del parqueadero.
    public void liberarEspacio(Vehiculo v) {
        if (v instanceof Moto) espaciosMoto++;
        if (v instanceof Automovil || v instanceof VehiculoTemporal) espaciosAuto++;
        if (v instanceof Camion) espaciosCamion++;
    }

    // Calcula cuánto dinero ha recaudado el parqueadero en total, sumando todos los pagos registrados.
    public double calcularTotalIngresos() {
        double total = 0;
        for (Pago p : pagos) {
            total += p.getMonto();
        }
        return total;
    }
    // Registra una membresía para un cliente y su vehículo si hay espacio.
// Calcula el monto total, descuenta el espacio y guarda el pago.

    public boolean registrarMembresia(Vehiculo vehiculo, Cliente cliente, int meses) {
        if (!espacioDisponible(vehiculo)) return false;

        double tarifaMensual = obtenerTarifa(vehiculo);
        Membresia m = new Membresia(vehiculo, cliente, meses, tarifaMensual);
        membresias.add(m);
        reducirEspacio(vehiculo);
        pagos.add(new Pago(vehiculo, m.getMonto(), "Membresia"));
        return true;
    }
    public boolean tieneMembresiaActiva(Vehiculo vehiculo) {
        for (Membresia m : membresias) {
            if (m.getVehiculo().equals(vehiculo) && m.estaActiva()) {
                return true;
            }
        }
        return false;


    }

    // Muestra la lista de vehículos actualmente dentro del parqueadero.
    public void mostrarVehiculosEnParqueadero() {
        System.out.println("=== Vehículos actualmente en el parqueadero ===");

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en este momento.");
            return;
        }

        for (Vehiculo v : vehiculos) {
            String tipo = v.getClass().getSimpleName();
            String propietario = (v.getPropietario() != null) ? v.getPropietario().getNombre() : "Temporal";
            System.out.println("Tipo: " + tipo +
                    " | Placa: " + v.getPlaca() +
                    " | Color: " + v.getColor() +
                    " | Modelo: " + v.getModelo() +
                    " | Propietario: " + propietario);
        }
    }

}
