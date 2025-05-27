package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParqueaderoTest {

    private Parqueadero parqueadero;
    private Cliente cliente;
    private Vehiculo vehiculo;

    @BeforeEach
    public void setUp() {
        parqueadero = new Parqueadero(2, 2, 1, 1000, 2000, 3000);
        cliente = new Cliente("Ana", "123", "3100000000", "ana@email.com");
        vehiculo = new Automovil(2020, "Rojo", "ABC123");
        vehiculo.setPropietario(cliente);
        parqueadero.agregarCliente(cliente);
    }

    @Test
    public void testRegistrarVehiculoCorrecto() {
        boolean resultado = parqueadero.registrarVehiculo(cliente.getCedula(), vehiculo);
        assertTrue(resultado);
        assertEquals(1, cliente.getVehiculos().size());
    }

    @Test
    public void testRegistrarVehiculoClienteNoExiste() {
        boolean resultado = parqueadero.registrarVehiculo("999", vehiculo);
        assertFalse(resultado);
    }

    @Test
    public void testIngresarVehiculoTemporal_espacioDisponible() {
        parqueadero.registrarVehiculo(cliente.getCedula(), vehiculo);
        boolean resultado = parqueadero.ingresarVehiculoTemporal(vehiculo);
        assertTrue(resultado);
    }

    @Test
    public void testRegistrarSalidaCalculaPagoYLibera() {
        parqueadero.registrarVehiculo(cliente.getCedula(), vehiculo);
        parqueadero.ingresarVehiculoTemporal(vehiculo);
        double pago = parqueadero.registrarSalida(vehiculo, 2.5);  // 2.5 horas

        assertEquals(5000, pago); // 2.5 * 2000 (auto)
        assertEquals(0, parqueadero.vehiculos.size());
    }

    @Test
    public void testTieneMembresiaActiva() {
        parqueadero.registrarMembresia(vehiculo, cliente, 1);
        assertTrue(parqueadero.tieneMembresiaActiva(vehiculo));
    }

    @Test
    public void testCalcularTotalIngresos() {
        parqueadero.registrarVehiculo(cliente.getCedula(), vehiculo);
        parqueadero.ingresarVehiculoTemporal(vehiculo);
        parqueadero.registrarSalida(vehiculo, 1.5); // pago: 3000

        assertEquals(3000.0, parqueadero.calcularTotalIngresos());
    }
}
