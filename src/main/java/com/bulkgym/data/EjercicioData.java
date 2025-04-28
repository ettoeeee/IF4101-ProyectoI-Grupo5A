package com.bulkgym.data;

import com.bulkgym.domain.Ejercicio;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Ejercicio> findAll() {
        String sql = "SELECT e.id_ejercicio, e.nombre_ejercicio, c.id_categoria, c.nombre_categoria, " +
                     "f.id_fotografia, f.ruta_imagen " +
                     "FROM Ejercicio e " +
                     "JOIN CategoriaEjercicio c ON e.id_categoria = c.id_categoria " +
                     "LEFT JOIN FotografiasEjercicio f ON e.id_ejercicio = f.id_ejercicio";
        return jdbcTemplate.query(sql, new EjercicioExtractor());
    }

    @Transactional
    public void guardar(Ejercicio ejercicio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withSchemaName("dbo")
                .withProcedureName("InsertEjercicio")
                .declareParameters(
                    new SqlParameter("nombre_ejercicio", Types.VARCHAR),
                    new SqlParameter("id_categoria", Types.INTEGER),
                    new SqlOutParameter("id_ejercicio", Types.INTEGER)
                );

        int idCategoria = ejercicio.getCategoriaEjercicio() != null && 
                          !ejercicio.getCategoriaEjercicio().isEmpty()
                          ? ejercicio.getCategoriaEjercicio().get(0).getIdCategoriaEjercicio()
                          : 0;

        Map<String, Object> params = Map.of(
            "nombre_ejercicio", ejercicio.getNombreEjercicio(),
            "id_categoria", idCategoria
        );

        Map<String, Object> result = jdbcCall.execute(params);
        ejercicio.setIdEjercicio((Integer) result.get("id_ejercicio"));
    }

    @Transactional
    public void update(Ejercicio ejercicio) {
        int idCategoria = ejercicio.getCategoriaEjercicio() != null && 
                          !ejercicio.getCategoriaEjercicio().isEmpty()
                          ? ejercicio.getCategoriaEjercicio().get(0).getIdCategoriaEjercicio()
                          : 0;

        String sql = "UPDATE Ejercicio SET nombre_ejercicio = ?, id_categoria = ? WHERE id_ejercicio = ?";
        jdbcTemplate.update(sql, ejercicio.getNombreEjercicio(), idCategoria, ejercicio.getIdEjercicio());
    }

    @Transactional
    public void delete(int id) {
        // Primero eliminar las fotografías asociadas
        jdbcTemplate.update("DELETE FROM FotografiasEjercicio WHERE id_ejercicio = ?", id);

        // Luego eliminar el ejercicio
        jdbcTemplate.update("DELETE FROM Ejercicio WHERE id_ejercicio = ?", id);
    }

    @Transactional(readOnly = true)
    public Ejercicio findById(int id) {
        String sql = "SELECT e.id_ejercicio, e.nombre_ejercicio, c.id_categoria, c.nombre_categoria, " +
                     "f.id_fotografia, f.ruta_imagen " +
                     "FROM Ejercicio e " +
                     "JOIN CategoriaEjercicio c ON e.id_categoria = c.id_categoria " +
                     "LEFT JOIN FotografiasEjercicio f ON e.id_ejercicio = f.id_ejercicio " +
                     "WHERE e.id_ejercicio = ?";

        List<Ejercicio> ejercicios = jdbcTemplate.query(sql, new EjercicioExtractor(), id);
        return ejercicios.isEmpty() ? null : ejercicios.get(0);
    }
}
