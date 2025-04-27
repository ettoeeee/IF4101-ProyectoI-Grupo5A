package com.bulkgym.business;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.data.EjercicioData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EjercicioBusinessTest {

    private List<Ejercicio> listaEjercicios;
    private EjercicioBusiness ejercicioBusiness;

    /*
    @BeforeEach
    public void setUp() {
        listaEjercicios = new ArrayList<>();

        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setIdEjercicio(1);
        ejercicio1.setNombreEjercicio("Flexiones");

        Ejercicio ejercicio2 = new Ejercicio();
        ejercicio2.setIdEjercicio(2);
        ejercicio2.setNombreEjercicio("Sentadillas");

        listaEjercicios.add(ejercicio1);
        listaEjercicios.add(ejercicio2);

        ejercicioBusiness = new EjercicioBusiness() {
            @Override
            public List<Ejercicio> findAllExercises() {
                return listaEjercicios;
            }

            @Override
            public void insertarEjercicio(Ejercicio ejercicio) {
                ejercicio.setIdEjercicio(listaEjercicios.size() + 1); // Simulación de auto-incremento
                listaEjercicios.add(ejercicio);
            }

            @Override
            public boolean eliminarEjercicioC(int idEjercicio) {
                return listaEjercicios.removeIf(e -> e.getIdEjercicio() == idEjercicio);
            }

            @Override
            public void modificarEjercicioPorId(int idEjercicio) {
                for (Ejercicio e : listaEjercicios) {
                    if (e.getIdEjercicio() == idEjercicio) {
                        e.setNombreEjercicio("Modificado");
                    }
                }
            }
        };
    }

    @Test
    public void testInsertarEjercicio() {
        Ejercicio nuevo = new Ejercicio();
        nuevo.setNombreEjercicio("Abdominales");

        ejercicioBusiness.insertarEjercicio(nuevo);

        assertEquals(3, listaEjercicios.size());
        assertEquals("Abdominales", listaEjercicios.get(2).getNombreEjercicio());
    }

    @Test
    public void testEliminarEjercicioExistente() {
        boolean resultado = ejercicioBusiness.eliminarEjercicioC(1);

        assertTrue(resultado);
        assertEquals(1, listaEjercicios.size());
    }

    @Test
    public void testEliminarEjercicioNoExistente() {
        boolean resultado = ejercicioBusiness.eliminarEjercicioC(99);

        assertFalse(resultado);
        assertEquals(2, listaEjercicios.size());
    }

    @Test
    public void testModificarEjercicio() {
        ejercicioBusiness.modificarEjercicioPorId(2);

        assertEquals("Modificado", listaEjercicios.get(1).getNombreEjercicio());
    }

    @Test
    public void testBuscarTodosEjercicios() {
        List<Ejercicio> resultado = ejercicioBusiness.findAllExercises();

        assertEquals(2, resultado.size());
        assertEquals("Flexiones", resultado.get(0).getNombreEjercicio());
        assertEquals("Sentadillas", resultado.get(1).getNombreEjercicio());
    }
    */
}

