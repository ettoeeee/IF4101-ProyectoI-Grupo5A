package com.bulkgym.util;

import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.CategoriaEjercicio;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportePdfUtilTest {

    @InjectMocks
    private ReportePdfUtil reportePdfUtil;

    @Mock
    private ReporteRutinaDTO dto;

    @Mock private ItemRutinaMedida itemMedida;
    @Mock private MedidaCorporal medidaCorporal;
    @Mock private ItemRutinaEjercicio itemRutinaEjercicio;
    @Mock private Ejercicio ejercicio;
    @Mock private CategoriaEjercicio categoria1, categoria2;

    private Date fecha1, fecha2;

    @BeforeEach
    void setup() throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = fmt.parse("2025-05-01");
        fecha2 = fmt.parse("2025-05-02");
    }

    @Test
    void generarPdfDesdeRutina_sinItems_generatesValidPdfHeader() throws Exception {
        when(dto.getNombreCliente()).thenReturn("Juan Pérez");
        when(dto.getTelefonoCliente()).thenReturn("555-1234");
        when(dto.getEdadCliente()).thenReturn(30);
        when(dto.getObjetivo()).thenReturn("Ganancia muscular");
        when(dto.getFechaCreacion()).thenReturn(fecha1);
        when(dto.getMedidas()).thenReturn(List.of());
        when(dto.getEjercicios()).thenReturn(List.of());

        byte[] pdf = reportePdfUtil.generarPdfDesdeRutina(dto);
        assertNotNull(pdf);

        // El PDF comienza con "%PDF"
        String header = new String(pdf, 0, 4);
        assertEquals("%PDF", header);
    }

    @Test
    void generarPdfDesdeRutina_conMedidasYEjercicios_includesAllSections() throws Exception {
        // --- configuración del DTO ---
        when(dto.getNombreCliente()).thenReturn("Ana Gómez");
        when(dto.getTelefonoCliente()).thenReturn("555-9876");
        when(dto.getEdadCliente()).thenReturn(25);
        when(dto.getObjetivo()).thenReturn("Pérdida de grasa");
        when(dto.getFechaCreacion()).thenReturn(fecha2);

        // mock item de medida
        when(itemMedida.getMedidaCorporal()).thenReturn(medidaCorporal);
        when(itemMedida.getValorMedida()).thenReturn(85.0);
        when(medidaCorporal.getNombreMedida()).thenReturn("Cintura");
        when(medidaCorporal.getUnidadMedida()).thenReturn("cm");
        when(dto.getMedidas()).thenReturn(List.of(itemMedida));

        // mock item de ejercicio con dos categorías
        //when(itemRutinaEjercicio.getEjercicio()).thenReturn(ejercicio);
        when(itemRutinaEjercicio.getSeriesEjercicio()).thenReturn(4);
        when(itemRutinaEjercicio.getRepeticionesEjercicio()).thenReturn(12);
        when(ejercicio.getNombreEjercicio()).thenReturn("Sentadilla");
        when(ejercicio.getCategoriaEjercicio()).thenReturn(List.of(categoria1, categoria2));
        when(categoria1.getNombreCategoria()).thenReturn("Pierna");
        when(categoria2.getNombreCategoria()).thenReturn("Fuerza");
        when(dto.getEjercicios()).thenReturn(List.of(itemRutinaEjercicio));

        // generamos el PDF
        byte[] pdfBytes = reportePdfUtil.generarPdfDesdeRutina(dto);
        assertNotNull(pdfBytes);

        // 1) Primero comprueba el header
        String magic = new String(pdfBytes, 0, 4);
        assertEquals("%PDF", magic, "El output debe arrancar con %PDF");

        // 2) Parsea la página
        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes));
        String pageText = PdfTextExtractor.getTextFromPage(reader, 1);
        reader.close();

        assertTrue(pageText.contains("Ana Gómez"));
        assertTrue(pageText.contains("Cintura"));
        assertTrue(pageText.contains("85.0"));
        assertTrue(pageText.contains("Sentadilla"));
        assertTrue(pageText.contains("Pierna") && pageText.contains("Fuerza"));
    }
}
