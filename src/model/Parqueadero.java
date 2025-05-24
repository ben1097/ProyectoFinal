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
}

