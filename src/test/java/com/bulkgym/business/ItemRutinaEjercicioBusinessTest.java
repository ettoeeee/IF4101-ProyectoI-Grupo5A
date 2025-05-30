package com.bulkgym.business;

import com.bulkgym.data.ItemRutinaEjercicioData;
import com.bulkgym.domain.ItemRutinaEjercicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemRutinaEjercicioBusinessTest {

    @InjectMocks
    private ItemRutinaEjercicioBusiness business;

    @Mock
    private ItemRutinaEjercicioData data;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllItems() {
        ItemRutinaEjercicio item1 = new ItemRutinaEjercicio(1, 101, 3, 12, "Barra");
        ItemRutinaEjercicio item2 = new ItemRutinaEjercicio(2, 102, 4, 10, "Mancuerna");
        List<ItemRutinaEjercicio> mockList = Arrays.asList(item1, item2);

        when(data.findAll()).thenReturn(mockList);

        List<ItemRutinaEjercicio> result = business.findAllItems();

        assertEquals(2, result.size());
        verify(data, times(1)).findAll();
    }

    @Test
    void testInsertarItem() {
        ItemRutinaEjercicio item = new ItemRutinaEjercicio(1, 101, 3, 15, "Banco");

        doNothing().when(data).guardar(item);

        business.insertarItem(item);

        verify(data, times(1)).guardar(item);
    }

    @Test
    void testEliminarItem() {
        int idRutina = 1;
        int idEjercicio = 101;

        doNothing().when(data).delete(idRutina, idEjercicio);

        boolean result = business.eliminarItem(idRutina, idEjercicio);

        assertTrue(result);
        verify(data).delete(idRutina, idEjercicio);
    }

    @Test
    void testModificarItemPorIdExistente() {
        int idRutina = 1;
        int idEjercicio = 101;

        ItemRutinaEjercicio item = new ItemRutinaEjercicio(idRutina, idEjercicio, 3, 10, "Cuerda");

        when(data.findById(idRutina, idEjercicio)).thenReturn(item);

        business.modificarItemPorId(idRutina, idEjercicio, 5, 20, "Polea");

        assertEquals(5, item.getSeriesEjercicio());
        assertEquals(20, item.getRepeticionesEjercicio());
        assertEquals("Polea", item.getEquipoEjercicio());

        verify(data).update(item);
    }

    @Test
    void testModificarItemPorIdInexistente() {
        int idRutina = 1;
        int idEjercicio = 999;

        when(data.findById(idRutina, idEjercicio)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            business.modificarItemPorId(idRutina, idEjercicio, 5, 20, "Polea");
        });

        assertTrue(exception.getMessage().contains("no existe"));
        verify(data, never()).update(any());
    }

    @Test
    void testBuscarItemPorId() {
        ItemRutinaEjercicio item = new ItemRutinaEjercicio(1, 101, 3, 12, "Máquina");

        when(data.findById(1, 101)).thenReturn(item);

        ItemRutinaEjercicio result = business.buscarItemPorId(1, 101);

        assertNotNull(result);
        assertEquals(101, result.getIdEjercicio());
        verify(data).findById(1, 101);
    }

    @Test
    void testContarItems() {
        when(data.contarItems()).thenReturn(5);

        int result = business.contarItems();

        assertEquals(5, result);
        verify(data).contarItems();
    }

    @BeforeEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }
}
