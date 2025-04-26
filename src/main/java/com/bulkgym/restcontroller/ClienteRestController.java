package com.bulkgym.restcontroller;

import com.bulkgym.business.ClienteBusiness;
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
    private ClienteBusiness clienteBusiness;

    @PostMapping
    public ResponseEntity<RespuestaDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = mapearDtoACliente(clienteDTO);
        clienteBusiness.guardarCliente(cliente);
        return ResponseEntity.ok(new RespuestaDTO("Cliente guardado con éxito."));
    }

    @GetMapping("/ping")
    public String ping() {
        return "funciona";
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteBusiness.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarCliente(@PathVariable("id") int id) {
        boolean eliminado = clienteBusiness.eliminarCliente(id);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Cliente eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Cliente no encontrado."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> actualizarCliente(@PathVariable("id") int id, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setIdPersona(id);
        boolean actualizado = clienteBusiness.actualizarCliente(clienteDTO);
        if (actualizado) {
            return ResponseEntity.ok(new RespuestaDTO("Cliente actualizado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("No se pudo actualizar el cliente."));
        }
    }

    // Función privada para mapear de DTO a Cliente
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

    // Manejo de error bonito
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
