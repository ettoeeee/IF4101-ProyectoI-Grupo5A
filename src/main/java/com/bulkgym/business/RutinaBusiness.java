package com.bulkgym.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bulkgym.data.RutinaData;
import com.bulkgym.domain.Rutina;

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
        // insertam y recupera el id
        int idGenerado = rutinaData.insertarRutina(rutina);
        rutina.setIdRutina(idGenerado);
        return rutina;
    }

    @Transactional(readOnly = true)
    public Rutina buscarRutina(int idRutina) {
        return rutinaData.buscarPorId(idRutina);
    }
}
