package com.bulkgym.domain;

import java.util.List;

public class CategoriaEjercicio {
    private int idCategoriaEjercicio;
    private String nombreCategoria;
    private List<Ejercicio> ejercicios;
  
    public CategoriaEjercicio() {
    }


    public CategoriaEjercicio(int codCategoria, String nombreCategoria, List<Ejercicio> ejercicios) {
        this.idCategoriaEjercicio = codCategoria;
        this.nombreCategoria = nombreCategoria;
        this.ejercicios = ejercicios;
    }

  
    public int getCodCategoria() {
        return idCategoriaEjercicio;
    }

    public void setCodCategoria(int codCategoria) {
        this.idCategoriaEjercicio = codCategoria;
    }

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
