package com.bulkgym.business;

import com.bulkgym.data.ItemRutinaMedidaData;
import com.bulkgym.domain.ItemRutinaMedida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemRutinaMedidaBusinessTest {

    @InjectMocks
    private ItemRutinaMedidaBusiness business;

    @Mock
    private ItemRutinaMedidaData data;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        when(data.findAll()).thenReturn(List.of(new ItemRutinaMedida()));

        List<ItemRutinaMedida> result = business.obtenerTodas();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(data).findAll();
    }

    @Test
    void testObtenerPorId() {
        int id = 1;
        when(data.findById(id)).thenReturn(new ItemRutinaMedida());

        ItemRutinaMedida result = business.obtenerPorId(id);

        assertNotNull(result);
        verify(data).findById(id);
    }

    @Test
    void testGuardar() {
        ItemRutinaMedida item = new ItemRutinaMedida();

        business.guardar(item);

        verify(data).guardar(item);
    }

    @Test
    void testActualizar() {
        ItemRutinaMedida item = new ItemRutinaMedida();

        business.actualizar(item);

        verify(data).update(item);
    }

    @Test
    void testEliminar() {
        int id = 42;

        business.eliminar(id);

        verify(data).delete(id);
    }
}
