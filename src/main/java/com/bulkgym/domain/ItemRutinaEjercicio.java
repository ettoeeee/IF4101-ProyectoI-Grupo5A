package com.bulkgym.domain;


public class ItemRutinaEjercicio {

    private int idRutina;
    private int idEjercicio;
    private Integer seriesEjercicio;
    private Integer repeticionesEjercicio;
    private String equipoEjercicio;

    // Constructor vacío
    public ItemRutinaEjercicio() {
    }

    // Constructor completo
    public ItemRutinaEjercicio(int idRutina, int idEjercicio, Integer seriesEjercicio, Integer repeticionesEjercicio, String equipoEjercicio) {
        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.seriesEjercicio = seriesEjercicio;
        this.repeticionesEjercicio = repeticionesEjercicio;
        this.equipoEjercicio = equipoEjercicio;
    }

    // Getters y setters
    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getSeriesEjercicio() {
        return seriesEjercicio;
    }

    public void setSeriesEjercicio(Integer seriesEjercicio) {
        this.seriesEjercicio = seriesEjercicio;
    }

    public Integer getRepeticionesEjercicio() {
        return repeticionesEjercicio;
    }

    public void setRepeticionesEjercicio(Integer repeticionesEjercicio) {
        this.repeticionesEjercicio = repeticionesEjercicio;
    }

    public String getEquipoEjercicio() {
        return equipoEjercicio;
    }

    public void setEquipoEjercicio(String equipoEjercicio) {
        this.equipoEjercicio = equipoEjercicio;
    }
}
