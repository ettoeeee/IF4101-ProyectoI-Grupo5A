package com.bulkgym.domain;

public class MedidaCorporal {
	private int codMedida;
	private String nombreMedida,
				   unidadMedida, imagen;
	public int getCodMedida() {
		return codMedida;
	}
	public void setCodMedida(int idMedidaCorporal) {
		this.codMedida = idMedidaCorporal;
	}
	public String getNombreMedida() {
		return nombreMedida;
	}
	public void setNombreMedida(String tipoMedida) {
		this.nombreMedida = tipoMedida;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidad) {
		this.unidadMedida = unidad;
	}
	
	public MedidaCorporal(int idMedidaCorporal, String tipoMedida, String unidad, String imagen) {
		super();
		this.codMedida = idMedidaCorporal;
		this.nombreMedida = tipoMedida;
		this.unidadMedida = unidad;
		this.imagen= imagen;
	}
	
	public MedidaCorporal() {
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
}
