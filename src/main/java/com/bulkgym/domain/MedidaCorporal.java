package com.bulkgym.domain;

public class MedidaCorporal {
	private int idMedidaCorporal;
	private String tipoMedida,
				   unidad;
	public int getIdMedidaCorporal() {
		return idMedidaCorporal;
	}
	public void setIdMedidaCorporal(int idMedidaCorporal) {
		this.idMedidaCorporal = idMedidaCorporal;
	}
	public String getTipoMedida() {
		return tipoMedida;
	}
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	
	public MedidaCorporal(int idMedidaCorporal, String tipoMedida, String unidad) {
		super();
		this.idMedidaCorporal = idMedidaCorporal;
		this.tipoMedida = tipoMedida;
		this.unidad = unidad;
	}
	
	public MedidaCorporal() {
	}
}
