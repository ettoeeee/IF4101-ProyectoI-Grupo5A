package com.bulkgym.data;

import com.bulkgym.domain.ItemRutinaEjercicio;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemRutinaEjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final String SQL_POR_RUTINA = """
            SELECT ire.id_rutina,
                   ire.id_ejercicio,
                   ire.series_ejercicio,
                   ire.repeticiones_ejercicio,
                   ire.equipo_ejercicio
              FROM ItemRutinaEjercicio ire
             WHERE ire.id_rutina = ?
        """;

    @Transactional(readOnly = true)
    public List<ItemRutinaEjercicio> findByRutinaId(int idRutina) {
       return jdbcTemplate.query(SQL_POR_RUTINA, new ItemRutinaEjercicioExtractor(), idRutina);
    }
    @Transactional
    public void eliminarPorRutina(int idRutina) {
        String sql = "DELETE FROM ItemRutinaEjercicio WHERE id_rutina = ?";
        jdbcTemplate.update(sql, idRutina);
    }

    
    
    
    
    @Transactional(readOnly = true)
    public List<ItemRutinaEjercicio> findAll() {
        String sql = """
            SELECT id_rutina, id_ejercicio, series_ejercicio, repeticiones_ejercicio, equipo_ejercicio
            FROM ItemRutinaEjercicio
        """;
        return jdbcTemplate.query(sql, new ItemRutinaEjercicioExtractor());
    }

    @Transactional
    public void guardar(ItemRutinaEjercicio item) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("InsertItemRutinaEjercicio")
                .declareParameters(
                    new SqlParameter("id_rutina", Types.INTEGER),
                    new SqlParameter("id_ejercicio", Types.INTEGER),
                    new SqlParameter("series_ejercicio", Types.INTEGER),
                    new SqlParameter("repeticiones_ejercicio", Types.INTEGER),
                    new SqlParameter("equipo_ejercicio", Types.VARCHAR)
                    // No hay OutParameter porque PK es compuesta y la recibe el caller
                );

        Map<String, Object> params = Map.of(
            "id_rutina", item.getIdRutina(),
            "id_ejercicio", item.getIdEjercicio(),
            "series_ejercicio", item.getSeriesEjercicio(),
            "repeticiones_ejercicio", item.getRepeticionesEjercicio(),
            "equipo_ejercicio", item.getEquipoEjercicio()
        );

        jdbcCall.execute(params);
    }

    @Transactional
    public void update(ItemRutinaEjercicio item) {
        String sql = """
            UPDATE ItemRutinaEjercicio
            SET series_ejercicio = ?, repeticiones_ejercicio = ?, equipo_ejercicio = ?
            WHERE id_rutina = ? AND id_ejercicio = ?
        """;

        jdbcTemplate.update(sql,
            item.getSeriesEjercicio(),
            item.getRepeticionesEjercicio(),
            item.getEquipoEjercicio(),
            item.getIdRutina(),
            item.getIdEjercicio()
        );
    }

    @Transactional
    public void delete(int idRutina, int idEjercicio) {
        String sql = "DELETE FROM ItemRutinaEjercicio WHERE id_rutina = ? AND id_ejercicio = ?";
        jdbcTemplate.update(sql, idRutina, idEjercicio);
    }

    @Transactional(readOnly = true)
    public ItemRutinaEjercicio findById(int idRutina, int idEjercicio) {
        String sql = """
            SELECT id_rutina, id_ejercicio, series_ejercicio, repeticiones_ejercicio, equipo_ejercicio
            FROM ItemRutinaEjercicio
            WHERE id_rutina = ? AND id_ejercicio = ?
        """;

        List<ItemRutinaEjercicio> items = jdbcTemplate.query(sql, new ItemRutinaEjercicioExtractor(), idRutina, idEjercicio);
        return items.isEmpty() ? null : items.get(0);
    }

    @Transactional(readOnly = true)
    public int contarItems() {
        String sql = "SELECT COUNT(*) FROM ItemRutinaEjercicio";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

