package com.bulkgym.domain;

public class Cliente extends Persona{
	private int idCliente;
	private boolean activo;
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	// End of getters-setters.
}// End of class [Cliente].
