package com.bulkgym.dto;

import java.util.List;
import java.util.Date;

import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.ItemRutinaEjercicio;

/**
 * 
 */
public class ReporteRutinaDTO {

    // Datos del cliente
    private String nombreCliente;
    private String telefonoCliente;
    private int edadCliente;

    // Datos de la rutina
    private int idRutina;
    private String objetivo;
    private Date fechaCreacion;

    // Listas asociadas
    private List<ItemRutinaMedida> medidas;
    private List<ItemRutinaEjercicio> ejercicios;

    // Getters y setters
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ItemRutinaMedida> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<ItemRutinaMedida> medidas) {
        this.medidas = medidas;
    }

    public List<ItemRutinaEjercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ItemRutinaEjercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

	public int getIdRutina() {
		return idRutina;
	}

	public void setIdRutina(int idRutina2) {
		this.idRutina = idRutina2;
	}

	public int getEdadCliente() {
		return edadCliente;
	}

	public void setEdadCliente(int edadCliente) {
		this.edadCliente = edadCliente;
	}
    
    
}
