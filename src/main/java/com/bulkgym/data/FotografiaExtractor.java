package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.FotografiaEjercicio;

public class FotografiaExtractor implements ResultSetExtractor<List<FotografiaEjercicio>> {

    @Override
    public List<FotografiaEjercicio> extractData(ResultSet rs) throws SQLException {
        List<FotografiaEjercicio> fotografias = new ArrayList<>();

        while (rs.next()) {
            FotografiaEjercicio fotografia = new FotografiaEjercicio();
            fotografia.setIdFotografia(rs.getInt("id_fotografia"));
            fotografia.setRutaImagen(rs.getString("ruta_imagen"));
            fotografia.setIdEjercicio(rs.getInt("id_ejercicio"));
            fotografias.add(fotografia);
        }

        return fotografias;
    }
}
