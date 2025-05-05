package com.bulkgym.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.bulkgym.domain.Instructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class InstructorDataTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private InstructorData instructorData;

    private Instructor ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Instructor();
        ejemplo.setIdInstructor(42);
        ejemplo.setIdPersona(7);
        ejemplo.setNombre("María");
        ejemplo.setApellidos("López");
        ejemplo.setFechaNacimiento(Date.valueOf(LocalDate.of(1990, 5, 15)));
        ejemplo.setSexo('F');
        ejemplo.setTelefono("555-1234");
        ejemplo.setCorreoElectronico("maria@ejemplo.com");
        ejemplo.setImagenRuta("imagen.png");
        ejemplo.setFechaIngreso(Date.valueOf(LocalDate.of(2022, 1, 1)));
    }

    @Test
    void listarTodos_deberíaDelegarYDevolverLaLista() {
        List<Instructor> listaMock = List.of(ejemplo);
        when(jdbcTemplate.query(
                contains("SELECT i.id_instructor"), 
                any(InstructorExtractor.class))
        ).thenReturn(listaMock);

        List<Instructor> resultado = instructorData.listarTodos();

        assertSame(listaMock, resultado);
        verify(jdbcTemplate).query(
            contains("FROM Instructor i"), 
            any(InstructorExtractor.class)
        );
    }

    @Test
    void buscarPorId_cuandoExisteDeberíaDevolverInstructor() {
        when(jdbcTemplate.query(
                contains("WHERE i.id_instructor = ?"), 
                any(InstructorExtractor.class), 
                eq(ejemplo.getIdInstructor()))
        ).thenReturn(List.of(ejemplo));

        Instructor encontrado = instructorData.buscarPorId(ejemplo.getIdInstructor());
        assertEquals(ejemplo, encontrado);

        verify(jdbcTemplate).query(
            contains("WHERE i.id_instructor = ?"),
            any(InstructorExtractor.class),
            eq(ejemplo.getIdInstructor())
        );
    }

    @Test
    void buscarPorId_cuandoNoExisteDeberíaDevolverNull() {
        when(jdbcTemplate.query(
                anyString(), 
                any(InstructorExtractor.class), 
                anyInt())
        ).thenReturn(Collections.emptyList());

        assertNull(instructorData.buscarPorId(999));
    }

    @Test
    void actualizar_deberíaActualizarPersonaYInstructor() {
        // Stub de ambos updates
        when(jdbcTemplate.update(contains("UPDATE Persona"), 
                anyString(), anyString(), any(), anyString(), anyString(), anyString(), anyString(), anyInt()))
        .thenReturn(1);
        when(jdbcTemplate.update(contains("UPDATE Instructor"),
                any(), anyInt()))
        .thenReturn(1);

        instructorData.actualizar(ejemplo);

        verify(jdbcTemplate).update(contains("UPDATE Persona"),
            eq(ejemplo.getNombre()),
            eq(ejemplo.getApellidos()),
            eq(ejemplo.getFechaNacimiento()),
            eq(String.valueOf(ejemplo.getSexo())),
            eq(ejemplo.getTelefono()),
            eq(ejemplo.getCorreoElectronico()),
            eq(ejemplo.getImagenRuta()),
            eq(ejemplo.getIdPersona())
        );
        verify(jdbcTemplate).update(contains("UPDATE Instructor"),
            eq(ejemplo.getFechaIngreso()),
            eq(ejemplo.getIdInstructor())
        );
    }

    @Test
    void eliminar_deberíaLlamarDeleteEnOrden() {
        instructorData.eliminar(ejemplo.getIdInstructor(), ejemplo.getIdPersona());

        // Primero borra Instructor
        verify(jdbcTemplate).update("DELETE FROM Instructor WHERE id_instructor = ?", ejemplo.getIdInstructor());
        // Luego borra Persona
        verify(jdbcTemplate).update("DELETE FROM Persona WHERE id_persona = ?", ejemplo.getIdPersona());
    }
}
