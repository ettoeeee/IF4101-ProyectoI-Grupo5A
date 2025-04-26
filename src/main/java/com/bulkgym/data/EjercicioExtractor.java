package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.Rutina;
import com.bulkgym.domain.CategoriaEjercicio;

public class EjercicioExtractor implements ResultSetExtractor<List<Ejercicio>> {

	@Override
	public List<Ejercicio> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, Ejercicio> map = new HashMap<>();
		Ejercicio ejercicio = null;

		while (rs.next()) {
			int id = rs.getInt("id_ejercicio");
			ejercicio = map.get(id);

			if (ejercicio == null) {
				ejercicio = new Ejercicio();
				ejercicio.setIdEjercicio(id);
				ejercicio.setNombreEjercicio(rs.getString("nombre_ejercicio"));
				ejercicio.setEquipo(rs.getString("equipo"));
				ejercicio.setSeries(rs.getInt("series"));
				ejercicio.setRepeticiones(rs.getInt("repeticiones"));

				// Asignar Rutina
				List<Rutina> rutinas = new ArrayList<>();
				Rutina rutina = new Rutina();
				rutina.setIdRutina(rs.getInt("id_rutina"));
				rutinas.add(rutina);
				ejercicio.setRutina(rutinas);

				// Asignar CategoriaEjercicio
				List<CategoriaEjercicio> categorias = new ArrayList<>();
				CategoriaEjercicio categoria = new CategoriaEjercicio();
				categoria.setNombreCategoria(rs.getString("categoria")); // suponiendo que el atributo se llama así
				categorias.add(categoria);
				ejercicio.setCategoriaEjercicio(categorias);

				map.put(id, ejercicio);
			}
		}

		return new ArrayList<>(map.values());
	}
}

