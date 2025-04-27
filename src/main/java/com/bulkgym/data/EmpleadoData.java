package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.Empleado;
import com.bulkgym.dto.EmpleadoDTO;

@Repository
public class EmpleadoData {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional (readOnly = true)
	public Empleado findById (int idEmpleado) {
		
		String SQL = """
			SELECT e.id_empleado, e.rol_empleado,
				   p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
	               p.sexo, p.telefono, p.correo_electronico, p.imagen
	        FROM Empleado e
	        JOIN Persona p ON e.id_persona = p.id_persona
	        WHERE c.id_cliente = ?
		""";
		
		List<Empleado> list = jdbcTemplate.query(SQL, new EmpleadoExtractor(), idEmpleado);		
		return list.isEmpty() ? null : list.get(0);
	}// End of method [findById].
	
	@Transactional
	public List<Empleado> findAll() {
		
		String SQL = """
			SELECT e.id_empleado, e.rol_empleado,
				   p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
	               p.sexo, p.telefono, p.correo_electronico, p.imagen,
	               p.direccion, p.nombre_contacto_emergencia, p.tel_contacto_emergencia
	        FROM Empleado e
	        JOIN Persona p ON e.id_persona = p.id_persona
		""";
		
		return jdbcTemplate.query(SQL, new EmpleadoExtractor());
	}// End of method [findAll].
	
	@Transactional
	public void saveEmpleado(Empleado empleado) {
		if (empleado.getRolEmpleado() == null)
			throw new IllegalArgumentException("Cada empleado DEBE tener un rol.");
		
		Integer count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM Persona WHERE id_persona = ?", 
				Integer.class,
				empleado.getIdPersona()
		);
		
		if (count != null && count > 0) 
			throw new IllegalArgumentException("Ya hay una persona registrada con esta cédula.");
		
	    String sqlPersona = """
		        INSERT INTO Persona (
		            id_persona, nombre, apellidos, fecha_nacimiento,
		            sexo, telefono, correo_electronico, imagen,
		            direccion, nombre_contacto_emergencia, tel_contacto_emergencia
		        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		    """;

		    jdbcTemplate.update(sqlPersona,
		    	empleado.getIdPersona(),
		    	empleado.getNombre(),
		    	empleado.getApellidos(),
		    	empleado.getFechaNacimiento(),
		        String.valueOf(empleado.getSexo()),
		        empleado.getTelefono(),
		        empleado.getCorreoElectronico(),
		        empleado.getImagenRuta(),
		        empleado.getDireccion(),
		        empleado.getNombreContactoEmergencia(),
		        empleado.getTelContactoEmergencia()
		    );

		    Integer idEmpleado = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(id_empleado), 0) + 1 FROM Empleado", Integer.class);
		    
		    String sqlEmpleado = """
			        INSERT INTO Empleado (id_empleado, id_persona, rol_empleado)
			        VALUES (?, ?, ?)
			    """;
			    jdbcTemplate.update(sqlEmpleado, idEmpleado, empleado.getIdPersona(), empleado.getRolEmpleado());

			    empleado.setIdEmpleado(idEmpleado);
	}
	
	@Transactional
	public boolean deleteEmpleado(int idEmpleado) {
		try {
			Integer idPersona = jdbcTemplate.queryForObject(
					"SELECT id_persona FROM Empleado WHERE id_empleado = ?", 
					Integer.class,
					idEmpleado
			);
			
			int rowsEmpleado = jdbcTemplate.update(
				"DELETE FROM Empleado WHERE id_empleado = ?", idEmpleado
			);
			
			jdbcTemplate.update(
				"DELETE FROM Persona WHERE id_persona = ?", idPersona
			);
			
			return rowsEmpleado > 0;
			
	    } catch (EmptyResultDataAccessException e) {
	        return false;
	    }// End of try-catch.	
	}// End of method [deleteEmpleado].
	
	@Transactional
	public boolean updateEmpleado(EmpleadoDTO empleado) {
		if (empleado.getRolEmpleado() == null)
			throw new IllegalArgumentException("Cada empleado DEBE tener un rol.");
		
		String SQL = """
			UPDATE Persona
	        SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, sexo = ?, telefono = ?, correo_electronico = ?, imagen = ?, direccion = ?, nombre_contacto_emergencia = ?, tel_contacto_emergencia = ?
	        WHERE id_persona = ?		
	    """;
		
	    int result = jdbcTemplate.update(SQL,
	    		empleado.getNombre(),
	    		empleado.getApellidos(),
	    		empleado.getFechaNacimiento(),
		        String.valueOf(empleado.getSexo()),
		        empleado.getTelefono(),
		        empleado.getCorreoElectronico(),
		        empleado.getImagenRuta(),
		        empleado.getDireccion(),
		        empleado.getNombreContactoEmergencia(),
		        empleado.getTelContactoEmergencia(),
		        empleado.getIdPersona()
		    );

		return result > 0;
	}// End of method [updateEmpleado].
}// End of class [EmpleadoData].
