package com.bulkgym.business;

import java.util.ArrayList;
import java.util.List;

import com.bulkgym.domain.Cliente;

public class ClienteBusiness {

	
	private List<Cliente> clientes = new ArrayList<>();

	
	/*
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente agregado: " + cliente.getNombre());
    }
    
    */
	
	//Agregar un nuevo cliente
	public void agregarCliente(Cliente cliente) {
	    boolean existe = false;
	    System.out.println("Intentando agregar cliente con ID: " + cliente.getIdCliente() + ", nombre: " + cliente.getNombre());
	    for (int i = 0; i < clientes.size(); i++) {
	        System.out.println("Cliente existente en la lista (ID): " + clientes.get(i).getIdCliente());
	        if (clientes.get(i).getIdCliente() == cliente.getIdCliente()) {
	            existe = true;
	            break;
	        }
	    }
	    if (!existe) {
	        clientes.add(cliente);
	        System.out.println("Cliente agregado manualmente: " + cliente.getNombre() + ". Tamaño de la lista ahora: " + clientes.size());
	    } else {
	        System.out.println("Ya existe un cliente con ID: " + cliente.getIdCliente() + ". No se agregó.");
	    }
	}


    //Obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clientes;
    }

    // Obtener cliente por ID
    public Cliente obtenerClientePorId(int idCliente) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getIdCliente() == idCliente) {
                return c;
            }
        }
        return null; // No encontrado
    }

    // Actualizar cliente 
    public boolean actualizarCliente(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getIdCliente() == clienteActualizado.getIdCliente()) {
                clientes.set(i, clienteActualizado);
                System.out.println("Cliente actualizado: " + clienteActualizado.getNombre());
                return true;
            }
        }
        return false; // No encontrado
    }

    //Eliminar cliente por ID 
    public boolean eliminarCliente(int idCliente) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getIdCliente() == idCliente) {
                clientes.remove(i);
                System.out.println("Cliente eliminado con ID: " + idCliente);
                return true;
            }
        }
        return false; // No encontrado
    }
}
