package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;

public class CategoriaExtractor implements ResultSetExtractor<List<CategoriaEjercicio>> {

	@Override
	public List<CategoriaEjercicio> extractData (ResultSet rs) throws SQLException{
		
		Map<Integer, CategoriaEjercicio> map = new HashMap<Integer, CategoriaEjercicio>();
		CategoriaEjercicio ejercicio = null;
		while(rs.next()) {
			Integer id = rs.getInt("id_categoria");
			ejercicio = map.get(id);
			if (ejercicio == null) {
				ejercicio = new CategoriaEjercicio();
				ejercicio.setCodCategoria(id);
				ejercicio.setNombreCategoria(rs.getString("nombre_categoria"));
				map.put(id, ejercicio);
				
			}
			
		}
		return new ArrayList<CategoriaEjercicio>(map.values());
		
	}


}
