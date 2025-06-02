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

            //  Validar si ya tiene una rutina vigente
            if (rutinaData.tieneRutinaVigente(idCliente)) {
                throw new IllegalArgumentException("El cliente ya tiene una rutina vigente.");
            }

            //  Validar que haya al menos una medida
            if (dto.getMedidas() == null || dto.getMedidas().isEmpty()) {
                throw new IllegalArgumentException("La rutina debe tener al menos una medida.");
            }

            // Validar que haya al menos un ejercicio
            if (dto.getEjercicios() == null || dto.getEjercicios().isEmpty()) {
                throw new IllegalArgumentException("La rutina debe tener al menos un ejercicio.");
            }

            // 4️Asignar fecha de renovación automática si no viene
            if (dto.getFechaCreacion() == null) {
                dto.setFechaCreacion(new Date(System.currentTimeMillis()));
            }

            if (dto.getFechaRenovacion() == null) {
                dto.setFechaRenovacion(Date.valueOf(dto.getFechaCreacion().toLocalDate().plusMonths(3)));
            }

            //  Crear la rutina base
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

            // Guardar medidas
            for (ItemRutinaMedidaDTO medida : dto.getMedidas()) {
                ItemRutinaMedida item = new ItemRutinaMedida();
                item.setValorMedida(medida.getValorMedida());
                item.setRutina(rutina);

                MedidaCorporal mc = new MedidaCorporal();
                mc.setCodMedida(medida.getIdMedidaCorporal());
                item.setMedidaCorporal(mc);

                itemRutinaMedidaData.guardar(item);
            }

            // Guardar ejercicios
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
            System.err.println(" Error al guardar rutina: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en crearRutinaCompleta", e);
        }
    }



    // modificar rutina
    @Transactional(readOnly = true)
    public RutinaCompletaDTO obtenerRutinaCompleta(int idCliente, int idRutina) {
        Rutina rutina = rutinaData.buscarPorId(idRutina);
        if (rutina == null || rutina.getIdCliente() != idCliente) return null;

        Cliente cliente = clienteData.buscarPorId(idCliente);
        List<ItemRutinaMedida> medidas = itemRutinaMedidaData.findByRutinaId(idRutina);
        List<ItemRutinaEjercicio> ejercicios = itemRutinaEjercicioData.findByRutinaId(idRutina);

        RutinaCompletaDTO dto = new RutinaCompletaDTO();
        dto.setIdRutina(idRutina);
        dto.setIdInstructor(rutina.getIdInstructor());
        dto.setHorario(rutina.getHorario());
        dto.setObjetivo(rutina.getObjetivo());
        dto.setLesiones(rutina.getLesiones());
        dto.setPadecimientos(rutina.getPadecimientos());
        dto.setFechaCreacion(rutina.getFechaCreacion());
        dto.setFechaRenovacion(rutina.getFechaRenovacion());
        dto.setCliente(cliente); // ✅ incluye cliente

        dto.setMedidas(
            medidas.stream().map(m -> {
                ItemRutinaMedidaDTO d = new ItemRutinaMedidaDTO();
                d.setIdMedidaCorporal(m.getMedidaCorporal().getCodMedida());
                d.setValorMedida(m.getValorMedida());
                d.setFechaMedicion(dto.getFechaCreacion());
                return d;
            }).toList()
        );

        dto.setEjercicios(
            ejercicios.stream().map(e -> {
                ItemRutinaEjercicioDTO d = new ItemRutinaEjercicioDTO();
                d.setIdEjercicio(e.getIdEjercicio());
                d.setSeries(e.getSeriesEjercicio());
                d.setRepeticiones(e.getRepeticionesEjercicio());
                d.setEquipo(e.getEquipoEjercicio());
                return d;
            }).toList()
        );

        return dto;
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
    
    @Transactional
    public void actualizarRutinaCompleta(int idCliente, int idRutina, RutinaCompletaDTO dto) {
        Rutina existente = rutinaData.buscarPorId(idRutina);
        if (existente == null || existente.getIdCliente() != idCliente) {
            throw new IllegalArgumentException("Rutina no encontrada o no pertenece al cliente.");
        }

        // Actualizar datos básicos
        existente.setObjetivo(dto.getObjetivo());
        existente.setLesiones(dto.getLesiones());
        existente.setPadecimientos(dto.getPadecimientos());
        existente.setHorario(dto.getHorario());
        existente.setFechaCreacion(dto.getFechaCreacion());
        existente.setFechaRenovacion(dto.getFechaRenovacion());
        existente.setIdInstructor(dto.getIdInstructor());

        rutinaData.update(existente); // Asegúrate de tener este método implementado

        // Limpiar medidas y ejercicios anteriores
        itemRutinaMedidaData.eliminarPorRutina(idRutina);
        itemRutinaEjercicioData.eliminarPorRutina(idRutina);

        // Insertar medidas nuevas
        for (ItemRutinaMedidaDTO medida : dto.getMedidas()) {
            ItemRutinaMedida item = new ItemRutinaMedida();
            item.setValorMedida(medida.getValorMedida());
            item.setRutina(existente);

            MedidaCorporal mc = new MedidaCorporal();
            mc.setCodMedida(medida.getIdMedidaCorporal());
            item.setMedidaCorporal(mc);

            itemRutinaMedidaData.guardar(item);
        }

        // Insertar ejercicios nuevos
        for (ItemRutinaEjercicioDTO ejercicio : dto.getEjercicios()) {
            ItemRutinaEjercicio item = new ItemRutinaEjercicio();
            item.setIdRutina(idRutina);
            item.setIdEjercicio(ejercicio.getIdEjercicio());
            item.setSeriesEjercicio(ejercicio.getSeries());
            item.setRepeticionesEjercicio(ejercicio.getRepeticiones());
            item.setEquipoEjercicio(ejercicio.getEquipo());

            itemRutinaEjercicioData.guardar(item);
        }
    }

    
    @Transactional
    public boolean eliminarRutina(int idCliente, int idRutina) {
        Rutina existente = rutinaData.buscarPorId(idRutina);
        if (existente == null || existente.getIdCliente() != idCliente) {
            return false;
        }

        // Eliminar medidas y ejercicios relacionados primero
        itemRutinaMedidaData.eliminarPorRutina(idRutina);
        itemRutinaEjercicioData.eliminarPorRutina(idRutina);

        // Luego eliminar la rutina principal
        return rutinaData.eliminarRutina(idRutina);
    }



}
