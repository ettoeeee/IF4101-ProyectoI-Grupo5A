package com.bulkgym.domain;

import java.util.List;

public class Ejercicio {
	//Atributos
	private int idEjercicio;
	private String nombreEjercicio;
	private List<CategoriaEjercicio> categoriaEjercicio;
	private List<FotografiaEjercicio> fotografiasEjercicios;
	
	public Ejercicio() {}

	public Ejercicio(int idEjercicio, String nombreEjercicio, List<CategoriaEjercicio> categoriaEjercicio, List<FotografiaEjercicio> fotografiasEjercicios) {
		super();
		this.idEjercicio = idEjercicio;
		this.nombreEjercicio = nombreEjercicio;
		this.categoriaEjercicio = categoriaEjercicio;
		this.fotografiasEjercicios = fotografiasEjercicios;
	}

	public int getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getNombreEjercicio() {
		return nombreEjercicio;
	}

	public void setNombreEjercicio(String nombreEjercicio) {
		this.nombreEjercicio = nombreEjercicio;
	}

	public List<CategoriaEjercicio> getCategoriaEjercicio() {
		return categoriaEjercicio;
	}

	public void setCategoriaEjercicio(List<CategoriaEjercicio> categoriaEjercicio) {
		this.categoriaEjercicio = categoriaEjercicio;
	}
	
	public List<FotografiaEjercicio> getFotografiasEjercicio() {
        return fotografiasEjercicios;
    }

    public void setFotografiasEjercicio(List<FotografiaEjercicio> fotografiasEjercicios) {
        this.fotografiasEjercicios = fotografiasEjercicios;
    }
	
}