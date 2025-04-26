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
        String sqlSelect = "SELECT * FROM EJERCICIO";
        return jdbcTemplate.query(sqlSelect, new EjercicioExtractor());
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> findExercisesByName(String nombre) {
        String sqlSelect = "SELECT e.id_ejercicio, e.nombre_ejercicio, e.equipo, e.series, e.repeticiones, " +
                           "c.nombre_categoria AS categoria, r.id_rutina " +
                           "FROM Ejercicio e " +
                           "JOIN CategoriaEjercicio c ON e.cod_categoria = c.cod_categoria " +
                           "JOIN Rutina r ON e.id_rutina = r.id_rutina " +
                           "WHERE e.nombre_ejercicio LIKE ? OR c.nombre_categoria LIKE ?";

        return jdbcTemplate.query(sqlSelect, new EjercicioExtractor(), "%" + nombre + "%", "%" + nombre + "%");
    }

    @Transactional(readOnly = true)
    public Ejercicio findEjercicioById(int codEjercicio) {
        String sql = "SELECT e.id_ejercicio, e.nombre_ejercicio, e.equipo, e.series, e.repeticiones, " +
                     "c.nombre_categoria AS categoria, r.id_rutina " +
                     "FROM Ejercicio e " +
                     "JOIN CategoriaEjercicio c ON e.cod_categoria = c.cod_categoria " +
                     "JOIN Rutina r ON e.id_rutina = r.id_rutina " +
                     "WHERE e.id_ejercicio = ?";

        List<Ejercicio> ejercicios = jdbcTemplate.query(sql, new EjercicioExtractor(), codEjercicio);
        return ejercicios.isEmpty() ? null : ejercicios.get(0);
    }

    @Transactional
    public void modificarEjercicio(int codEjercicio) {
        // 1. Obtener el ejercicio actual
        Ejercicio ejercicioExistente = findEjercicioById(codEjercicio);

        if (ejercicioExistente != null) {
            // 2. Simulación de la actualización con los datos existentes (en un escenario real, esto vendría de la vista)
            String nuevoNombre = ejercicioExistente.getNombreEjercicio();
            String nuevoEquipo = ejercicioExistente.getEquipo();
            int nuevasSeries = ejercicioExistente.getSeries();
            int nuevasRepeticiones = ejercicioExistente.getRepeticiones();

            // Suponiendo que solo se actualiza la primera categoría de la lista
            int nuevaCategoria = ejercicioExistente.getCategoriaEjercicio() != null &&
                                 !ejercicioExistente.getCategoriaEjercicio().isEmpty()
                                 ? ejercicioExistente.getCategoriaEjercicio().get(0).getCodCategoria()
                                 : 0;

            // 3. Ejecutar el UPDATE (ajusta los nombres de columnas según tu base de datos)
            String sqlUpdate = "UPDATE Ejercicio " +
                               "SET nombre_ejercicio = ?, equipo = ?, series = ?, repeticiones = ?, cod_categoria = ? " +
                               "WHERE cod_ejercicio = ?";

            jdbcTemplate.update(sqlUpdate,
                nuevoNombre,
                nuevoEquipo,
                nuevasSeries,
                nuevasRepeticiones,
                nuevaCategoria,
                codEjercicio
            );
        }
    }


    @Transactional
    public void insertarEjercicio(Ejercicio ejercicio) {
        String sqlInsert = "INSERT INTO Ejercicio (nombre_ejercicio, equipo, series, repeticiones, cod_categoria, id_rutina) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlInsert,
                ejercicio.getNombreEjercicio(),
                ejercicio.getEquipo(),
                ejercicio.getSeries(),
                ejercicio.getRepeticiones(),
                ejercicio.getCategoriaEjercicio().get(0).getCodCategoria(),
                ejercicio.getRutina().get(0).getIdRutina());
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
