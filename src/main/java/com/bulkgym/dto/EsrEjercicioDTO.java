package com.bulkgym.dto;

public class EsrEjercicioDTO {

	private int idRutina;
    private int idEjercicio;
    private int seriesEjercicio;
    private int repeticionesEjercicio;
    private String equipoEjercicio;

    // Constructor
    public EsrEjercicioDTO() {}

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

    public int getSeriesEjercicio() {
        return seriesEjercicio;
    }

    public void setSeriesEjercicio(int seriesEjercicio) {
        this.seriesEjercicio = seriesEjercicio;
    }

    public int getRepeticionesEjercicio() {
        return repeticionesEjercicio;
    }

    public void setRepeticionesEjercicio(int repeticionesEjercicio) {
        this.repeticionesEjercicio = repeticionesEjercicio;
    }

    public String getEquipoEjercicio() {
        return equipoEjercicio;
    }

    public void setEquipoEjercicio(String equipoEjercicio) {
        this.equipoEjercicio = equipoEjercicio;
    }
	
	
}
