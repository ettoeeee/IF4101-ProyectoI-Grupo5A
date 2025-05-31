package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.bulkgym.domain.Rutina;


public class RutinaExtractor implements ResultSetExtractor<List<Rutina>> {

	@Override
	public List<Rutina> extractData (ResultSet rs) throws SQLException{
		
		Map<Integer, Rutina> map = new HashMap<Integer, Rutina>();
		Rutina rutina = null;
		while(rs.next()) {
			Integer id = rs.getInt("id_rutina");
			rutina = map.get(id);
			if (rutina == null) {
				rutina = new Rutina();
				rutina.setIdRutina(id);
				rutina.setFechaCreacion(rs.getDate("fecha_creacion"));
				rutina.setFechaRenovacion(rs.getDate("fecha_renovacion"));
				rutina.setHorario(rs.getString("horario"));
				rutina.setObjetivo(rs.getString("objetivo"));
				rutina.setLesiones(rs.getString("lesiones"));
				rutina.setPadecimientos(rs.getString("padecimientos"));
				map.put(id, rutina);
				
			}
			
		}
		return new ArrayList<Rutina>(map.values());
		
	}

}
