package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.CategoriaEjercicio;

public class CategoriaExtractor implements ResultSetExtractor<List<CategoriaEjercicio>> {

    @Override
    public List<CategoriaEjercicio> extractData(ResultSet rs) throws SQLException {
        List<CategoriaEjercicio> categorias = new ArrayList<>();

        while (rs.next()) {
            CategoriaEjercicio categoria = new CategoriaEjercicio();
            categoria.setIdCategoriaEjercicio(rs.getInt("id_categoria"));
            categoria.setNombreCategoria(rs.getString("nombre_categoria"));
            categorias.add(categoria);
        }

        return categorias;
    }
}
