package com.bulkgym.service;

import com.bulkgym.data.*;
import com.bulkgym.domain.*;
import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.util.ReportePdfUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ReporteRutinaService {

    @Autowired private ClienteData clienteData;
    @Autowired private RutinaData rutinaData;
    @Autowired private ItemRutinaMedidaData medidaData;
    @Autowired private ItemRutinaEjercicioData ejercicioData;
    @Autowired private ReportePdfUtil reportePdfUtil;

    public byte[] generarReporte(int idRutina) {
        // 1. Obtener la rutina
        Rutina rutina = rutinaData.buscarPorId(idRutina);
        if (rutina == null) {
            throw new RuntimeException("La rutina con ID " + idRutina + " no existe.");
        }

        // 2. Obtener el cliente
        Cliente cliente = clienteData.buscarPorId(rutina.getIdCliente());
        if (cliente == null) {
            throw new RuntimeException("El cliente asociado a la rutina no existe.");
        }

        // 3. Calcular edad del cliente
        int edad = Period.between(
            cliente.getFechaNacimiento().toLocalDate(),
            LocalDate.now()
        ).getYears();

        // 4. Obtener medidas y ejercicios
      
        //List<ItemRutinaEjercicio> ejercicios = ejercicioData.obtenerEjerciciosPorRutina(idRutina);

        // 5. Armar DTO
        ReporteRutinaDTO dto = new ReporteRutinaDTO();
        dto.setNombreCliente(cliente.getNombre() + " " + cliente.getApellidos());
        dto.setTelefonoCliente(cliente.getTelefono());
        dto.setEdadCliente(edad);
        dto.setObjetivo(rutina.getObjetivo());
        dto.setFechaCreacion(rutina.getFechaCreacion());
        //dto.setEjercicios(ejercicios);

        // 6. Generar PDF
        return reportePdfUtil.generarPdfDesdeRutina(dto);
    }
    
    
}

