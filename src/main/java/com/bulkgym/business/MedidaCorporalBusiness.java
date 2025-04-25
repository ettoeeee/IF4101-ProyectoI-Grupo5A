package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.MedidaCorporalData;
import com.bulkgym.domain.MedidaCorporal;

@Service
public class MedidaCorporalBusiness {

    @Autowired
    private MedidaCorporalData medidaCorporalData;

    public List<MedidaCorporal> obtenerTodas() {
        return medidaCorporalData.findAll();
    }

    public MedidaCorporal obtenerPorId(int id) {
        return medidaCorporalData.findById(id);
    }

    public void guardar(MedidaCorporal medida) {
        medidaCorporalData.guardar(medida);
    }

    public void actualizar(MedidaCorporal medida) {
        medidaCorporalData.update(medida);
    }

    public void eliminar(int id) {
        medidaCorporalData.delete(id);
    }
}
