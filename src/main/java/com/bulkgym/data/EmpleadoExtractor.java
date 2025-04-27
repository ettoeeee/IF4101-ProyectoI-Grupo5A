package com.bulkgym.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.bulkgym.domain.Empleado;

public class EmpleadoExtractor implements ResultSetExtractor<List<Empleado>>{
	
	@Override
	public List<Empleado> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, Empleado> map = new HashMap<>();
		
		while (rs.next()) {
			int id = rs.getInt("id_empleado");
			Empleado empleado = map.get(id);
			
			if (empleado == null) {
				empleado = new Empleado();
				empleado.setIdEmpleado(id);
				empleado.setRolEmpleado("rol_empleado");
				
                empleado.setIdPersona(rs.getInt("id_persona"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellidos(rs.getString("apellidos"));
                empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                empleado.setSexo(rs.getString("sexo").charAt(0));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setCorreoElectronico(rs.getString("correo_electronico"));
                empleado.setImagenRuta(rs.getString("imagen"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setNombreContactoEmergencia(rs.getString("nombre_contacto_emergencia"));
                empleado.setTelContactoEmergencia(rs.getString("tel_contacto_emergencia"));
                
                map.put(id, empleado);
			}// End of 'if'.
		}// End of 'while'.
		return new ArrayList<>(map.values());
	}// End of method [extractData]
}// End of class [EmpleadoExtractor].
