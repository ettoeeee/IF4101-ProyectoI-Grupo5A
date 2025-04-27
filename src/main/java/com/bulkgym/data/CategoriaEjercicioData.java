package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bulkgym.domain.CategoriaEjercicio;

@Repository
public class CategoriaEjercicioData {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private EjercicioData ejercicioData;

    public List<CategoriaEjercicio> buscarCategoriaPorId(int idCategoria) {
        String sql = "SELECT * FROM CategoriaEjercicio WHERE id_categoria = ?";
        return jdbcTemplate.query(sql, new CategoriaExtractor(), idCategoria);
    }
    
    public List<CategoriaEjercicio> obtenerTodasLasCategorias() {
        String sql = "SELECT * FROM CategoriaEjercicio";
        return jdbcTemplate.query(sql, new CategoriaExtractor());
    }

    public boolean eliminarCategoriaPorId(int id) {
        try {
            String eliminarEjerciciosSQL = "DELETE FROM Ejercicio WHERE id_categoria = ?";
            jdbcTemplate.update(eliminarEjerciciosSQL, id); // Eliminar los ejercicios asociados
            
            String eliminarCategoriaSQL = "DELETE FROM CategoriaEjercicio WHERE id_categoria = ?";
            int rowsAffected = jdbcTemplate.update(eliminarCategoriaSQL, id); // Eliminar la categoría

            return rowsAffected > 0; // Si se eliminaron filas, la categoría fue eliminada correctamente
        } catch (Exception e) {
            return false;
        }
    }

	
    public CategoriaEjercicio modificarCategoria(int idCategoria) {
        String sql = "SELECT * FROM CategoriaEjercicio WHERE id_categoria = ?";
        
        return jdbcTemplate.queryForObject(sql, new Object[]{idCategoria}, (rs, rowNum) -> {
            CategoriaEjercicio categoria = new CategoriaEjercicio();
            categoria.setCodCategoria(rs.getInt("id_categoria"));
            categoria.setNombreCategoria(rs.getString("nombre_categoria"));
            return categoria;
        });
    }
    
    public void insertarCategoria(CategoriaEjercicio categoriaEjercicio) {
        String sql = "INSERT INTO CategoriaEjercicio (nombre_categoria) VALUES (?)";
        jdbcTemplate.update(sql, categoriaEjercicio.getNombreCategoria());
    }


}
