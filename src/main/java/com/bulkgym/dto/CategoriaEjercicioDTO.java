package com.bulkgym.dto;

import java.util.List;
import com.bulkgym.domain.Ejercicio;

public class CategoriaEjercicioDTO {

    private String nombreCategoria;
    private List<Ejercicio> ejercicios; // Listado de ejercicios asociados a la categoría

    // Getters y Setters
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
