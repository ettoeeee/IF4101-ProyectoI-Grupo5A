package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.EjercicioData;
import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;

@Service
public class EjercicioBusiness {

    @Autowired
    private EjercicioData ejercicioData;

    public List<Ejercicio> findAllExercises() {
        return ejercicioData.findAll();
    }

    public void insertarEjercicio(Ejercicio ejercicio) {
        ejercicioData.guardar(ejercicio);
    }

    public boolean eliminarEjercicioC(int codEjercicio) {
        ejercicioData.delete(codEjercicio);
        return true;
    }

    public void modificarEjercicioPorId(int codEjercicio, String nuevoNombre, String nuevaImagen, int nuevoIdCategoria) {
        Ejercicio ejercicio = ejercicioData.findById(codEjercicio);
        if (ejercicio == null) {
            throw new IllegalArgumentException("El ejercicio con ID " + codEjercicio + " no existe.");
        }

        ejercicio.setNombreEjercicio(nuevoNombre);
        ejercicio.setImagen(nuevaImagen);

        CategoriaEjercicio nuevaCategoria = new CategoriaEjercicio();
        nuevaCategoria.setIdCategoriaEjercicio(nuevoIdCategoria);

        ejercicio.setCategoriaEjercicio(List.of(nuevaCategoria));

        ejercicioData.update(ejercicio);
    }

    
    public int contarEjercicios() {
        return ejercicioData.contarEjercicios();
    }

    public int contarCategorias() {
        return ejercicioData.contarCategorias();
    }


}
