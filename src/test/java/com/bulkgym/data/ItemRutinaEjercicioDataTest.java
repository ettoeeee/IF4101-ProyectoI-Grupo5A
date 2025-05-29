package com.bulkgym.data;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bulkgym.data.ItemRutinaEjercicioData;
import com.bulkgym.domain.ItemRutinaEjercicio;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class ItemRutinaEjercicioDataTest {

    private JdbcTemplate jdbcTemplate;
    private ItemRutinaEjercicioData data;

    @BeforeEach
    void setUp() throws Exception {
        jdbcTemplate = mock(JdbcTemplate.class);
        data = new ItemRutinaEjercicioData();

        // Inyectar el jdbcTemplate mock via reflexión (porque el campo es privado)
        var field = ItemRutinaEjercicioData.class.getDeclaredField("jdbcTemplate");
        field.setAccessible(true);
        field.set(data, jdbcTemplate);
    }

    @Test
    void testFindAll() {
        List<ItemRutinaEjercicio> expectedList = new ArrayList<>();
        expectedList.add(new ItemRutinaEjercicio());

        when(jdbcTemplate.query(anyString(), any(ResultSetExtractor.class))).thenReturn(expectedList);

        List<ItemRutinaEjercicio> result = data.findAll();

        assertNotNull(result);
        assertEquals(expectedList.size(), result.size());

        verify(jdbcTemplate).query(anyString(), any(ResultSetExtractor.class));
    }


    @Test
    public void guardar() {
    	ItemRutinaEjercicio item = new ItemRutinaEjercicio();
        item.setIdRutina(1);
        item.setIdEjercicio(2);
        item.setSeriesEjercicio(3);
        item.setRepeticionesEjercicio(4);
        item.setEquipoEjercicio("EquipoX");
    }

    
    @Test
    void testUpdate() {
        ItemRutinaEjercicio item = new ItemRutinaEjercicio();
        item.setIdRutina(1);
        item.setIdEjercicio(2);
        item.setSeriesEjercicio(3);
        item.setRepeticionesEjercicio(4);
        item.setEquipoEjercicio("EquipoX");

        data.update(item);

        verify(jdbcTemplate).update(
            anyString(),
            eq(3),
            eq(4),
            eq("EquipoX"),
            eq(1),
            eq(2)
        );
    }

    @Test
    void testDelete() {
        int idRutina = 1;
        int idEjercicio = 2;

        data.delete(idRutina, idEjercicio);

        verify(jdbcTemplate).update(anyString(), eq(idRutina), eq(idEjercicio));
    }

    @Test
    void testFindById() {
        int idRutina = 1;
        int idEjercicio = 2;

        List<ItemRutinaEjercicio> mockedList = new ArrayList<>();
        ItemRutinaEjercicio expected = new ItemRutinaEjercicio();
        mockedList.add(expected);

        when(jdbcTemplate.query(anyString(), any(ResultSetExtractor.class), any(Object[].class)))
            .thenReturn(mockedList);

        ItemRutinaEjercicio result = data.findById(idRutina, idEjercicio);

        assertNotNull(result);
        assertEquals(expected, result);

        // Caso cuando no encuentra nada
        when(jdbcTemplate.query(anyString(), any(ResultSetExtractor.class), any(Object[].class)))
            .thenReturn(new ArrayList<>());

        result = data.findById(idRutina, idEjercicio);
        assertNull(result);
    }



    @Test
    void testContarItems() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(42);

        int count = data.contarItems();

        assertEquals(42, count);

        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class));
    }
}
