package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.Instructor;

@Repository
public class InstructorData {
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
    @Transactional(readOnly = true)
    public List<Instructor> listarTodos() {
        String sql = """
            SELECT i.id_instructor, i.fecha_ingreso,
                   p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
                   p.sexo, p.telefono, p.correo_electronico, p.imagen
            FROM Instructor i
            JOIN Persona p ON i.id_persona = p.id_persona
        """;
        return jdbcTemplate.query(sql, new InstructorExtractor());
    }

    @Transactional
    public void crear(Instructor instructor) {
        // Insertar en Persona
        String sqlPersona = """
            INSERT INTO Persona (nombre, apellidos, fecha_nacimiento, sexo, telefono, correo_electronico, imagen)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sqlPersona,
            instructor.getNombre(),
            instructor.getApellidos(),
            instructor.getFechaNacimiento(),
            String.valueOf(instructor.getSexo()),
            instructor.getTelefono(),
            instructor.getCorreoElectronico(),
            instructor.getImagenRuta()
        );

        // Obtener el último ID insertado (esto depende del motor: aquí usamos SQL Server)
        Integer idPersona = jdbcTemplate.queryForObject("SELECT MAX(id_persona) FROM Persona", Integer.class);

        // Insertar en Instructor
        String sqlInstructor = """
            INSERT INTO Instructor (id_persona, fecha_ingreso)
            VALUES (?, ?)
        """;
        jdbcTemplate.update(sqlInstructor,
            idPersona,
            instructor.getFechaIngreso()
        );
    }

    @Transactional
    public void actualizar(Instructor instructor) {
        String sqlPersona = """
            UPDATE Persona
            SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, sexo = ?, telefono = ?, correo_electronico = ?, imagen = ?
            WHERE id_persona = ?
        """;

        jdbcTemplate.update(sqlPersona,
            instructor.getNombre(),
            instructor.getApellidos(),
            instructor.getFechaNacimiento(),
            String.valueOf(instructor.getSexo()),
            instructor.getTelefono(),
            instructor.getCorreoElectronico(),
            instructor.getImagenRuta(),
            instructor.getIdPersona()
        );

        String sqlInstructor = """
            UPDATE Instructor
            SET fecha_ingreso = ?
            WHERE id_instructor = ?
        """;

        jdbcTemplate.update(sqlInstructor,
            instructor.getFechaIngreso(),
            instructor.getIdInstructor()
        );
    }

    @Transactional
    public void eliminar(int idInstructor, int idPersona) {
        // Eliminar de Instructor primero
        String sqlInstructor = "DELETE FROM Instructor WHERE id_instructor = ?";
        jdbcTemplate.update(sqlInstructor, idInstructor);

        // Luego eliminar de Persona
        String sqlPersona = "DELETE FROM Persona WHERE id_persona = ?";
        jdbcTemplate.update(sqlPersona, idPersona);
    }

    @Transactional(readOnly = true)
    public Instructor buscarPorId(int idInstructor) {
        String sql = """
            SELECT i.id_instructor, i.fecha_ingreso,
                   p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
                   p.sexo, p.telefono, p.correo_electronico, p.imagen
            FROM Instructor i
            JOIN Persona p ON i.id_persona = p.id_persona
            WHERE i.id_instructor = ?
        """;

        List<Instructor> lista = jdbcTemplate.query(sql, new InstructorExtractor(), idInstructor);
        return lista.isEmpty() ? null : lista.get(0);
    }
}
