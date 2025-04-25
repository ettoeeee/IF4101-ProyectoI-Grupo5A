package com.bulkgym.domain;

import java.sql.Date;

public class Persona {
	private int idPersona;
	private String nombre,
				   apellidos,
				   telefono,
				   correoElectronico,
				   imagenRuta;
	private Date fechaNacimiento;
	private char sexo;
	
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
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
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date date) {
		this.fechaNacimiento = date;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	// End of getters-setters.
}// End of class [Persona].
