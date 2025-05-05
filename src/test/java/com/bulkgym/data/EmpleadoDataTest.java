package com.bulkgym.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import com.bulkgym.dto.EmpleadoDTO;
import com.bulkgym.domain.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmpleadoDataTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private EmpleadoData empleadoData;

    private EmpleadoDTO dto;

    @BeforeEach
    void setUp() {
        dto = new EmpleadoDTO();
        dto.setIdPersona(42);
        dto.setIdEmpleado(7);
        dto.setNombre("Ana");
        dto.setApellidos("Pérez");
        dto.setFechaNacimiento(java.sql.Date.valueOf("1985-12-01"));
        dto.setSexo('F');
        dto.setTelefono("555-1234");
        dto.setCorreoElectronico("ana@ejemplo.com");
        dto.setImagenRuta("data:image/png;base64,...");
        dto.setDireccion("Calle Falsa 123");
        dto.setNombreContactoEmergencia("Luis Pérez");
        dto.setTelContactoEmergencia("555-5678");
        dto.setRolEmpleado("Técnico");
    }

    @Test
    void updateEmpleado_deberíaRetornarFalseSiNingunaActualizaciónAfectaFilas() {
        lenient().when(jdbcTemplate.update(
        		anyString(), 
        		any(), 
        		anyInt()))
        	.thenReturn(0);

        boolean resultado = empleadoData.updateEmpleado(dto);
        assertFalse(resultado, "Debe devolver false si ninguna de las dos actualizaciones afecta filas");
    }

    @Test
    void updateEmpleado_deberíaLanzarIllegalArgumentException_sinRol() {
        dto.setRolEmpleado(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            empleadoData.updateEmpleado(dto);
        });
        assertEquals("Cada empleado DEBE tener un rol.", ex.getMessage());
        verifyNoInteractions(jdbcTemplate);
    }

    @Test
    void findById_deberíaDevolverNull_siNoHayResultados() {
        // simula que query lanza EmptyResultDataAccessException
        when(jdbcTemplate.query(
                anyString(),
                any(EmpleadoExtractor.class),
                any(Object[].class)
            )).thenReturn(Collections.emptyList());

            Empleado e = empleadoData.findById(123);
            assertNull(e, "Como no hay filas, debe retornar null");
    }

    @Test
    void deleteEmpleado_deberíaRetornarFalse_siNoExisteRegistro() {
        // simulamos que al buscar id_persona lanza excepción
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyInt()))
            .thenThrow(new EmptyResultDataAccessException(1));

        boolean borrado = empleadoData.deleteEmpleado(999);
        assertFalse(borrado, "Si no existe el empleado, deleteEmpleado debe devolver false");
    }
}
