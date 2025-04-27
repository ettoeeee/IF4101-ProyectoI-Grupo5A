package com.bulkgym.restcontroller;

import com.bulkgym.business.EmpleadoBusiness;
import com.bulkgym.domain.Empleado;
import com.bulkgym.dto.EmpleadoDTO;
import com.bulkgym.dto.RespuestaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

	@Autowired
	private EmpleadoBusiness empleadoBusiness;
	
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
	
	private Empleado mapDTOtoEmpleado(EmpleadoDTO dto) {
		Empleado empleado = new Empleado();
		empleado.setRolEmpleado(dto.getRolEmpleado());
		
		empleado.setIdPersona(dto.getIdPersona());
		empleado.setNombre(dto.getNombre());
		empleado.setApellidos(dto.getApellidos());
		empleado.setFechaNacimiento(dto.getFechaNacimiento());
		empleado.setSexo(dto.getSexo());
		empleado.setTelefono(dto.getTelefono());
		empleado.setCorreoElectronico(dto.getCorreoElectronico());
		empleado.setImagenRuta(dto.getImagenRuta());
		empleado.setDireccion(dto.getDireccion());
		empleado.setNombreContactoEmergencia(dto.getNombreContactoEmergencia());
		empleado.setTelContactoEmergencia(dto.getTelContactoEmergencia());
		
		return empleado;
	}
	
	@PostMapping
	public ResponseEntity<RespuestaDTO> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
		Empleado empleado = mapDTOtoEmpleado(empleadoDTO);
		empleadoBusiness.saveEmpleado(empleado);
		return ResponseEntity.ok(new RespuestaDTO("Empleado guardado con éxito."));
	}
	
	@GetMapping
	public ResponseEntity<List<Empleado>> listEmpleados() {
		List<Empleado> empleados = empleadoBusiness.findAll();
		return ResponseEntity.ok(empleados);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaDTO> deleteEmpleado(@PathVariable("id") int id) {
		boolean deleted = empleadoBusiness.deleteEmpleado(id);
		
		if(deleted) return ResponseEntity.ok(new RespuestaDTO("Empleado eliminado con éxito."));
		else return ResponseEntity.status(404).body(new RespuestaDTO("Empleado no encontrado."));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RespuestaDTO> updateEmpleado(@PathVariable("id") int id, @RequestBody EmpleadoDTO empleadoDTO) {
		empleadoDTO.setIdPersona(id);
		boolean updated = empleadoBusiness.updateEmpleado(empleadoDTO);
		if (updated) return ResponseEntity.ok(new RespuestaDTO("Empleado actualizado con éxito."));
		else return ResponseEntity.status(404).body(new RespuestaDTO("No se pudo actualizar el empleado."));
	}
	
}//  End of class [EmpleadoRestController].
