package com.bulkgym.domain;

public class FotografiaEjercicio {
    private int idFotografia;
    private String rutaImagen;
    private int idEjercicio;  // El ID del ejercicio relacionado

    public FotografiaEjercicio() {}

    public FotografiaEjercicio(int idFotografia, String rutaImagen, int idEjercicio) {
        this.idFotografia = idFotografia;
        this.rutaImagen = rutaImagen;
        this.idEjercicio = idEjercicio;
    }

    public int getIdFotografia() {
        return idFotografia;
    }

    public void setIdFotografia(int idFotografia) {
        this.idFotografia = idFotografia;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }
}
