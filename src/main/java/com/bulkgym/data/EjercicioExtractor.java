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

            // Si aún no hemos creado el ejercicio, lo hacemos
            if (ejercicio == null) {
                ejercicio = new Ejercicio();
                ejercicio.setIdEjercicio(id);
                ejercicio.setNombreEjercicio(rs.getString("nombre_ejercicio"));

                // Asignar CategoriaEjercicio
                List<CategoriaEjercicio> categorias = new ArrayList<>();
                CategoriaEjercicio categoria = new CategoriaEjercicio();
                categoria.setNombreCategoria(rs.getString("nombre_categoria")); // Asumiendo que este es el nombre de la columna
                categorias.add(categoria);
                ejercicio.setCategoriaEjercicio(categorias);

                // Inicializamos la lista de fotografías asociadas
                ejercicio.setFotografiasEjercicio(new ArrayList<>());
                
                map.put(id, ejercicio);
            }

            // Extraemos las fotografías si las hay (una vez por ejercicio)
            if (rs.getString("ruta_imagen") != null) {
                FotografiaEjercicio fotografia = new FotografiaEjercicio();
                fotografia.setIdFotografia(rs.getInt("id_fotografia"));
                fotografia.setRutaImagen(rs.getString("ruta_imagen"));
                fotografia.setIdEjercicio(id); // Asociamos la fotografía con el ejercicio

                // Añadimos la fotografía a la lista de fotografías del ejercicio
                ejercicio.getFotografiasEjercicio().add(fotografia);
            }
        }

        // Retornamos todos los ejercicios con sus respectivas fotografías
        return new ArrayList<>(map.values());
    }
}
