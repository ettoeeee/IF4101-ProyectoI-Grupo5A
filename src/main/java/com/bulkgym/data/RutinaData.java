package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.Rutina;

@Repository
public class RutinaData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Buscar todas las rutinas de un cliente
    @Transactional(readOnly = true)
    public List<Rutina> encontrarRutinasPorCliente(int idCliente) {
        String sql = """
            SELECT r.id_rutina, r.id_cliente, r.id_instructor, r.fecha_creacion, r.fecha_renovacion, r.horario,
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
    
    @Transactional(readOnly = true)
    public Rutina buscarPorId(int idRutina) {
        String sql = """
            SELECT id_rutina,
                   id_cliente,
                   id_instructor,
                   fecha_creacion,
                   fecha_renovacion,
                   horario,
                   objetivo,
                   lesiones,
                   padecimientos
            FROM Rutina
            WHERE id_rutina = ?
        """;

        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> {
                Rutina r = new Rutina();
                r.setIdRutina(rs.getInt("id_rutina"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setIdInstructor(rs.getInt("id_instructor"));
                r.setFechaCreacion(rs.getDate("fecha_creacion"));
                r.setFechaRenovacion(rs.getDate("fecha_renovacion"));
                r.setHorario(rs.getString("horario"));
                r.setObjetivo(rs.getString("objetivo"));
                r.setLesiones(rs.getString("lesiones"));
                r.setPadecimientos(rs.getString("padecimientos"));
                return r;
            },
            idRutina
        );
    }
    
    @Transactional
    public int insertarRutina(Rutina rutina) {
        String sql = """
            INSERT INTO Rutina (
              id_cliente, 
              id_instructor, 
              fecha_creacion, 
              fecha_renovacion, 
              horario, 
              objetivo, 
              lesiones, 
              padecimientos
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rutina.getIdCliente());
            ps.setInt(2, rutina.getIdInstructor());
            ps.setDate(3, rutina.getFechaCreacion());
            ps.setDate(4, rutina.getFechaRenovacion());
            ps.setString(5, rutina.getHorario());
            ps.setString(6, rutina.getObjetivo());
            ps.setString(7, rutina.getLesiones());
            ps.setString(8, rutina.getPadecimientos());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Rutina> encontrarRutinasPorNombreCliente(String nombreCliente) {
        String sql = """
            SELECT r.id_rutina, r,id_cliente, r.id_instructor, r.fecha_creacion, r.fecha_renovacion, r.horario,
                   r.objetivo, r.lesiones, r.padecimientos
            FROM Rutina r
            JOIN Cliente c ON r.id_cliente = c.id_cliente
            WHERE c.nombre LIKE ?
        """;

        return jdbcTemplate.query(sql, new RutinaExtractor(), "%" + nombreCliente + "%");
    }

}

