package com.bulkgym.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bulkgym.domain.Instructor;

import java.util.*;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class InstructorDataTest {

    private List<Instructor> baseDeDatosFalsa;

    @BeforeEach
    void setUp() {
        baseDeDatosFalsa = new ArrayList<>();
    }

    @Test
    void testCrearInstructor() {
        Instructor instructor = construirInstructor(1, 100);
        baseDeDatosFalsa.add(instructor);

        assertEquals(1, baseDeDatosFalsa.size());
        assertEquals("Carlos", baseDeDatosFalsa.get(0).getNombre());
    }

    @Test
    void testBuscarInstructorPorId() {
        baseDeDatosFalsa.add(construirInstructor(1, 100));
        baseDeDatosFalsa.add(construirInstructor(2, 101));

        Optional<Instructor> encontrado = baseDeDatosFalsa.stream()
                .filter(i -> i.getIdInstructor() == 2)
                .findFirst();

        assertTrue(encontrado.isPresent());
        assertEquals(101, encontrado.get().getIdPersona());
    }

    @Test
    void testActualizarInstructor() {
        Instructor original = construirInstructor(1, 100);
        baseDeDatosFalsa.add(original);

        original.setNombre("Luis");
        original.setFechaIngreso(Date.valueOf("2023-03-01"));

        Instructor actualizado = baseDeDatosFalsa.get(0);
        assertEquals("Luis", actualizado.getNombre());
        assertEquals(Date.valueOf("2023-03-01"), actualizado.getFechaIngreso());
    }

    @Test
    void testEliminarInstructor() {
        baseDeDatosFalsa.add(construirInstructor(1, 100));
        baseDeDatosFalsa.add(construirInstructor(2, 101));

        baseDeDatosFalsa.removeIf(i -> i.getIdInstructor() == 2);

        assertEquals(1, baseDeDatosFalsa.size());
        assertEquals(1, baseDeDatosFalsa.get(0).getIdInstructor());
    }

    private Instructor construirInstructor(int idInstructor, int idPersona) {
        Instructor i = new Instructor();
        i.setIdInstructor(idInstructor);
        i.setIdPersona(idPersona);
        i.setNombre("Carlos");
        i.setApellidos("Ramírez");
        i.setFechaNacimiento(Date.valueOf("1990-05-15"));
        i.setSexo('M');
        i.setTelefono("8888-8888");
        i.setCorreoElectronico("carlos@correo.com");
        i.setImagenRuta("foto.jpg");
        i.setFechaIngreso(Date.valueOf("2024-01-01"));
        return i;
    }

}
