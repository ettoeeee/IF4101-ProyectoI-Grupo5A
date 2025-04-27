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
    
    public List<CategoriaEjercicio> obtenerTodasLasCategorias() {
        return categoriaData.obtenerTodasLasCategorias();
    }

    public List<CategoriaEjercicio> buscarCategoriaPorId(int idCategoria) {
        return categoriaData.buscarCategoriaPorId(idCategoria);
    }

    public boolean eliminarCategoriaPorId(int id) {
        // Lógica para eliminar la categoría por ID
        return categoriaData.eliminarCategoriaPorId(id);
    }


    public CategoriaEjercicio modificarCategoria(int idCategoria) {
        return categoriaData.modificarCategoria(idCategoria);
    }

    public void insertarCategoria(CategoriaEjercicio categoriaEjercicio) {
        categoriaData.insertarCategoria(categoriaEjercicio);
    }
}

