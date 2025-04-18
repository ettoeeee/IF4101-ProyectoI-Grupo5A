package com.bulkgym.domain;

import java.sql.Date;

public class Instructor extends Persona{

	private int idInstructor;
	private Date fechaIngreso;
	
	public int getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(int idInstructor) {
		this.idInstructor = idInstructor;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date date) {
		this.fechaIngreso = date;
	}
	// End of getters-setters.	
}// End of class [Instructor].
