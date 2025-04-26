package com.bulkgym.domain;

import java.util.List;

public class Ejercicio {
	//Atributos
	private int idEjercicio;
	private List<Rutina> rutina;
	private List<CategoriaEjercicio> categoriaEjercicio;
	private String equipo;
	private int series;
	private int repeticiones;
	private String nombreEjercicio;
	
	public Ejercicio() {}
	
	public Ejercicio(int idEjercicio, List<Rutina> rutina, List<CategoriaEjercicio> categoriaEjercicio, String equipo,
			int series, int repeticiones, String nombreEjercicio) {
		this.idEjercicio = idEjercicio;
		this.rutina = rutina;
		this.categoriaEjercicio = categoriaEjercicio;
		this.equipo = equipo;
		this.series = series;
		this.repeticiones = repeticiones;
		this.nombreEjercicio = nombreEjercicio;
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

	public List<Rutina> getRutina() {
		return rutina;
	}

	public void setRutina(List<Rutina> rutina) {
		this.rutina = rutina;
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
		
	
}
