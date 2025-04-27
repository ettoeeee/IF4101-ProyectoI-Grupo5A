package com.bulkgym.dto;

import java.util.List;

import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Rutina;

public class EjercicioDTO {

    private String nombreEjercicio;
    private String equipo;
    private int series;
    private int repeticiones;
    private List<CategoriaEjercicio> categoriaEjercicio;
    private List<Rutina> rutina;

    // Getters y Setters
    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public List<CategoriaEjercicio> getCategoriaEjercicio() {
        return categoriaEjercicio;
    }

    public void setCategoriaEjercicio(List<CategoriaEjercicio> categoriaEjercicio) {
        this.categoriaEjercicio = categoriaEjercicio;
    }

    public List<Rutina> getRutina() {
        return rutina;
    }

    public void setRutina(List<Rutina> rutina) {
        this.rutina = rutina;
    }
}
