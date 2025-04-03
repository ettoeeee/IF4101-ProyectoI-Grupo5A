package com.bulkgym.domain;

import java.time.LocalDate;

public class Rutina {
	private int idRutina;
	private LocalDate fechaCreacion,
					  fechaRenovacion;
	private String horario,
			       objetivo,
			       lesiones,
			       padecimientos;
	
	public int getIdRutina() {
		return idRutina;
	}
	public void setIdRutina(int idRutina) {
		this.idRutina = idRutina;
	}
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public LocalDate getFechaRenovacion() {
		return fechaRenovacion;
	}
	public void setFechaRenovacion(LocalDate fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getLesiones() {
		return lesiones;
	}
	public void setLesiones(String lesiones) {
		this.lesiones = lesiones;
	}
	public String getPadecimientos() {
		return padecimientos;
	}
	public void setPadecimientos(String padecimientos) {
		this.padecimientos = padecimientos;
	}
	// End of getters-setters.
}// End of class [Rutina].
