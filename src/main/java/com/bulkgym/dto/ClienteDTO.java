	package com.bulkgym.dto;
	
	import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
	
	public class ClienteDTO {
	
	    private int idPersona;
	    private String nombre;
	    private String apellidos;
	    private char sexo;
	    private String telefono;
	    private String correoElectronico;
	    private String imagenRuta;
	    private String direccion;
	    private String nombreContactoEmergencia;
	    private String telContactoEmergencia;
	    private boolean activo;
	    
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	    private Date fechaNacimiento;

	    public int getIdPersona() {
			return idPersona;
		}
		public void setIdPersona(int idPersona) {
			this.idPersona = idPersona;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getNombreContactoEmergencia() {
			return nombreContactoEmergencia;
		}
		public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
			this.nombreContactoEmergencia = nombreContactoEmergencia;
		}
		public String getTelContactoEmergencia() {
			return telContactoEmergencia;
		}
		public void setTelContactoEmergencia(String telContactoEmergencia) {
			this.telContactoEmergencia = telContactoEmergencia;
		}
		public void setFechaNacimiento(Date fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
		}
		public void setSexo(char sexo) {
			this.sexo = sexo;
		}
		// Getters y Setters
	    public String getNombre() {
	        return nombre;
	    }
	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }
	
	    public String getApellidos() {
	        return apellidos;
	    }
	    public void setApellidos(String apellidos) {
	        this.apellidos = apellidos;
	    }
	
	   
	    public String getTelefono() {
	        return telefono;
	    }
	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
	    }
	
	    public String getCorreoElectronico() {
	        return correoElectronico;
	    }
	    public void setCorreoElectronico(String correoElectronico) {
	        this.correoElectronico = correoElectronico;
	    }
	
	    public String getImagenRuta() {
	        return imagenRuta;
	    }
	    public void setImagenRuta(String imagenRuta) {
	        this.imagenRuta = imagenRuta;
	    }
	
	    public boolean isActivo() {
	        return activo;
	    }
	    public void setActivo(boolean activo) {
	        this.activo = activo;
	    }
		public Date getFechaNacimiento() {
			return fechaNacimiento;
		}
		public char getSexo() {
			return sexo;
		}
	    
	}
