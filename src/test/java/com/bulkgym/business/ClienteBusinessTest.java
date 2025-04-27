package com.bulkgym.business;

import static org.junit.jupiter.api.Assertions.*;

import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

@SpringBootTest
public class ClienteBusinessTest {

    @Autowired
    private ClienteBusiness clienteBusiness;

    private Cliente crearCliente(int idPersona, String nombre) {
        Cliente c = new Cliente();
        c.setIdPersona(idPersona);
        c.setNombre(nombre);
        c.setApellidos("Apellido");
        c.setFechaNacimiento(Date.valueOf("2000-01-01"));
        c.setSexo('M');
        c.setTelefono("123456789");
        c.setCorreoElectronico("correo@test.com");
        c.setImagenRuta("ruta.png");
        c.setDireccion("Dirección prueba");
        c.setNombreContactoEmergencia("Contacto Emergencia");
        c.setTelContactoEmergencia("88888888");
        c.setActivo(true);
        return c;
    }

    private ClienteDTO crearClienteDTODesdeCliente(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdPersona(cliente.getIdPersona());
        dto.setNombre(cliente.getNombre());
        dto.setApellidos(cliente.getApellidos());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        dto.setSexo(cliente.getSexo());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreoElectronico(cliente.getCorreoElectronico());
        dto.setImagenRuta(cliente.getImagenRuta());
        dto.setDireccion(cliente.getDireccion());
        dto.setNombreContactoEmergencia(cliente.getNombreContactoEmergencia());
        dto.setTelContactoEmergencia(cliente.getTelContactoEmergencia());
        dto.setActivo(cliente.isActivo());
        return dto;
    }

    // ---- PRUEBAS PARA GUARDAR CLIENTES ----

    @Test
    public void testGuardarClienteNuevo() {
        Cliente c = crearCliente(2001, "Jose Pablo");
        clienteBusiness.guardarCliente(c);

        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();
        assertTrue(clientes.stream().anyMatch(cl -> cl.getNombre().equals("Jose Pablo")));
    }

    @Test
    public void testGuardarClienteDuplicado() {
        Cliente c1 = crearCliente(2002, "Carlos");
        clienteBusiness.guardarCliente(c1);

        Cliente c2 = crearCliente(2002, "Carlos Duplicado");
        Exception exception = assertThrows(Exception.class, () -> clienteBusiness.guardarCliente(c2));

        assertTrue(exception.getMessage().contains("Ya existe una persona registrada"));
    }

    @Test
    public void testGuardarVariosClientes() {
        clienteBusiness.guardarCliente(crearCliente(2003, "Cliente A"));
        clienteBusiness.guardarCliente(crearCliente(2004, "Cliente B"));
        clienteBusiness.guardarCliente(crearCliente(2005, "Cliente C"));

        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();
        assertTrue(clientes.size() >= 3);
    }

    // ---- PRUEBAS PARA ACTUALIZAR CLIENTES ----

    @Test
    public void testActualizarClienteExistente() {
        Cliente c = crearCliente(2006, "Cliente Actualizar");
        clienteBusiness.guardarCliente(c);

        ClienteDTO dto = crearClienteDTODesdeCliente(c);
        dto.setNombre("Cliente Actualizado Correctamente");

        boolean actualizado = clienteBusiness.actualizarCliente(dto);
        assertTrue(actualizado);
    }

    @Test
    public void testActualizarClienteInexistente() {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdPersona(9999); // id que no existe
        dto.setNombre("No Existe");

        boolean actualizado = clienteBusiness.actualizarCliente(dto);
        assertFalse(actualizado);
    }

    // ---- PRUEBAS PARA ELIMINAR CLIENTES ----

    @Test
    public void testEliminarClienteExistente() {
        Cliente c = crearCliente(2007, "Cliente Eliminar");
        clienteBusiness.guardarCliente(c);

        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();
        int idCliente = clientes.get(clientes.size() - 1).getIdCliente();

        boolean eliminado = clienteBusiness.eliminarCliente(idCliente);
        assertTrue(eliminado);
    }

    @Test
    public void testEliminarClienteInexistente() {
        boolean eliminado = clienteBusiness.eliminarCliente(9999);
        assertFalse(eliminado);
    }

    // ---- PRUEBAS PARA CONSULTAR CLIENTES ----

    @Test
    public void testObtenerTodosLosClientes() {
        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();
        assertNotNull(clientes);
    }

    @Test
    public void testContarClientes() {
        int cantidad = clienteBusiness.contarClientes();
        assertTrue(cantidad >= 0);
    }
}
