package com.bulkgym.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bulkgym.domain.Rutina;

@ExtendWith(MockitoExtension.class)
class RutinaDataTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RutinaData rutinaData;

    private Rutina rutina;

    @BeforeEach
    void setUp() {
        rutina = new Rutina();
        rutina.setIdCliente(42);
        rutina.setFechaCreacion(Date.valueOf("2025-05-30"));
        rutina.setFechaRenovacion(Date.valueOf("2025-06-30"));
        rutina.setHorario("Mañana");
        rutina.setObjetivo("Fuerza");
        rutina.setLesiones("Ninguna");
        rutina.setPadecimientos("Ninguno");
    }

    @Test
    void insertarRutina_deberiaRetornarIdGenerado() {
        // Cuando llama al update del jdbc, se simula un id de mentiras para el keyholder
        doAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(1);
            Map<String, Object> keys = new HashMap<>();
            keys.put("id_rutina", 99);
            ((GeneratedKeyHolder) kh).getKeyList().add(keys);
            return 1;  // filas afectadas
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        int id = rutinaData.insertarRutina(rutina);

        assertEquals(99, id, "El ID devuelto debe venir del KeyHolder");
        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }
}
