package com.bulkgym.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bulkgym.domain.MedidaCorporal;

class MedidaCorporalBusinessTest {
	private MedidaCorporalBusiness business;

    @BeforeEach
    void setUp() {
        business = new MedidaCorporalBusiness();
    }


    @Test
    void testAgregarMedidaIncrementaTamanio() {
        business.agregarMedida("Peso", "kg");
        assertEquals(1, business.obtenerTodas().size());
    }

    @Test
    void testAgregarMedidaDatosCorrectos() {
        business.agregarMedida("Altura", "cm");
        MedidaCorporal medida = business.obtenerPorId(1);
        assertEquals("Altura", medida.getTipoMedida());
        assertEquals("cm", medida.getUnidad());
    }

    @Test
    void testAgregarVariasMedidas() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        assertEquals(2, business.obtenerTodas().size());
    }

    @Test
    void testIdAutoIncrementado() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        assertEquals(2, business.obtenerPorId(2).getIdMedidaCorporal());
    }

    @Test
    void testAgregarMedidaNoNula() {
        business.agregarMedida("Cintura", "cm");
        assertNotNull(business.obtenerPorId(1));
    }

    // ----------- PRUEBAS OBTENER POR ID ------------------

    @Test
    void testObtenerPorIdExistente() {
        business.agregarMedida("Peso", "kg");
        assertNotNull(business.obtenerPorId(1));
    }

    @Test
    void testObtenerPorIdNoExistente() {
        assertNull(business.obtenerPorId(99));
    }

    @Test
    void testObtenerPorIdConVariasEntradas() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        assertEquals("Altura", business.obtenerPorId(2).getTipoMedida());
    }

    @Test
    void testObtenerPorIdConIDNegativo() {
        assertNull(business.obtenerPorId(-1));
    }

    @Test
    void testObtenerPorIdLimiteInferior() {
        business.agregarMedida("Cuello", "cm");
        assertEquals(1, business.obtenerPorId(1).getIdMedidaCorporal());
    }

    // ----------- PRUEBAS OBTENER TODAS ------------------

    @Test
    void testObtenerTodasConDatos() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        assertEquals(2, business.obtenerTodas().size());
    }

    @Test
    void testObtenerTodasVacio() {
        assertTrue(business.obtenerTodas().isEmpty());
    }

    @Test
    void testObtenerTodasNoNulo() {
        assertNotNull(business.obtenerTodas());
    }

    @Test
    void testObtenerTodasConOrden() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        assertEquals("Peso", business.obtenerTodas().get(0).getTipoMedida());
    }

    @Test
    void testObtenerTodasAgregaYLee() {
        business.agregarMedida("Biceps", "cm");
        assertEquals("Biceps", business.obtenerTodas().get(0).getTipoMedida());
    }

    // ----------- PRUEBAS ACTUALIZAR ------------------

    @Test
    void testActualizarMedidaExistente() {
        business.agregarMedida("Peso", "kg");
        boolean result = business.actualizarMedida(1, "Altura", "cm");
        assertTrue(result);
        assertEquals("Altura", business.obtenerPorId(1).getTipoMedida());
    }

    @Test
    void testActualizarMedidaNoExistente() {
        boolean result = business.actualizarMedida(99, "Altura", "cm");
        assertFalse(result);
    }

    @Test
    void testActualizarMedidaConNuevosValores() {
        business.agregarMedida("Peso", "kg");
        business.actualizarMedida(1, "Cuello", "cm");
        assertEquals("cm", business.obtenerPorId(1).getUnidad());
    }

    @Test
    void testActualizarMedidaValoresNoCambianSiNoExiste() {
        business.agregarMedida("Peso", "kg");
        business.actualizarMedida(99, "Altura", "cm");
        assertEquals("Peso", business.obtenerPorId(1).getTipoMedida());
    }

    @Test
    void testActualizarMedidaRetornaTrueCorrectamente() {
        business.agregarMedida("Brazo", "cm");
        assertTrue(business.actualizarMedida(1, "Pierna", "cm"));
    }

    // ----------- PRUEBAS ELIMINAR ------------------

    @Test
    void testEliminarMedidaExistente() {
        business.agregarMedida("Peso", "kg");
        boolean eliminado = business.eliminarMedida(1);
        assertTrue(eliminado);
        assertNull(business.obtenerPorId(1));
    }

    @Test
    void testEliminarMedidaNoExistente() {
        assertFalse(business.eliminarMedida(5));
    }

    @Test
    void testEliminarMedidaReduceLista() {
        business.agregarMedida("Peso", "kg");
        business.agregarMedida("Altura", "cm");
        business.eliminarMedida(1);
        assertEquals(1, business.obtenerTodas().size());
    }

    @Test
    void testEliminarMedidaIdIncorrectoNoAfecta() {
        business.agregarMedida("Peso", "kg");
        business.eliminarMedida(999);
        assertEquals(1, business.obtenerTodas().size());
    }

    @Test
    void testEliminarMedidaRetornaTrueCorrectamente() {
        business.agregarMedida("Cuello", "cm");
        assertTrue(business.eliminarMedida(1));
    }
}
