package com.bulkgym.data;

import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ItemRutinaMedidaDataTest {

    @InjectMocks
    private ItemRutinaMedidaData itemData;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(ItemRutinaMedidaExtractor.class)))
                .thenReturn(List.of(new ItemRutinaMedida()));

        List<ItemRutinaMedida> result = itemData.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(jdbcTemplate).query(anyString(), any(ItemRutinaMedidaExtractor.class));
    }

    @Test
    void testFindById() {
        int id = 1;
        when(jdbcTemplate.query(anyString(), any(ItemRutinaMedidaExtractor.class), eq(id)))
                .thenReturn(List.of(new ItemRutinaMedida()));

        ItemRutinaMedida result = itemData.findById(id);

        assertNotNull(result);
        verify(jdbcTemplate).query(anyString(), any(ItemRutinaMedidaExtractor.class), eq(id));
    }

    @Test
    void testGuardar() {
        ItemRutinaMedida item = new ItemRutinaMedida();
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(10);
        Rutina rutina = new Rutina();
        rutina.setIdRutina(20);

        item.setValorMedida(85.5);
        item.setMedidaCorporal(medida);
        item.setRutina(rutina);

        when(jdbcTemplate.update(any(), any(GeneratedKeyHolder.class))).thenReturn(1);

        itemData.guardar(item);

        // No hay assert aquí porque el ID generado se maneja por KeyHolder mockeado sin efecto.
        verify(jdbcTemplate).update(any(), any(GeneratedKeyHolder.class));
    }

    @Test
    void testUpdate() {
        ItemRutinaMedida item = new ItemRutinaMedida();
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(15);
        Rutina rutina = new Rutina();
        rutina.setIdRutina(5);

        item.setIdItemRutinaMedida(2);
        item.setValorMedida(65.0);
        item.setMedidaCorporal(medida);
        item.setRutina(rutina);

        when(jdbcTemplate.update(anyString(), anyDouble(), anyInt(), anyInt(), anyInt())).thenReturn(1);

        boolean updated = itemData.update(item);

        assertTrue(updated);
        verify(jdbcTemplate).update(anyString(), anyDouble(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testDelete() {
        int id = 99;
        when(jdbcTemplate.update(anyString(), eq(id))).thenReturn(1);

        boolean deleted = itemData.delete(id);

        assertTrue(deleted);
        verify(jdbcTemplate).update(anyString(), eq(id));
    }
}
