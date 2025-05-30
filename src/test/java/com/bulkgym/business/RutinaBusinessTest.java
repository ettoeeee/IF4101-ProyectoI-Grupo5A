package com.bulkgym.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bulkgym.data.RutinaData;
import com.bulkgym.domain.Rutina;

@ExtendWith(MockitoExtension.class)
public class RutinaBusinessTest {

    @Mock
    private RutinaData rutinaData;

    @InjectMocks
    private RutinaBusiness rutinaBusiness;

    private Rutina rutina;

    @BeforeEach
    void setUp() {
        rutina = new Rutina();
        rutina.setFechaCreacion(Date.valueOf("2025-05-30"));
    }

    @Test
    void crearRutina_deberiaAsignarIdClienteYRetornarIdGenerado() {
        // da por hecho que el DAO nos devuelve 123 como ID generado
        when(rutinaData.insertarRutina(rutina)).thenReturn(123);

        Rutina creada = rutinaBusiness.crearRutina(7, rutina);

        // después de haber seteado el idCliente y el idRutina
        assertEquals(7, creada.getIdCliente());
        assertEquals(123, creada.getIdRutina());
        verify(rutinaData).insertarRutina(rutina);
    }

    @Test
    void obtenerRutinas_deberiaDelegarLaLlamadaAlDAO() {
        when(rutinaData.encontrarRutinasPorCliente(7)).thenReturn(List.of(rutina));

        List<Rutina> lista = rutinaBusiness.obtenerRutinas(7);

        assertEquals(1, lista.size());
        verify(rutinaData).encontrarRutinasPorCliente(7);
    }
}
