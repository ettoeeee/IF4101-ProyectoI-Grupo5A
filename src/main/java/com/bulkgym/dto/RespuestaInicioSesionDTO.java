package com.bulkgym.dto;

public class RespuestaInicioSesionDTO {
    private String mensaje;
    private String rol;

    public RespuestaInicioSesionDTO() {}
    
    public RespuestaInicioSesionDTO(String mensaje, String rol) {
        this.mensaje = mensaje;
        this.rol = rol;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getRol() {
        return rol;
    }
}
