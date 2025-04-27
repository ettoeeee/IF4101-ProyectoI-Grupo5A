package com.bulkgym.dto;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EmpleadoDTO {
	
	private int idPersona,
				idEmpleado;
    private char sexo;
    private String nombre, 
    			   apellidos, 
    			   telefono, 
    			   correoElectronico, 
    			   imagenRuta, 
    			   direccion, 
    			   nombreContactoEmergencia, 
    			   telContactoEmergencia,
    			   rolEmpleado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
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
	public String getRolEmpleado() {
		return rolEmpleado;
	}
	public void setRolEmpleado(String rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
    
}
