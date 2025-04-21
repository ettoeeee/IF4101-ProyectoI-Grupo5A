package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.CategoriaEjercicioData;
import com.bulkgym.domain.CategoriaEjercicio;

@Service
public class CategoriaBusiness {

    @Autowired
    private CategoriaEjercicioData categoriaData;

    public List<CategoriaEjercicio> buscarCategoriaPorId(int idCategoria) {
        return categoriaData.buscarCategoriaPorId(idCategoria);
    }

    public boolean eliminarCategoria(CategoriaEjercicio categoria) {
        return categoriaData.eliminarCategoria(categoria);
    }

    public CategoriaEjercicio modificarCategoria(int idCategoria) {
        return categoriaData.modificarCategoria(idCategoria);
    }

    public void insertarCategoria(CategoriaEjercicio categoriaEjercicio) {
        categoriaData.insertarCategoria(categoriaEjercicio);
    }
}

