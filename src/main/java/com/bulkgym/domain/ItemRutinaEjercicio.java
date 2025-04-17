package com.bulkgym.domain;

public class ItemRutinaEjercicio {
    private int seriesEjercicio;
    private int repeticionesEjercicio;
    
   
    private Ejercicio ejercicio;  
    private Rutina rutina;       


    public ItemRutinaEjercicio() {
    }


    public ItemRutinaEjercicio(int seriesEjercicio, int repeticionesEjercicio, Ejercicio ejercicio, Rutina rutina) {
        this.seriesEjercicio = seriesEjercicio;
        this.repeticionesEjercicio = repeticionesEjercicio;
        this.ejercicio = ejercicio;
        this.rutina = rutina;
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

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }
}
