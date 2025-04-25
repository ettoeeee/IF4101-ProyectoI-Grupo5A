package com.bulkgym.data;

import com.bulkgym.domain.Ejercicio;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EjercicioData {
	
	private JdbcTemplate jdbcTemplate;
	
	//Metodo que retornara la lista completa de ejercicios que se ha tenido en la base de datos hasta el momento.4
	@Transactional(readOnly=true)
	public List<Ejercicio> buscarTodosEjercicios(){
		
		String sqlSelect = "SELECT * FROM EJERCICIO";
		
		return jdbcTemplate.query(sqlSelect, new EjercicioExtractor());
	}
	
	// Buscar ejercicios por nombre usando un procedimiento almacenado
	@Transactional(readOnly = true)
    public List<Ejercicio> findExercisesByName(String nombre) {

        String sqlSelect = "SELECT e.cod_ejercicio, e.nombre_ejercicio, " +
                           "c.cod_categoria, c.nombre_categoria " +
                           "FROM Ejercicio e " +
                           "JOIN CategoriaEjercicio c ON e.cod_categoria = c.cod_categoria " +
                           "WHERE e.nombre_ejercicio LIKE ? OR c.nombre_categoria LIKE ?";

        return jdbcTemplate.query(sqlSelect, new EjercicioExtractor(), "%" + nombre + "%", "%" + nombre + "%");
    }
	
	@Transactional(readOnly = true)
	public Ejercicio findEjercicioById(int codEjercicio) {
	    String sql = "SELECT e.cod_ejercicio, e.nombre_ejercicio, " +
	                 "c.cod_categoria, c.nombre_categoria " +
	                 "FROM Ejercicio e " +
	                 "JOIN CategoriaEjercicio c ON e.cod_categoria = c.cod_categoria " +
	                 "WHERE e.cod_ejercicio = ?";

	    List<Ejercicio> ejercicios = jdbcTemplate.query(sql, new EjercicioExtractor(), codEjercicio);
	    
	    if (ejercicios.isEmpty()) {
	        return null; // o puedes lanzar una excepción si prefieres
	    }

	    return ejercicios.get(0);
	}


	@Transactional
	public void modificarEjercicio(int codEjercicio) {
	    // 1. Obtener el ejercicio actual
	    Ejercicio ejercicioExistente = findEjercicioById(codEjercicio);

	    if (ejercicioExistente != null) {
	        // 2. Aquí simulamos que se actualizan los datos con lo que vino desde la vista.
	        // En el mundo real, tú deberías tener esta info desde la capa Business.
	        String nuevoNombre = ejercicioExistente.getNombreEjercicio(); // se sustituye por el nuevo nombre
	        int nuevaCategoria = ejercicioExistente.getCategoriaEjercicio().getCodCategoria(); // nueva categoría

	        // 3. Ejecutar el UPDATE
	        String sqlUpdate = "UPDATE Ejercicio " +
	                           "SET nombre_ejercicio = ?, cod_categoria = ? " +
	                           "WHERE cod_ejercicio = ?";

	        jdbcTemplate.update(sqlUpdate, nuevoNombre, nuevaCategoria, codEjercicio);
	    }
	}

	@Transactional
	public void insertarEjercicio(Ejercicio ejercicio) {
	    String sqlInsert = "INSERT INTO Ejercicio (nombreEjercicio, equipoEjercicio, codCategoria) VALUES (?, ?, ?)";
	    
	    jdbcTemplate.update(sqlInsert,
	        ejercicio.getNombreEjercicio(),
	        ejercicio.getCategoriaEjercicio().getCodCategoria()
	    );
	}

	@Transactional
	public boolean eliminarEjercicio(int codEjercicio) {
	    // 1. Obtener el ejercicio actual
	    Ejercicio ejercicioExistente = findEjercicioById(codEjercicio);

	    // 2. Verificar si el ejercicio existe
	    if (ejercicioExistente != null) {
	        // 3. Ejecutar el DELETE
	        String sqlDelete = "DELETE FROM Ejercicio WHERE cod_ejercicio = ?";
	        jdbcTemplate.update(sqlDelete, codEjercicio);
	        return true;
	    }

	    // 4. Si no se encontró, retornar false
	    return false;
	}

}// End of class [EjercicioData].
