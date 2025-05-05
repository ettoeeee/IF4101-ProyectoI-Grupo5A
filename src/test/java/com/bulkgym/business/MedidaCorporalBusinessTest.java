package com.bulkgym.business;

import com.bulkgym.data.MedidaCorporalData;
import com.bulkgym.domain.MedidaCorporal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedidaCorporalBusinessTest {

    @InjectMocks //Inyecta el mockito
    private MedidaCorporalBusiness medidaCorporalBusiness;

    @Mock //simula la clase MedidaCorporalData para que no se conecte a una base real.
    private MedidaCorporalData medidaCorporalData;

    @BeforeEach
    void setUp() { //Inicializa las anotaciones de Mockito antes de cada prueba.
        MockitoAnnotations.openMocks(this);
    }

    // Añadir
    @Test
    void testGuardar() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setNombreMedida("Masa Muscular");
        medida.setUnidadMedida("kg");
        medida.setImagen(null);

        doNothing().when(medidaCorporalData).guardar(medida);

        medidaCorporalBusiness.guardar(medida);

        verify(medidaCorporalData, times(1)).guardar(medida);
    }

    // Modificar
    @Test
    void testActualizar() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(5);
        medida.setNombreMedida("Diámetro de Muñeca");
        medida.setUnidadMedida("cm");
        medida.setImagen(null);

        when(medidaCorporalData.update(medida)).thenReturn(true);

        medidaCorporalBusiness.actualizar(medida);

        verify(medidaCorporalData, times(1)).update(medida);
    }

    // Eliminar
    @Test
    void testEliminar() {
        int id = 10;

        when(medidaCorporalData.delete(id)).thenReturn(true);

        medidaCorporalBusiness.eliminar(id);

        verify(medidaCorporalData, times(1)).delete(id);
    }

    // Buscar por ID
    @Test
    void testObtenerPorId() {
        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(3);

        when(medidaCorporalData.findById(3)).thenReturn(medida);

        MedidaCorporal result = medidaCorporalBusiness.obtenerPorId(3);

        assertNotNull(result);
        assertEquals(3, result.getCodMedida());
        verify(medidaCorporalData, times(1)).findById(3);
    }

    // Buscar todos
    @Test
    void testObtenerTodas() {
        List<MedidaCorporal> lista = List.of(
            new MedidaCorporal(), new MedidaCorporal()
        );

        when(medidaCorporalData.findAll()).thenReturn(lista);

        List<MedidaCorporal> result = medidaCorporalBusiness.obtenerTodas();

        assertEquals(2, result.size());
        verify(medidaCorporalData, times(1)).findAll();
    }
}
