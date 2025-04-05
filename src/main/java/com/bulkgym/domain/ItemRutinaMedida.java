package com.bulkgym.domain;

import java.time.LocalDate;

public class ItemRutinaMedida extends MedidaCorporal{
	private int idItemRutinaMedida;
	private double valorMedida;
	private LocalDate fechaMedicion;
	private MedidaCorporal medidaCorporal;
	
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
	public LocalDate getFechaMedicion() {
		return fechaMedicion;
	}
	public void setFechaMedicion(LocalDate fechaMedicion) {
		this.fechaMedicion = fechaMedicion;
	}
	
	public MedidaCorporal getMedidaCorporal() {
		return medidaCorporal;
	}
	public void setMedidaCorporal(MedidaCorporal medidaCorporal) {
		this.medidaCorporal = medidaCorporal;
	}
	public ItemRutinaMedida(int valorMedida, LocalDate fechaMedicion) {
		super();
		this.valorMedida = valorMedida;
		this.fechaMedicion = fechaMedicion;
	}
	
	public ItemRutinaMedida() {
	}
}
