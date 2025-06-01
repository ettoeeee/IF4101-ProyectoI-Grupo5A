package com.bulkgym.util;

import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class ReportePdfUtil {

    public byte[] generarPdfDesdeRutina(ReporteRutinaDTO dto) {
        Document documento = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            PdfWriter.getInstance(documento, out);
            documento.open();

            // 1) TÍTULO
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Reporte de Rutina", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // 2) DATOS DEL CLIENTE
            documento.add(new Paragraph("Cliente: " + dto.getNombreCliente()));
            documento.add(new Paragraph("Teléfono: " + dto.getTelefonoCliente()));
            documento.add(Chunk.NEWLINE);

            // 3) DATOS DE LA RUTINA
            documento.add(new Paragraph("Objetivo: " + dto.getObjetivo()));
            documento.add(new Paragraph("Fecha de creación: " + df.format(dto.getFechaCreacion())));
            documento.add(Chunk.NEWLINE);

            // 4) TABLA DE MEDIDAS CORPORALES
            if (dto.getMedidas() != null && !dto.getMedidas().isEmpty()) {
                documento.add(new Paragraph("Medidas corporales:",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                PdfPTable tablaMedidas = new PdfPTable(3);
                tablaMedidas.setWidthPercentage(100);

                for (String h : new String[]{"Tipo", "Valor", "Unidad"}) {
                    PdfPCell cell = new PdfPCell(
                            new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaMedidas.addCell(cell);
                }
                for (ItemRutinaMedida m : dto.getMedidas()) {
                    tablaMedidas.addCell(m.getMedidaCorporal().getNombreMedida());
                    tablaMedidas.addCell(String.valueOf(m.getValorMedida()));
                    tablaMedidas.addCell(m.getMedidaCorporal().getUnidadMedida());
                }
                documento.add(tablaMedidas);
                documento.add(Chunk.NEWLINE);
            }

            // 5) TABLA DE EJERCICIOS
            if (dto.getEjercicios() != null && !dto.getEjercicios().isEmpty()) {
                documento.add(new Paragraph("Ejercicios asignados:",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                PdfPTable tablaEjercicios = new PdfPTable(4);
                tablaEjercicios.setWidthPercentage(100);

                // ID Ejercicio, Equipo, Series, Repeticiones
                for (String h : new String[]{"ID Ejercicio", "Equipo", "Series", "Repeticiones"}) {
                    PdfPCell cell = new PdfPCell(
                            new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaEjercicios.addCell(cell);
                }
                for (ItemRutinaEjercicio e : dto.getEjercicios()) {
                    tablaEjercicios.addCell(String.valueOf(e.getIdEjercicio()));
                    tablaEjercicios.addCell(e.getEquipoEjercicio());
                    tablaEjercicios.addCell(String.valueOf(e.getSeriesEjercicio()));
                    tablaEjercicios.addCell(String.valueOf(e.getRepeticionesEjercicio()));
                }
                documento.add(tablaEjercicios);
                documento.add(Chunk.NEWLINE);
            }

            // 6) Cerrar documento y devolver bytes
            documento.close();
            return out.toByteArray();
        } catch (DocumentException de) {
            throw new RuntimeException("Error generando el PDF: " + de.getMessage(), de);
        }
    }
}
