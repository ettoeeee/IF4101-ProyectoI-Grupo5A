package com.bulkgym.domain;

public class Empleado extends Persona{
	private int idEmpleado;
	private String rolEmpleado;
	
	public Empleado() {}

	public Empleado(int idEmpleado, String rolEmpleado) {
		super();
		this.idEmpleado = idEmpleado;
		this.rolEmpleado = rolEmpleado;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getRolEmpleado() {
		return rolEmpleado;
	}

	public void setRolEmpleado(String rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	};
	
}
