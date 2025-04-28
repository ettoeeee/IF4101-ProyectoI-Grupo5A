package com.bulkgym.domain;

import java.util.List;

public class CategoriaEjercicio {
    private int idCategoriaEjercicio;
    private String nombreCategoria;
    
    public CategoriaEjercicio() {
    }


    public CategoriaEjercicio(int codCategoria, String nombreCategoria) {
        this.idCategoriaEjercicio = codCategoria;
        this.nombreCategoria = nombreCategoria;
        
    }

    
    public int getIdCategoriaEjercicio() {
		return idCategoriaEjercicio;
	}


	public void setIdCategoriaEjercicio(int idCategoriaEjercicio) {
		this.idCategoriaEjercicio = idCategoriaEjercicio;
	}


	public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

  
}
