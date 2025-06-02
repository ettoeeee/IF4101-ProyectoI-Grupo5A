package com.bulkgym.business;


import java.sql.Date;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.data.ClienteData;
import com.bulkgym.data.ItemRutinaEjercicioData;
import com.bulkgym.data.ItemRutinaMedidaData;
import com.bulkgym.data.RutinaData;
import com.bulkgym.domain.Cliente;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;
import com.bulkgym.dto.ItemRutinaEjercicioDTO;
import com.bulkgym.dto.ItemRutinaMedidaDTO;
import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.dto.RutinaCompletaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class RutinaBusiness {

    @Autowired
    private RutinaData rutinaData;
    
    @Autowired
    private ClienteData clienteData;

    @Transactional(readOnly = true)
    public List<Rutina> obtenerRutinas(int idCliente) {
        return rutinaData.encontrarRutinasPorCliente(idCliente);
    }
    
    @Transactional(readOnly = true)
    public Rutina buscarRutina(int idRutina) {
        return rutinaData.buscarPorId(idRutina);
    }

    @Transactional(readOnly = true)
    public List<Rutina> obtenerRutinasPorNombreCliente(String nombreCliente) {
        return rutinaData.encontrarRutinasPorNombreCliente(nombreCliente);
    }

    @Transactional
    public Rutina crearRutina(int idCliente, Rutina rutina) {
        // asocia al cliente
        rutina.setIdCliente(idCliente);
        // inserta y recupera el id
        int pk = rutinaData.insertarRutina(rutina);
        rutina.setIdRutina(pk);
        return rutina;
    }
    
    // VEROOO 
    @Autowired
    private ItemRutinaMedidaData itemRutinaMedidaData;

    @Autowired
    private ItemRutinaEjercicioData itemRutinaEjercicioData;


    @Transactional
    public Rutina crearRutinaCompleta(int idCliente, RutinaCompletaDTO dto) {
        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            System.out.println("📥 DTO recibido: " + json);

            Rutina rutina = new Rutina();
            rutina.setIdCliente(idCliente);
            rutina.setIdInstructor(dto.getIdInstructor());
            rutina.setHorario(dto.getHorario());
            rutina.setObjetivo(dto.getObjetivo());
            rutina.setLesiones(dto.getLesiones());
            rutina.setPadecimientos(dto.getPadecimientos());
            rutina.setFechaCreacion(dto.getFechaCreacion());
            rutina.setFechaRenovacion(dto.getFechaRenovacion());

            int idRutina = rutinaData.insertarRutina(rutina);
            rutina.setIdRutina(idRutina);

            // medidas
            for (ItemRutinaMedidaDTO medida : dto.getMedidas()) {
                ItemRutinaMedida item = new ItemRutinaMedida();
                item.setValorMedida(medida.getValorMedida());
                item.setRutina(rutina);

                MedidaCorporal mc = new MedidaCorporal();
                mc.setCodMedida(medida.getIdMedidaCorporal());
                item.setMedidaCorporal(mc);

                itemRutinaMedidaData.guardar(item);
            }

            // ejrecicios
            for (ItemRutinaEjercicioDTO ejercicio : dto.getEjercicios()) {
                ItemRutinaEjercicio item = new ItemRutinaEjercicio();
                item.setIdRutina(idRutina);
                item.setIdEjercicio(ejercicio.getIdEjercicio());
                item.setSeriesEjercicio(ejercicio.getSeries());
                item.setRepeticionesEjercicio(ejercicio.getRepeticiones());
                item.setEquipoEjercicio(ejercicio.getEquipo());

                itemRutinaEjercicioData.guardar(item);
            }

            return rutina;

        } catch (Exception e) {
            System.err.println("Error al guardar rutina: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en crearRutinaCompleta", e);
        }

    }   


    

  
    @Transactional(readOnly = true)
    public List<Rutina> obtenerRutinasDesdeFecha(Date fechaLimite) {
        // Validación adicional
        if (fechaLimite == null) {
            throw new IllegalArgumentException("La fecha límite no puede ser nula");
        }
        
        // Obtener rutinas desde la fecha límite
        List<Rutina> rutinas = rutinaData.encontrarRutinasDesdeFecha(fechaLimite);
        
        // Puedes agregar lógica de transformación aquí si es necesario
        return rutinas;
    }

    

    public ReporteRutinaDTO obtenerReporteRutinaDTO(int idRutina) {
        Rutina rutina = rutinaData.buscarPorId(idRutina);
        if (rutina == null) return null;

        Cliente cliente = clienteData.buscarPorId(rutina.getIdCliente());
        if (cliente == null) return null;

        // 1) Traigo únicamente las filas de ItemRutinaMedida cuyo id_rutina sea el que quiero.
        List<ItemRutinaMedida> todas = itemRutinaMedidaData.findByRutinaId(idRutina);

        // 2) Agrupo por id de medida corporal para eliminar duplicados “idénticos”
        List<ItemRutinaMedida> medidasSinDuplicados = new ArrayList<>(
            todas.stream()
                 .collect(Collectors.toMap(
                     m -> m.getMedidaCorporal().getCodMedida(), // clave única: código de la medida
                     m -> m,
                     (primera, segunda) -> primera               // si hay dos con mismo codMedida, retengo la primera
                 ))
                 .values()
        );

        // 3) Pasamos lo mismo para ejercicios, si quieres filtrar por rutina en lugar de findAll:
        List<ItemRutinaEjercicio> todosLosEjercicios = itemRutinaEjercicioData.findByRutinaId(idRutina);
        // (supongamos que creaste findByRutinaId en ItemRutinaEjercicioData de forma análoga)
        List<ItemRutinaEjercicio> ejerciciosSinDuplicados = new ArrayList<>(
            todosLosEjercicios.stream()
                .collect(Collectors.toMap(
                    e -> e.getIdEjercicio(),   // clave única: id del ejercicio
                    e -> e,
                    (primero, segundo) -> primero
                ))
                .values()
        );

        ReporteRutinaDTO dto = new ReporteRutinaDTO();
        dto.setIdRutina(idRutina);
        dto.setNombreCliente(cliente.getNombre());
        dto.setTelefonoCliente(cliente.getTelefono());
        dto.setObjetivo(rutina.getObjetivo());
        dto.setFechaCreacion(rutina.getFechaCreacion());
        dto.setMedidas(medidasSinDuplicados);
        dto.setEjercicios(ejerciciosSinDuplicados);

        return dto;
    }


}
