package com.bulkgym.data;

import com.bulkgym.domain.MedidaCorporal;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedidaCorporalDataTest {

    private static MedidaCorporalData data;

    @BeforeAll
    static void setup() {
        data = new MedidaCorporalData();
    }

    @Test
    void testInsertarMedida() {
        assertDoesNotThrow(() -> data.insertarMedida("Altura", "cm"));
    }

    @Test
    void testObtenerTodas() throws SQLException {
        List<MedidaCorporal> medidas = data.obtenerTodas();
        assertNotNull(medidas);
        assertTrue(medidas.size() > 0); // Asegurarse que al menos una medida fue insertada
    }

    @Test
    void testObtenerPorId() throws SQLException {
        List<MedidaCorporal> medidas = data.obtenerTodas();
        if (!medidas.isEmpty()) {
            MedidaCorporal primera = medidas.get(0);
            MedidaCorporal encontrada = data.obtenerPorId(primera.getIdMedidaCorporal());
            assertNotNull(encontrada);
            assertEquals(primera.getIdMedidaCorporal(), encontrada.getIdMedidaCorporal());
        }
    }

    @Test
    void testActualizarMedida() throws SQLException {
        List<MedidaCorporal> medidas = data.obtenerTodas();
        if (!medidas.isEmpty()) {
            MedidaCorporal medida = medidas.get(0);
            boolean actualizado = data.actualizarMedida(medida.getIdMedidaCorporal(), "Peso", "kg");
            assertTrue(actualizado);

            MedidaCorporal actualizada = data.obtenerPorId(medida.getIdMedidaCorporal());
            assertEquals("Peso", actualizada.getIdMedidaCorporal());
            assertEquals("kg", actualizada.getIdMedidaCorporal());
        }
    }

    @Test
    void testEliminarMedida() throws SQLException {
        data.insertarMedida("Temporal", "tmp");
        List<MedidaCorporal> medidas = data.obtenerTodas();
        MedidaCorporal ultima = medidas.get(medidas.size() - 1);

        boolean eliminado = data.eliminarMedida(ultima.getIdMedidaCorporal());
        assertTrue(eliminado);

        MedidaCorporal eliminada = data.obtenerPorId(ultima.getIdMedidaCorporal());
        assertNull(eliminada);
    }
}