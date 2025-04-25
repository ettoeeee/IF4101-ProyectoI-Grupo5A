package com.bulkgym.business;

import com.bulkgym.domain.CategoriaEjercicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaBusinessTest {

    private List<CategoriaEjercicio> listaCategorias;
    private CategoriaBusiness categoriaBusiness;

    @BeforeEach
    public void setUp() {
        listaCategorias = new ArrayList<>();

        CategoriaEjercicio cat1 = new CategoriaEjercicio();
        cat1.setCodCategoria(1);
        cat1.setNombreCategoria("Pecho");

        CategoriaEjercicio cat2 = new CategoriaEjercicio();
        cat2.setCodCategoria(2);
        cat2.setNombreCategoria("Espalda");

        listaCategorias.add(cat1);
        listaCategorias.add(cat2);

        categoriaBusiness = new CategoriaBusiness() {
            @Override
            public List<CategoriaEjercicio> buscarCategoriaPorId(int idCategoria) {
                List<CategoriaEjercicio> resultado = new ArrayList<>();
                for (CategoriaEjercicio c : listaCategorias) {
                    if (c.getCodCategoria() == idCategoria) {
                        resultado.add(c);
                    }
                }
                return resultado;
            }

            @Override
            public boolean eliminarCategoria(CategoriaEjercicio categoria) {
                return listaCategorias.removeIf(c -> c.getCodCategoria() == categoria.getCodCategoria());
            }

            @Override
            public CategoriaEjercicio modificarCategoria(int idCategoria) {
                for (CategoriaEjercicio c : listaCategorias) {
                    if (c.getCodCategoria() == idCategoria) {
                        c.setNombreCategoria("Modificado");
                        return c;
                    }
                }
                return null;
            }

            @Override
            public void insertarCategoria(CategoriaEjercicio categoria) {
                categoria.setCodCategoria(listaCategorias.size() + 1); // simulación de auto-incremento
                listaCategorias.add(categoria);
            }
        };
    }

    @Test
    public void testInsertarCategoria() {
        CategoriaEjercicio nueva = new CategoriaEjercicio();
        nueva.setNombreCategoria("Pierna");

        categoriaBusiness.insertarCategoria(nueva);

        assertEquals(3, listaCategorias.size());
        assertEquals("Pierna", listaCategorias.get(2).getNombreCategoria());
    }

    @Test
    public void testEliminarCategoriaExistente() {
        CategoriaEjercicio aEliminar = new CategoriaEjercicio();
        aEliminar.setCodCategoria(1);

        boolean resultado = categoriaBusiness.eliminarCategoria(aEliminar);
        assertTrue(resultado);
        assertEquals(1, listaCategorias.size());
    }

    @Test
    public void testEliminarCategoriaNoExistente() {
        CategoriaEjercicio inexistente = new CategoriaEjercicio();
        inexistente.setCodCategoria(99);

        boolean resultado = categoriaBusiness.eliminarCategoria(inexistente);
        assertFalse(resultado);
        assertEquals(2, listaCategorias.size());
    }

    @Test
    public void testModificarCategoria() {
        CategoriaEjercicio modificada = categoriaBusiness.modificarCategoria(2);

        assertNotNull(modificada);
        assertEquals("Modificado", modificada.getNombreCategoria());
    }

    @Test
    public void testBuscarCategoriaPorId() {
        List<CategoriaEjercicio> resultado = categoriaBusiness.buscarCategoriaPorId(1);

        assertEquals(1, resultado.size());
        assertEquals("Pecho", resultado.get(0).getNombreCategoria());
    }
}

