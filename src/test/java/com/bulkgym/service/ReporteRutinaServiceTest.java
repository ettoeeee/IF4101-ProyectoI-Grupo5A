package com.bulkgym.service;

import com.bulkgym.data.*;
import com.bulkgym.domain.*;
import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.util.ReportePdfUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ReporteRutinaServiceTest {

    @Mock private ClienteData clienteData;
    @Mock private RutinaData rutinaData;
    @Mock private ItemRutinaMedidaData medidaData;
    @Mock private ItemRutinaEjercicioData ejercicioData;
    @Mock private ReportePdfUtil reportePdfUtil;

    @InjectMocks
    private ReporteRutinaService reporteRutinaService;

    private Rutina rutina;
    private Cliente cliente;

    @BeforeEach
    void setup() {
        rutina = new Rutina();
        rutina.setIdRutina(1);
        rutina.setIdCliente(10);
        rutina.setFechaCreacion(Date.valueOf("2024-1-1"));
        rutina.setObjetivo("Aumentar masa muscular");

        cliente = new Cliente();
        cliente.setIdCliente(10);
        cliente.setNombre("Luis");
        cliente.setApellidos("Ramírez");
        cliente.setTelefono("8888-8888");
        cliente.setFechaNacimiento(Date.valueOf("1995-05-20"));
    }

    @Test
    void generarReporte_deberiaRetornarArregloDeBytes() {
        when(rutinaData.buscarPorId(1)).thenReturn(rutina);
        when(clienteData.buscarPorId(10)).thenReturn(cliente);
        when(medidaData.obtenerMedidasPorRutina(1)).thenReturn(Collections.emptyList());
        when(ejercicioData.obtenerEjerciciosPorRutina(1)).thenReturn(Collections.emptyList());

        // Simular un PDF generado
        byte[] pdfMock = new byte[]{1, 2, 3};
        when(reportePdfUtil.generarPdfDesdeRutina(any(ReporteRutinaDTO.class))).thenReturn(pdfMock);

        byte[] resultado = reporteRutinaService.generarReporte(1);

        assertNotNull(resultado);
        assertArrayEquals(pdfMock, resultado);
        verify(rutinaData).buscarPorId(1);
        verify(clienteData).buscarPorId(10);
        verify(reportePdfUtil).generarPdfDesdeRutina(any());
    }
}
