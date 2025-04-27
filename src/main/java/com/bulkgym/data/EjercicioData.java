package com.bulkgym.data;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.CategoriaEjercicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EjercicioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Ejercicio> buscarTodosEjercicios() {
        // Modificado para reflejar la nueva estructura de la tabla Ejercicio
        String sqlSelect = "SELECT e.id_ejercicio, e.nombre_ejercicio, c.nombre_categoria " +
                           "FROM Ejercicio e " +
                           "JOIN CategoriaEjercicio c ON e.id_categoria = c.id_categoria";
        return jdbcTemplate.query(sqlSelect, new EjercicioExtractor());
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> findExercisesByName(String nombre) {
        // Consulta modificada para reflejar la nueva estructura
        String sqlSelect = "SELECT e.id_ejercicio, e.nombre_ejercicio, c.nombre_categoria " +
                           "FROM Ejercicio e " +
                           "JOIN CategoriaEjercicio c ON e.id_categoria = c.id_categoria " +
                           "WHERE e.nombre_ejercicio LIKE ? OR c.nombre_categoria LIKE ?";

        return jdbcTemplate.query(sqlSelect, new EjercicioExtractor(), "%" + nombre + "%", "%" + nombre + "%");
    }

    @Transactional(readOnly = true)
    public Ejercicio findEjercicioById(int codEjercicio) {
        // Ajustada la consulta a la nueva estructura de Ejercicio sin la columna de Rutina
        String sql = "SELECT e.id_ejercicio, e.nombre_ejercicio, c.nombre_categoria " +
                     "FROM Ejercicio e " +
                     "JOIN CategoriaEjercicio c ON e.id_categoria = c.id_categoria " +
                     "WHERE e.id_ejercicio = ?";

        List<Ejercicio> ejercicios = jdbcTemplate.query(sql, new EjercicioExtractor(), codEjercicio);
        return ejercicios.isEmpty() ? null : ejercicios.get(0);
    }

    /*
    @Transactional
    public void modificarEjercicio(int codEjercicio) {
        // 1. Obtener el ejercicio actual
        Ejercicio ejercicioExistente = findEjercicioById(codEjercicio);

        if (ejercicioExistente != null) {
            // 2. Simulación de la actualización con los datos existentes (en un escenario real, esto vendría de la vista)
            String nuevoNombre = ejercicioExistente.getNombreEjercicio();
            int nuevaCategoria = ejercicioExistente.getCategoriaEjercicio() != null &&
                                 !ejercicioExistente.getCategoriaEjercicio().isEmpty()
                                 ? ejercicioExistente.getCategoriaEjercicio().get(0).getCodCategoria() : 0;

            // 3. Ejecutar el UPDATE (ajusta los nombres de columnas según tu base de datos)
            String sqlUpdate = "UPDATE Ejercicio " +
                               "SET nombre_ejercicio = ?, id_categoria = ? " +
                               "WHERE id_ejercicio = ?";

            jdbcTemplate.update(sqlUpdate,
                nuevoNombre,
                nuevaCategoria,
                codEjercicio
            );
        }
    }
    */
    
    @Transactional
    public void modificarEjercicio(int codEjercicio, String nuevoNombre) {
    	String sqlUpdate = "UPDATE Ejercicio SET nombre_ejercicio = ? WHERE id_ejercicio = ?";
    	jdbcTemplate.update(sqlUpdate, nuevoNombre, codEjercicio);

    }


    @Transactional
    public void insertarEjercicio(Ejercicio ejercicio) {
        // Obtenemos el ID de la categoría desde el objeto Ejercicio
        int idCategoriaSeleccionada = ejercicio.getCategoriaEjercicio() != null &&
                                      !ejercicio.getCategoriaEjercicio().isEmpty()
                                      ? ejercicio.getCategoriaEjercicio().get(0).getIdCategoriaEjercicio() : 0;

        String sqlInsert = "INSERT INTO Ejercicio (nombre_ejercicio, id_categoria) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsert,
                ejercicio.getNombreEjercicio(),
                idCategoriaSeleccionada);  // Usamos el ID de la categoría directamente desde el objeto Ejercicio
    }


    @Transactional
    public boolean eliminarEjercicio(int codEjercicio) {
        Ejercicio ejercicioExistente = findEjercicioById(codEjercicio);

        if (ejercicioExistente != null) {
            String sqlDelete = "DELETE FROM Ejercicio WHERE id_ejercicio = ?";
            jdbcTemplate.update(sqlDelete, codEjercicio);
            return true;
        }

        return false;
    }

}
