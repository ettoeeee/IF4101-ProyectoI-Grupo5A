package com.bulkgym.domain;

import java.time.LocalDate;

public class Instructor extends Persona{

	private int idInstructor;
	private LocalDate fechaIngreso;
	
	public int getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(int idInstructor) {
		this.idInstructor = idInstructor;
	}
	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	// End of getters-setters.	
}// End of class [Instructor].
