package com.bulkgym.restcontroller;

import com.bulkgym.data.ClienteData;
import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;
import com.bulkgym.dto.RespuestaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteData clienteData;

    // 🔥 1. CREAR (ahora recibe ClienteDTO)
    @PostMapping
    public ResponseEntity<RespuestaDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = mapearDtoACliente(clienteDTO);
        clienteData.guardarCliente(cliente);
        return ResponseEntity.ok(new RespuestaDTO("Cliente guardado con éxito."));
    }

    // 🔥 2. PING (no se toca)
    @GetMapping("/ping")
    public String ping() {
        return "funciona";
    }

    // 🔥 3. LISTAR (devuelve lista de clientes normales)
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteData.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    // 🔥 4. ELIMINAR (igual, no cambia)
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarCliente(@PathVariable int id) {
        boolean eliminado = clienteData.eliminarCliente(id);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Cliente eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Cliente no encontrado."));
        }
    }

 // 🔥 5. ACTUALIZAR (Endpoint corregido)
 @PutMapping("/{id}")
 public ResponseEntity<RespuestaDTO> actualizarCliente(@PathVariable int id, @RequestBody ClienteDTO clienteDto) {
	    clienteDto.setIdPersona(id); // se asegura del ID
	    boolean actualizado = clienteData.actualizarCliente(clienteDto);
	    if (actualizado) {
	        return ResponseEntity.ok(new RespuestaDTO("Cliente actualizado con éxito."));
	    } else {
	        return ResponseEntity.status(404).body(new RespuestaDTO("No se pudo actualizar el cliente."));
	    }
	}


    // 🔥 6. Función privada para mapear de DTO a Cliente
    private Cliente mapearDtoACliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdPersona(dto.getIdPersona());
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setSexo(dto.getSexo());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreoElectronico(dto.getCorreoElectronico());
        cliente.setImagenRuta(dto.getImagenRuta());
        cliente.setDireccion(dto.getDireccion());
        cliente.setNombreContactoEmergencia(dto.getNombreContactoEmergencia());
        cliente.setTelContactoEmergencia(dto.getTelContactoEmergencia());
        cliente.setActivo(dto.isActivo());
        return cliente;
    }
}
