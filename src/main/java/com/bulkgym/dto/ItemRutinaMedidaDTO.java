package com.bulkgym.dto;

import java.sql.Date;

public class ItemRutinaMedidaDTO {


    private int idMedidaCorporal;
    private double valorMedida;
    private Date fechaMedicion;
    
    
   
    
    
	public int getIdMedidaCorporal() {
		return idMedidaCorporal;
	}
	public void setIdMedidaCorporal(int idMedidaCorporal) {
		this.idMedidaCorporal = idMedidaCorporal;
	}
	public double getValorMedida() {
		return valorMedida;
	}
	public void setValorMedida(double valorMedida) {
		this.valorMedida = valorMedida;
	}
	public Date getFechaMedicion() {
		return fechaMedicion;
	}
	public void setFechaMedicion(Date fechaMedicion) {
		this.fechaMedicion = fechaMedicion;
	}
   
    
    
}
