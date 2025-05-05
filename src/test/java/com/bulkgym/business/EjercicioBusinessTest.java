package com.bulkgym.business;

import com.bulkgym.data.EjercicioData;
import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EjercicioBusinessTest {

    @Mock
    private EjercicioData ejercicioData;

    @InjectMocks
    private EjercicioBusiness ejercicioBusiness;

    private List<Ejercicio> listaEjercicios;

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
    }

    @Test
    public void testInsertarEjercicio() {
        Ejercicio nuevo = new Ejercicio();
        nuevo.setNombreEjercicio("Abdominales");

        ejercicioBusiness.insertarEjercicio(nuevo);

        verify(ejercicioData).guardar(nuevo);
    }

    @Test
    public void testEliminarEjercicio() {
        boolean result = ejercicioBusiness.eliminarEjercicioC(1);

        verify(ejercicioData).delete(1);
        assertTrue(result);
    }

    @Test
    public void testModificarEjercicioPorId() {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(2);
        ejercicio.setNombreEjercicio("ViejoNombre");

        when(ejercicioData.findById(2)).thenReturn(ejercicio);

        ejercicioBusiness.modificarEjercicioPorId(2, "NuevoNombre", "nueva.jpg", 10);

        assertEquals("NuevoNombre", ejercicio.getNombreEjercicio());
        assertEquals("nueva.jpg", ejercicio.getImagen());
        assertEquals(10, ejercicio.getCategoriaEjercicio().get(0).getIdCategoriaEjercicio());

        verify(ejercicioData).update(ejercicio);
    }

    @Test
    public void testModificarEjercicioPorIdNoExistente() {
        when(ejercicioData.findById(99)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ejercicioBusiness.modificarEjercicioPorId(99, "NuevoNombre", "nueva.jpg", 5);
        });

        assertEquals("El ejercicio con ID 99 no existe.", exception.getMessage());
    }

    @Test
    public void testFindAllExercises() {
        when(ejercicioData.findAll()).thenReturn(listaEjercicios);

        List<Ejercicio> resultado = ejercicioBusiness.findAllExercises();

        assertEquals(2, resultado.size());
        assertEquals("Flexiones", resultado.get(0).getNombreEjercicio());
        assertEquals("Sentadillas", resultado.get(1).getNombreEjercicio());
    }

    @Test
    public void testContarEjercicios() {
        when(ejercicioData.contarEjercicios()).thenReturn(2);

        int total = ejercicioBusiness.contarEjercicios();

        assertEquals(2, total);
    }

    @Test
    public void testContarCategorias() {
        when(ejercicioData.contarCategorias()).thenReturn(3);

        int total = ejercicioBusiness.contarCategorias();

        assertEquals(3, total);
    }
}
