package com.bulkgym.business;

import com.bulkgym.data.ClienteData;
import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteBusinessTest {

    @Mock
    private ClienteData clienteData;

    @InjectMocks
    private ClienteBusiness clienteBusiness;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Juan");
        cliente.setApellidos("Pérez");
        cliente.setActivo(true);

        clienteDTO = new ClienteDTO();
        clienteDTO.setIdPersona(1);
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellidos("Pérez");
        clienteDTO.setActivo(true);
    }

    @Test
    void testGuardarCliente() {
        clienteBusiness.guardarCliente(cliente);
        verify(clienteData, times(1)).guardarCliente(cliente);
    }

    @Test
    void testObtenerTodosLosClientes() {
        when(clienteData.obtenerTodosLosClientes()).thenReturn(Arrays.asList(cliente));

        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombre());
        verify(clienteData, times(1)).obtenerTodosLosClientes();
    }

    @Test
    void testObtenerClientePorId() {
        when(clienteData.buscarPorId(1)).thenReturn(cliente);

        Cliente resultado = clienteBusiness.obtenerClientePorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCliente());
        assertEquals("Juan", resultado.getNombre());
        verify(clienteData, times(1)).buscarPorId(1);
    }

    @Test
    void testActualizarCliente() {
        when(clienteData.actualizarCliente(clienteDTO)).thenReturn(true);

        boolean actualizado = clienteBusiness.actualizarCliente(clienteDTO);

        assertTrue(actualizado);
        verify(clienteData, times(1)).actualizarCliente(clienteDTO);
    }

    @Test
    void testEliminarCliente() {
        when(clienteData.eliminarCliente(1)).thenReturn(true);

        boolean eliminado = clienteBusiness.eliminarCliente(1);

        assertTrue(eliminado);
        verify(clienteData, times(1)).eliminarCliente(1);
    }

    
    @Test
    void testContarClientes() {
        when(clienteData.contarClientes()).thenReturn(5);

        int cantidad = clienteBusiness.contarClientes();

        assertEquals(5, cantidad);
        verify(clienteData, times(1)).contarClientes();
    }

    @Test
    void testContarClientesActivos() {
        when(clienteData.contarClientesActivos()).thenReturn(3);

        int cantidad = clienteBusiness.contarClientesActivos();

        assertEquals(3, cantidad);
        verify(clienteData, times(1)).contarClientesActivos();
    }

    @Test
    void testContarClientesInactivos() {
        when(clienteData.contarClientesInactivos()).thenReturn(2);

        int cantidad = clienteBusiness.contarClientesInactivos();

        assertEquals(2, cantidad);
        verify(clienteData, times(1)).contarClientesInactivos();
    }

    @Test
    void testObtenerClientesRecientes() {
        when(clienteData.obtenerClientesRecientes()).thenReturn(Arrays.asList(cliente));

        List<Cliente> recientes = clienteBusiness.obtenerClientesRecientes();

        assertNotNull(recientes);
        assertEquals(1, recientes.size());
        verify(clienteData, times(1)).obtenerClientesRecientes();
    }
}
