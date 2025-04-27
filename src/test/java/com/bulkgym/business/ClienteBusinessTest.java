package com.bulkgym.business;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bulkgym.domain.Cliente;

public class ClienteBusinessTest {

    private ClienteBusiness clienteBusiness;
/*
    @BeforeEach
    public void setUp() {
        clienteBusiness = new ClienteBusiness();
    }

    private Cliente crearCliente(int id, String nombre) {
        Cliente c = new Cliente();
        c.setIdCliente(id);
        c.setNombre(nombre);
        c.setActivo(true);
        c.setIdPersona(id);
        c.setApellidos("Apellido");
        c.setCorreoElectronico("correo@test.com");
        c.setFechaNacimiento(Date.valueOf("2000-01-01")); // Ejemplo para el 1 de enero de 2000        c.setImagenRuta("ruta.png");
        c.setTelefono("123456789");
        c.setSexo('M');
        return c;
    }

    // ----- Pruebas para agregarCliente -----

    @Test
    public void testAgregarClienteNuevo() {
        Cliente c = crearCliente(1, "Jose Pablo");
        clienteBusiness.agregarCliente(c);
        assertEquals(1, clienteBusiness.obtenerTodosLosClientes().size());
    }

    @Test
    public void testAgregarClienteDuplicado() {
        Cliente c1 = crearCliente(1, "Jose Pablo");
        Cliente c2 = crearCliente(1, "Carlos");
        clienteBusiness.agregarCliente(c1);
        clienteBusiness.agregarCliente(c2);
        assertEquals(1, clienteBusiness.obtenerTodosLosClientes().size());
    }

    @Test
    public void testAgregarVariosClientes() {
        clienteBusiness.agregarCliente(crearCliente(1, "Jose Pablo"));
        clienteBusiness.agregarCliente(crearCliente(2, "Carlos"));
        clienteBusiness.agregarCliente(crearCliente(3, "Viviana"));
        assertEquals(3, clienteBusiness.obtenerTodosLosClientes().size());
    }

    @Test
    public void testAgregarClienteConIdNegativo() {
        Cliente c = crearCliente(-1, "Negativo");
        clienteBusiness.agregarCliente(c);
        assertEquals(1, clienteBusiness.obtenerTodosLosClientes().size());
    }

    @Test
    public void testAgregarClienteConNombreVacio() {
        Cliente c = crearCliente(5, "");
        clienteBusiness.agregarCliente(c);
        assertEquals(1, clienteBusiness.obtenerTodosLosClientes().size());
    }

    // ----- Pruebas para obtenerClientePorId -----

    @Test
    public void testObtenerClienteExistentePorId() {
        Cliente c = crearCliente(1, "Jose Pablo");
        clienteBusiness.agregarCliente(c);
        assertNotNull(clienteBusiness.obtenerClientePorId(1));
    }

    @Test
    public void testObtenerClienteInexistentePorId() {
        assertNull(clienteBusiness.obtenerClientePorId(99));
    }

    @Test
    public void testObtenerClientePorIdDespuesDeEliminar() {
        Cliente c = crearCliente(1, "Jose Pablo");
        clienteBusiness.agregarCliente(c);
        clienteBusiness.eliminarCliente(1);
        assertNull(clienteBusiness.obtenerClientePorId(1));
    }

    @Test
    public void testObtenerClientePorIdNegativo() {
        assertNull(clienteBusiness.obtenerClientePorId(-1));
    }

    @Test
    public void testObtenerClientePorIdSinAgregarNinguno() {
        assertNull(clienteBusiness.obtenerClientePorId(1));
    }

    // ----- Pruebas para actualizarCliente -----

    @Test
    public void testActualizarClienteExistente() {
        Cliente c = crearCliente(1, "Jose Pablo");
        clienteBusiness.agregarCliente(c);

        Cliente actualizado = crearCliente(1, "Jose Pablo Modificado exitosamente");
        boolean actualizadoFlag = clienteBusiness.actualizarCliente(actualizado);

        assertTrue(actualizadoFlag);
        assertEquals("Jose Pablo Modificado exitosamente", clienteBusiness.obtenerClientePorId(1).getNombre());
    }

    @Test
    public void testActualizarClienteInexistente() {
        Cliente c = crearCliente(1, "Mayra");
        assertFalse(clienteBusiness.actualizarCliente(c));
    }

    @Test
    public void testActualizarClienteConIdNegativo() {
        Cliente c = crearCliente(-1, "Eduardo");
        assertFalse(clienteBusiness.actualizarCliente(c));
    }

    @Test
    public void testActualizarClienteConMismoObjeto() {
        Cliente c = crearCliente(1, "Juan");
        clienteBusiness.agregarCliente(c);
        assertTrue(clienteBusiness.actualizarCliente(c));
    }

    @Test
    public void testActualizarClienteEnListaVacia() {
        Cliente c = crearCliente(1, "Oscar");
        assertFalse(clienteBusiness.actualizarCliente(c));
    }

    // ----- Pruebas para eliminarCliente -----

    @Test
    public void testEliminarClienteExistente() {
        Cliente c = crearCliente(1, "Jose Pablo");
        clienteBusiness.agregarCliente(c);
        assertTrue(clienteBusiness.eliminarCliente(1));
        assertEquals(0, clienteBusiness.obtenerTodosLosClientes().size());
    }

    @Test
    public void testEliminarClienteInexistente() {
        assertFalse(clienteBusiness.eliminarCliente(10));
    }

    @Test
    public void testEliminarClienteDeListaVacia() {
        assertFalse(clienteBusiness.eliminarCliente(1));
    }

    @Test
    public void testEliminarClienteDosVeces() {
        Cliente c = crearCliente(1, "Juan");
        clienteBusiness.agregarCliente(c);
        clienteBusiness.eliminarCliente(1);
        assertFalse(clienteBusiness.eliminarCliente(1));
    }

    @Test
    public void testEliminarClienteConIdNegativo() {
        assertFalse(clienteBusiness.eliminarCliente(-1));
    }

    // ----- Pruebas para obtenerTodosLosClientes -----

    @Test
    public void testObtenerTodosLosClientesVacia() {
        List<Cliente> lista = clienteBusiness.obtenerTodosLosClientes();
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testObtenerTodosLosClientesConDatos() {
        clienteBusiness.agregarCliente(crearCliente(1, "A"));
        clienteBusiness.agregarCliente(crearCliente(2, "B"));
        List<Cliente> lista = clienteBusiness.obtenerTodosLosClientes();
        assertEquals(2, lista.size());
    }
    */
}