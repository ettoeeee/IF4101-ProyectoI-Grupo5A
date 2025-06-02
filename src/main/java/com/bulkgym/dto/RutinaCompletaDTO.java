package com.bulkgym.dto;


import java.sql.Date;
import java.util.List;

import com.bulkgym.domain.Cliente;

public class RutinaCompletaDTO {
	 private int idInstructor;
	    private String horario;
	    private String objetivo;
	    private String lesiones;
	    private String padecimientos;
	    private Date fechaCreacion;
	    private Date fechaRenovacion;

	    private List<ItemRutinaMedidaDTO> medidas;
	    private List<ItemRutinaEjercicioDTO> ejercicios;
    
    
	    private int idRutina;

	    public int getIdRutina() {
	        return idRutina;
	    }

	    public void setIdRutina(int idRutina) {
	        this.idRutina = idRutina;
	    }
	    private Cliente cliente;

	    public Cliente getCliente() {
	        return cliente;
	    }

	    public void setCliente(Cliente cliente) {
	        this.cliente = cliente;
	    }
	    
	public int getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(int idInstructor) {
		this.idInstructor = idInstructor;
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
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaRenovacion() {
		return fechaRenovacion;
	}
	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
	public List<ItemRutinaMedidaDTO> getMedidas() {
		return medidas;
	}
	public void setMedidas(List<ItemRutinaMedidaDTO> medidas) {
		this.medidas = medidas;
	}
	public List<ItemRutinaEjercicioDTO> getEjercicios() {
		return ejercicios;
	}
	public void setEjercicios(List<ItemRutinaEjercicioDTO> ejercicios) {
		this.ejercicios = ejercicios;
	}


}
