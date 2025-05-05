package com.bulkgym.data;

import com.bulkgym.domain.MedidaCorporal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedidaCorporalDataTest {

    @InjectMocks
    private MedidaCorporalData medidaCorporalData;

    @Mock
    private JdbcTemplate jdbcTemplate;

    //No se conecta a una base de datos real (usa mocks).
    
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Buscar por ID (existe)
    @Test
    void testFindById_found() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(1);
        medida.setNombreMedida("Longitud del brazo");
        medida.setUnidadMedida("cm");
        medida.setImagen(null);

        when(jdbcTemplate.query(anyString(), any(MedidaCorporalExtractor.class), eq(1)))
            .thenReturn(List.of(medida));

        MedidaCorporal result = medidaCorporalData.findById(1);

        assertNotNull(result);
        assertEquals("Longitud del brazo", result.getNombreMedida());
        assertNull(result.getImagen());
    }

    // Buscar por ID (no existe)
    @Test
    void testFindById_notFound() {
        when(jdbcTemplate.query(anyString(), any(MedidaCorporalExtractor.class), eq(99)))
            .thenReturn(Collections.emptyList());

        MedidaCorporal result = medidaCorporalData.findById(99);
        assertNull(result);
    }

    // Guardar nueva medida 
    @Test
    void testInsert() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Altura de la rodilla");
        medida.setUnidadMedida("cm");
        medida.setImagen(null);

        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("GENERATED_KEY", 55));
            return 1;
        }).when(jdbcTemplate).update(any(), any(KeyHolder.class));

        medidaCorporalData.guardar(medida);

        assertEquals(55, medida.getCodMedida());
    }

    // Modificar medida 
    @Test
    void testUpdate_2() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(2);
        medida.setNombreMedida("Circunferencia del cuello");
        medida.setUnidadMedida("cm");
        medida.setImagen(null);

        when(jdbcTemplate.update(anyString(), eq("Circunferencia del cuello"), eq("cm"), isNull(), eq(2)))
            .thenReturn(1);

        boolean result = medidaCorporalData.update(medida);
        assertTrue(result);
    }

    @Test
    void testUpdate() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(999);
        medida.setNombreMedida("Cabeza");
        medida.setUnidadMedida("cm");
        medida.setImagen(null);

        when(jdbcTemplate.update(anyString(), any(), any(), isNull(), eq(999)))
            .thenReturn(0);

        boolean result = medidaCorporalData.update(medida);
        assertFalse(result);
    }

    // Eliminar medida
    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);
        boolean result = medidaCorporalData.delete(1);
        assertTrue(result);
    }

    @Test
    void testDelete_() {
        when(jdbcTemplate.update(anyString(), eq(999))).thenReturn(0);
        boolean result = medidaCorporalData.delete(999);
        assertFalse(result);
    }
    
    
    /*
     * DAO significa Data Access Object 

Es un patrón de diseño que se encarga de separar la lógica de acceso a datos del 
resto de la aplicación (como la lógica de negocio o la interfaz de usuario).

Ejecuta operaciones como SELECT, INSERT, UPDATE, y DELETE.

Convierte los datos de la base en objetos Java.
     */
}
