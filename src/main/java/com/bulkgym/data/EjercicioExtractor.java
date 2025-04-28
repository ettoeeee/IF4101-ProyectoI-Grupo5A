package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.FotografiaEjercicio;
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
	            ejercicio.setImagen(rs.getString("imagen")); // ✅ Imagen de la tabla Ejercicio
	            // Categoría
	            List<CategoriaEjercicio> categorias = new ArrayList<>();
	            CategoriaEjercicio categoria = new CategoriaEjercicio();
	            categoria.setNombreCategoria(rs.getString("nombre_categoria"));
	            categorias.add(categoria);
	            ejercicio.setCategoriaEjercicio(categorias);

	            // No fotografías por ahora
	            ejercicio.setFotografiasEjercicio(new ArrayList<>());
	            
	            map.put(id, ejercicio);
	        }

	        // 🔥 Comentar o eliminar esta parte para evitar el error
	        /*
	        if (rs.getString("ruta_imagen") != null) {
	            FotografiaEjercicio fotografia = new FotografiaEjercicio();
	            fotografia.setIdFotografia(rs.getInt("id_fotografia"));
	            fotografia.setRutaImagen(rs.getString("ruta_imagen"));
	            fotografia.setIdEjercicio(id);

	            ejercicio.getFotografiasEjercicio().add(fotografia);
	        }
	        */
	    }
	    return new ArrayList<>(map.values());
	}
}