package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.bulkgym.domain.Ejercicio;

public class EjercicioExtractor implements ResultSetExtractor<List<Ejercicio>> {

	@Override
	public List<Ejercicio> extractData (ResultSet rs) throws SQLException{
		
		Map<Integer, Ejercicio> map = new HashMap<Integer, Ejercicio>();
		Ejercicio ejercicio = null;
		while(rs.next()) {
			Integer id = rs.getInt("cod_ejercicio");
			ejercicio = map.get(id);
			if (ejercicio == null) {
				ejercicio = new Ejercicio();
				ejercicio.setIdEjercicio(id);
				ejercicio.setNombreEjercicio(rs.getString("nombre_ejercicio"));
				ejercicio.getCategoriaEjercicio().setNombreCategoria(rs.getString("nombre_categoria"));
				map.put(id, ejercicio);
				
			}
			
		}
		return new ArrayList<Ejercicio>(map.values());
		
	}

}
