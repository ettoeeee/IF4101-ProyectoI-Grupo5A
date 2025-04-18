package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.Instructor;

public class InstructorExtractor implements ResultSetExtractor<List<Instructor>>{
	
	public List<Instructor> extractData(ResultSet rs) throws SQLException {
		
		Map<Integer, Instructor> map = new HashMap<>();
		
		while (rs.next()) {
			int idInstructor = rs.getInt("id_instructor");
			
			if (!map.containsKey(idInstructor)) {
				Instructor instructor = new Instructor();
				instructor.setIdInstructor(idInstructor);
				instructor.setFechaIngreso(rs.getDate("fecha_ingreso"));
                instructor.setIdPersona(rs.getInt("id_persona"));
                instructor.setNombre(rs.getString("nombre"));
                instructor.setApellidos(rs.getString("apellidos"));
                instructor.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                instructor.setSexo(rs.getString("sexo").charAt(0));
                instructor.setTelefono(rs.getString("telefono"));
                instructor.setCorreoElectronico(rs.getString("correo_electronico"));
                instructor.setImagenRuta(rs.getString("imagen"));

                map.put(idInstructor, instructor);
			}// End of 'if'.
		}// End of 'while'.
		
		return new ArrayList<>(map.values());		
	}// End of method [extractData].
}// End of class [InstructorExtractor].
