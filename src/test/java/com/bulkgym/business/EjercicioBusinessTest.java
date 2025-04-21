package com.bulkgym.business;

import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EjercicioBusinessTest {

    private List<Ejercicio> listaSimulada;
    private EjercicioBusiness ejercicioBusiness;

    @BeforeEach
    public void setUp() {
        listaSimulada = new ArrayList<>();

        CategoriaEjercicio cat = new CategoriaEjercicio();
        cat.setCodCategoria(1);
        cat.setNombreCategoria("Pecho");

        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setIdEjercicio(1);
        ejercicio1.setNombreEjercicio("Press banca");
        ejercicio1.setCategoriaEjercicio(cat);

        Ejercicio ejercicio2 = new Ejercicio();
        ejercicio2.setIdEjercicio(2);
        ejercicio2.setNombreEjercicio("Aperturas");
        ejercicio2.setCategoriaEjercicio(cat);

        listaSimulada.add(ejercicio1);
        listaSimulada.add(ejercicio2);

        // Creamos una clase anónima simple para simular el comportamiento de Business
        ejercicioBusiness = new EjercicioBusiness() {
            @Override
            public List<Ejercicio> findAllExercises() {
                return listaSimulada;
            }

            @Override
            public void insertarEjercicio(Ejercicio ejercicio) {
                ejercicio.setIdEjercicio(listaSimulada.size() + 1); // simulación de auto incremento
                listaSimulada.add(ejercicio);
            }

            @Override
            public boolean eliminarEjercicioC(int codEjercicio) {
                return listaSimulada.removeIf(e -> e.getIdEjercicio() == codEjercicio);
            }

            @Override
            public void modificarEjercicioPorId(int codEjercicio) {
                for (Ejercicio e : listaSimulada) {
                    if (e.getIdEjercicio() == codEjercicio) {
                        e.setNombreEjercicio("Modificado");
                        return;
                    }
                }
            }
        };
    }

    @Test
    public void testInsertarEjercicio() {
        Ejercicio nuevo = new Ejercicio();
        nuevo.setNombreEjercicio("Remo con barra");
        nuevo.setCategoriaEjercicio(new CategoriaEjercicio(2, "Espalda", new ArrayList<>()));

        ejercicioBusiness.insertarEjercicio(nuevo);

        assertEquals(3, listaSimulada.size());
        assertEquals("Remo con barra", listaSimulada.get(2).getNombreEjercicio());
    }

    @Test
    public void testEliminarEjercicioExistente() {
        boolean resultado = ejercicioBusiness.eliminarEjercicioC(1);
        assertTrue(resultado);
        assertEquals(1, listaSimulada.size());
    }

    @Test
    public void testEliminarEjercicioNoExistente() {
        boolean resultado = ejercicioBusiness.eliminarEjercicioC(99);
        assertFalse(resultado);
    }

    @Test
    public void testModificarEjercicio() {
        ejercicioBusiness.modificarEjercicioPorId(2);
        assertEquals("Modificado", listaSimulada.get(1).getNombreEjercicio());
    }
}
