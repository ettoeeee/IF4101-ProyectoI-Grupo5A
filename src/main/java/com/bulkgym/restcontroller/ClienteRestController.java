package com.bulkgym.restcontroller;

import com.bulkgym.business.ClienteBusiness;
import com.bulkgym.data.ClienteData;
import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;
import com.bulkgym.dto.RespuestaDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteBusiness clienteBusiness;
    private ClienteData clienteData;


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
    


    @GetMapping("/cantidad")
    public Map<String, Integer> contarClientes() {
        int cantidad = clienteBusiness.contarClientes();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("cantidadClientes", cantidad);
        return respuesta;
    }

    @GetMapping("/cantidad/activos")
    public Map<String, Integer> contarClientesActivos() {
        int cantidad = clienteBusiness.contarClientesActivos();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("clientesActivos", cantidad);
        return respuesta;
    }

    @GetMapping("/cantidad/inactivos")
    public Map<String, Integer> contarClientesInactivos() {
        int cantidad = clienteBusiness.contarClientesInactivos();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("clientesInactivos", cantidad);
        return respuesta;
    }

    @GetMapping("/recientes")
    public List<Cliente> obtenerClientesRecientes() {
        return clienteBusiness.obtenerClientesRecientes();
    }
    
    
    //ISNTRUCTO 

}
