package com.bulkgym.domain;

public class Ejercicio {
	
	private int idEjercicio;
	private String nombreEjercicio;
	private CategoriaEjercicio categoriaEjercicio;
	
	public Ejercicio() {}

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

	public CategoriaEjercicio getCategoriaEjercicio() {
		return categoriaEjercicio;
	}

	public void setCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
		this.categoriaEjercicio = categoriaEjercicio;
	}
	
	
	
}
