package com.bulkgym.domain;



public class ItemRutinaMedida {
	private int idItemRutinaMedida;
	private double valorMedida;
	
	private MedidaCorporal medidaCorporal;
	private Rutina rutina;
	
	public int getIdItemRutinaMedida() {
		return idItemRutinaMedida;
	}
	public void setIdItemRutinaMedida(int idItemRutinaMedida) {
		this.idItemRutinaMedida = idItemRutinaMedida;
	}
	public double getValorMedida() {
		return valorMedida;
	}
	public void setValorMedida(double valorMedida) {
		this.valorMedida = valorMedida;
	}
	
	public Rutina getRutina() {
		return rutina;
	}
	public void setRutina(Rutina rutina) {
		this.rutina= rutina;
	}
	
	public MedidaCorporal getMedidaCorporal() {
		return medidaCorporal;
	}
	public void setMedidaCorporal(MedidaCorporal medidaCorporal) {
		this.medidaCorporal = medidaCorporal;
	}
	public ItemRutinaMedida(int valorMedida) {
		super();
		this.valorMedida = valorMedida;
	}
	
	public ItemRutinaMedida() {
	}
}
