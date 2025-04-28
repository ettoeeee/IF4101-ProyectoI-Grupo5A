package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bulkgym.data.CategoriaEjercicioData;
import com.bulkgym.domain.CategoriaEjercicio;


@Service
public class CategoriaBusiness {

    @Autowired
    private CategoriaEjercicioData categoriaData;

    public List<CategoriaEjercicio> obtenerTodas() {
        return categoriaData.findAll();
    }

    public CategoriaEjercicio obtenerPorId(int id) {
        return categoriaData.findById(id);
    }

    public void guardar(CategoriaEjercicio categoria) {
        categoriaData.guardar(categoria);
    }

    public void actualizar(CategoriaEjercicio categoria) {
        categoriaData.update(categoria);
    }

    public void eliminar(int id) {
        categoriaData.delete(id);
    }
}