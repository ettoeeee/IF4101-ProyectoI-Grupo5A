package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bulkgym.domain.CategoriaEjercicio;

public class CategoriaEjercicioData {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private EjercicioData ejercicioData;

    public List<CategoriaEjercicio> buscarCategoriaPorId(int idCategoria) {
        String sql = "SELECT * FROM CategoriaEjercicio WHERE codCategoria = ?";
        return jdbcTemplate.query(sql, new CategoriaExtractor(), idCategoria);
    }

    public boolean eliminarCategoria(CategoriaEjercicio categoria) {
        try {
            // 1. Eliminar ejercicios asociados
            String eliminarEjerciciosSQL = "DELETE FROM Ejercicio WHERE codCategoria = ?";
            jdbcTemplate.update(eliminarEjerciciosSQL, categoria.getCodCategoria());

            // 2. Eliminar categoría
            String eliminarCategoriaSQL = "DELETE FROM CategoriaEjercicio WHERE codCategoria = ?";
            jdbcTemplate.update(eliminarCategoriaSQL, categoria.getCodCategoria());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
    public CategoriaEjercicio modificarCategoria(int idCategoria) {
        String sql = "SELECT * FROM CategoriaEjercicio WHERE codCategoria = ?";
        
        return jdbcTemplate.queryForObject(sql, new Object[]{idCategoria}, (rs, rowNum) -> {
            CategoriaEjercicio categoria = new CategoriaEjercicio();
            categoria.setCodCategoria(rs.getInt("codCategoria"));
            categoria.setNombreCategoria(rs.getString("nombreCategoria"));
            return categoria;
        });
    }
    
    public void insertarCategoria(CategoriaEjercicio categoriaEjercicio) {
        String sql = "INSERT INTO CategoriaEjercicio (nombreCategoria) VALUES (?)";
        jdbcTemplate.update(sql, categoriaEjercicio.getNombreCategoria());
    }


}
