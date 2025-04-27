package com.bulkgym.domain;

import java.util.List;

public class Ejercicio {
	//Atributos
	private int idEjercicio;
	private String nombreEjercicio;
	private List<CategoriaEjercicio> categoriaEjercicio;
	
	public Ejercicio() {}

	public Ejercicio(int idEjercicio, String nombreEjercicio, List<CategoriaEjercicio> categoriaEjercicio) {
		super();
		this.idEjercicio = idEjercicio;
		this.nombreEjercicio = nombreEjercicio;
		this.categoriaEjercicio = categoriaEjercicio;
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
	
	
	
}