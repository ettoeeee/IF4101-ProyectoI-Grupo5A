package com.bulkgym.data;

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

import com.bulkgym.domain.CategoriaEjercicio;

@Repository
public class CategoriaEjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CategoriaEjercicio> findAll() {
        String sql = "SELECT id_categoria, nombre_categoria FROM CategoriaEjercicio";
        return jdbcTemplate.query(sql, new CategoriaExtractor());
    }

    @Transactional
    public void guardar(CategoriaEjercicio categoria) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        		.withSchemaName("dbo")
                .withProcedureName("InsertCategoriaEjercicio")
                .declareParameters(
                    new SqlParameter("nombre_categoria", Types.VARCHAR),
                    new SqlOutParameter("id_categoria", Types.INTEGER)
                );

        Map<String, Object> params = Map.of(
            "nombre_categoria", categoria.getNombreCategoria()
        );

        Map<String, Object> result = jdbcCall.execute(params);
        categoria.setIdCategoriaEjercicio((Integer) result.get("id_categoria"));
    }

    public void update(CategoriaEjercicio categoria) {
        String sql = "UPDATE CategoriaEjercicio SET nombre_categoria = ? WHERE id_categoria = ?";
        jdbcTemplate.update(sql, categoria.getNombreCategoria(), categoria.getIdCategoriaEjercicio());
    }

    public void delete(int idCategoria) {
        String sql = "DELETE FROM CategoriaEjercicio WHERE id_categoria = ?";
        jdbcTemplate.update(sql, idCategoria);
    }

    public CategoriaEjercicio findById(int id) {
        String sql = "SELECT id_categoria, nombre_categoria FROM CategoriaEjercicio WHERE id_categoria = ?";
        List<CategoriaEjercicio> categorias = jdbcTemplate.query(sql, new CategoriaExtractor(), id);
        return categorias.isEmpty() ? null : categorias.get(0);
    }
}
