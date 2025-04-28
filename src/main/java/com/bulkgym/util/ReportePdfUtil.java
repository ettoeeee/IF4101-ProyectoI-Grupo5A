package com.bulkgym.util;

import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class ReportePdfUtil {

    public byte[] generarPdfDesdeRutina(ReporteRutinaDTO dto) {
        try {
            Document documento = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(documento, out);
            documento.open();

            // Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Reporte de Rutina", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);

            // Cliente
            documento.add(new Paragraph("Cliente: " + dto.getNombreCliente()));
            documento.add(new Paragraph("Teléfono: " + dto.getTelefonoCliente()));
            documento.add(new Paragraph("Edad: " + dto.getEdadCliente() + " años"));
            documento.add(Chunk.NEWLINE);

            // Rutina
            documento.add(new Paragraph("Objetivo: " + dto.getObjetivo()));
            documento.add(new Paragraph("Fecha de creación: " + dto.getFechaCreacion()));
            documento.add(Chunk.NEWLINE);

            // Medidas corporales
            if (dto.getMedidas() != null && !dto.getMedidas().isEmpty()) {
                documento.add(new Paragraph("Medidas corporales:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                PdfPTable tablaMedidas = new PdfPTable(3);
                tablaMedidas.setWidthPercentage(100);
                tablaMedidas.addCell("Tipo");
                tablaMedidas.addCell("Valor");
                tablaMedidas.addCell("Unidad");

                for (ItemRutinaMedida m : dto.getMedidas()) {
                    tablaMedidas.addCell(m.getMedidaCorporal().getNombreMedida());
                    tablaMedidas.addCell(String.valueOf(m.getValorMedida()));
                    tablaMedidas.addCell(m.getMedidaCorporal().getUnidadMedida());
                }

                documento.add(tablaMedidas);
                documento.add(Chunk.NEWLINE);
            }

            // Ejercicios
            if (dto.getEjercicios() != null && !dto.getEjercicios().isEmpty()) {
                documento.add(new Paragraph("Ejercicios asignados:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                PdfPTable tablaEjercicios = new PdfPTable(4);
                tablaEjercicios.setWidthPercentage(100);
                tablaEjercicios.addCell("Ejercicio");
                tablaEjercicios.addCell("Categoría");
                tablaEjercicios.addCell("Series");
                tablaEjercicios.addCell("Repeticiones");

                for (ItemRutinaEjercicio e : dto.getEjercicios()) {
                    tablaEjercicios.addCell(e.getEjercicio().getNombreEjercicio());
                    //tablaEjercicios.addCell(e.getEjercicio().getCategoriaEjercicio().getNombreCategoria());
                    tablaEjercicios.addCell(String.valueOf(e.getSeriesEjercicio()));
                    tablaEjercicios.addCell(String.valueOf(e.getRepeticionesEjercicio()));
                }

                documento.add(tablaEjercicios);
            }

            documento.close();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando el PDF: " + e.getMessage());
        }
    }
}
