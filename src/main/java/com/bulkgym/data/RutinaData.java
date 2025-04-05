package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;

@Repository
public class RutinaData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Buscar todas las rutinas de un cliente
    @Transactional(readOnly = true)
    public List<Rutina> encontrarRutinasPorCliente(int idCliente) {
        String sql = """
            SELECT r.id_rutina, r.fecha_creacion, r.fecha_renovacion, r.horario,
                   r.objetivo, r.lesiones, r.padecimientos
            FROM Rutina r
            WHERE r.id_cliente = ?
        """;

        return jdbcTemplate.query(sql, new RutinaExtractor(), idCliente);
    }

    // Obtener una rutina específica con su lista de ejercicios
    @Transactional(readOnly = true)
    public List<Ejercicio> obtenerEjerciciosDeRutina(int idRutina) {
        String sql = """
            SELECT e.id_ejercicio, e.categoria, e.equipo,
                   ire.series_ejercicio, ire.repeticiones_ejercicio
            FROM Ejercicio e
            JOIN ItemRutinaEjercicio ire ON e.id_ejercicio = ire.id_ejercicio
            WHERE ire.id_rutina = ?
        """;

        return jdbcTemplate.query(sql, new EjercicioExtractor(), idRutina);
    }

    // Obtener medidas corporales asociadas a una rutina
    @Transactional(readOnly = true)
    public List<ItemRutinaMedida> obtenerValoresMedidasDeRutina(int idRutina) {
        String sql = """
            SELECT irm.id_item_rutina_medida,
                   irm.valor_medida,
                   irm.fecha_medicion,
                   mc.id_media_corporal,
                   mc.tipo_medida,
                   mc.unidad
            FROM ItemRutinaMedida irm
            JOIN MedidaCorporal mc ON irm.id_medida_corporal = mc.id_media_corporal
            WHERE irm.id_rutina = ?
        """;

        return jdbcTemplate.query(sql, new ItemRutinaMedidaExtractor(), idRutina);
    }

    // Verificar si un cliente tiene al menos una rutina
    @Transactional(readOnly = true)
    public boolean clienteTieneRutinas(int idCliente) {
        String sql = """
            SELECT COUNT(*) FROM Rutina WHERE id_cliente = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idCliente);
        return count != null && count > 0;
    }
}

