package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.ClienteData;
import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;

@Service
public class ClienteBusiness {

    @Autowired
    private ClienteData clienteData;

    public void guardarCliente(Cliente cliente) {
        clienteData.guardarCliente(cliente);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteData.obtenerTodosLosClientes();
    }

    public Cliente obtenerClientePorId(int idCliente) {
        return clienteData.buscarPorId(idCliente);
    }

    public boolean actualizarCliente(ClienteDTO clienteDTO) {
        return clienteData.actualizarCliente(clienteDTO);
    }

    public boolean eliminarCliente(int idCliente) {
        return clienteData.eliminarCliente(idCliente);
    }
}
