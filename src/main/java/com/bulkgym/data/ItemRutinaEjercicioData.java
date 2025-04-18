package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.ItemRutinaEjercicio;

@Repository
public class ItemRutinaEjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<ItemRutinaEjercicio> obtenerEjerciciosPorRutina(int idRutina) {
        String sql = """
            SELECT ire.id_item_rutina_ejercicio,
                   ire.series_ejercicio,
                   ire.repeticiones_ejercicio,
                   ire.equipo_ejercicio,
                   e.id_ejercicio,
                   e.nombre_ejercicio,
                   c.id_categoria_ejercicio,
                   c.nombre_categoria
            FROM ItemRutinaEjercicio ire
            JOIN Ejercicio e ON ire.id_ejercicio = e.id_ejercicio
            JOIN CategoriaEjercicio c ON e.id_ejercicio = c.id_ejercicio
            WHERE ire.id_rutina = ?
        """;

        return jdbcTemplate.query(sql, new ItemRutinaEjercicioExtractor(), idRutina);
    }
}

