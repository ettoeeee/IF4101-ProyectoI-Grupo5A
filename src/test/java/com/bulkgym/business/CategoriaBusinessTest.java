package com.bulkgym.business;

import com.bulkgym.data.CategoriaEjercicioData;
import com.bulkgym.domain.CategoriaEjercicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaBusinessTest {

    @Mock
    private CategoriaEjercicioData categoriaData;

    @InjectMocks
    private CategoriaBusiness categoriaBusiness;

    private CategoriaEjercicio cat1;
    private CategoriaEjercicio cat2;

    @BeforeEach
    public void setUp() {
        cat1 = new CategoriaEjercicio();
        cat1.setIdCategoriaEjercicio(1);
        cat1.setNombreCategoria("Pecho");

        cat2 = new CategoriaEjercicio();
        cat2.setIdCategoriaEjercicio(2);
        cat2.setNombreCategoria("Espalda");
    }

    @Test
    public void testObtenerTodas() {
        when(categoriaData.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        List<CategoriaEjercicio> resultado = categoriaBusiness.obtenerTodas();

        assertEquals(2, resultado.size());
        assertEquals("Pecho", resultado.get(0).getNombreCategoria());
        verify(categoriaData).findAll();
    }

    @Test
    public void testObtenerPorId() {
        when(categoriaData.findById(1)).thenReturn(cat1);

        CategoriaEjercicio resultado = categoriaBusiness.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals("Pecho", resultado.getNombreCategoria());
        verify(categoriaData).findById(1);
    }

    @Test
    public void testGuardarCategoria() {
        categoriaBusiness.guardar(cat1);

        verify(categoriaData).guardar(cat1);
    }

    @Test
    public void testActualizarCategoria() {
        categoriaBusiness.actualizar(cat2);

        verify(categoriaData).update(cat2);
    }

    @Test
    public void testEliminarCategoria() {
        categoriaBusiness.eliminar(2);

        verify(categoriaData).delete(2);
    }
}

