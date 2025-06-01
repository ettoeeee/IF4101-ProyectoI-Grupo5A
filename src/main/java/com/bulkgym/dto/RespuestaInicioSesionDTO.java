package com.bulkgym.dto;

public class RespuestaInicioSesionDTO {
    private String mensaje;
    private String rol;
    private String token;

    public RespuestaInicioSesionDTO() {}

    public RespuestaInicioSesionDTO(String mensaje, String rol, String token) {
        this.mensaje = mensaje;
        this.rol = rol;
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getRol() {
        return rol;
    }

    public String getToken() {
        return token;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
