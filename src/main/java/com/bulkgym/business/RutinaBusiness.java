package com.bulkgym.business;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.data.ItemRutinaEjercicioData;
import com.bulkgym.data.ItemRutinaMedidaData;
import com.bulkgym.data.RutinaData;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;
import com.bulkgym.dto.ItemRutinaEjercicioDTO;
import com.bulkgym.dto.ItemRutinaMedidaDTO;
import com.bulkgym.dto.RutinaCompletaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class RutinaBusiness {

    @Autowired
    private RutinaData rutinaData;

    @Transactional(readOnly = true)
    public List<Rutina> obtenerRutinas(int idCliente) {
        return rutinaData.encontrarRutinasPorCliente(idCliente);
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

    @Transactional(readOnly = true)
    public Rutina buscarRutina(int idRutina) {
        return rutinaData.buscarPorId(idRutina);
    }
    
    
    // VEROOO 
    @Autowired
    private ItemRutinaMedidaData itemRutinaMedidaData;

    @Autowired
    private ItemRutinaEjercicioData itemRutinaEjercicioData;


    @Transactional
    public Rutina crearRutinaCompleta(int idCliente, RutinaCompletaDTO dto) {
        try {
            // ✅ Imprimir el JSON recibido para verificar
            String json = new ObjectMapper().writeValueAsString(dto);
            System.out.println("📥 DTO recibido: " + json);

            // Crear objeto rutina
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

            // Insertar medidas
            for (ItemRutinaMedidaDTO medida : dto.getMedidas()) {
                ItemRutinaMedida item = new ItemRutinaMedida();
                item.setValorMedida(medida.getValorMedida());
                item.setRutina(rutina);

                MedidaCorporal mc = new MedidaCorporal();
                mc.setCodMedida(medida.getIdMedidaCorporal());
                item.setMedidaCorporal(mc);

                itemRutinaMedidaData.guardar(item);
            }

            // Insertar ejercicios
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
            System.err.println("❌ Error al guardar rutina completa: " + e.getMessage());
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

    
}
