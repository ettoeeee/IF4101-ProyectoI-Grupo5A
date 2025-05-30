package com.bulkgym.business;

import com.bulkgym.data.ItemRutinaEjercicioData;
import com.bulkgym.domain.ItemRutinaEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemRutinaEjercicioBusiness {

    @Autowired
    private ItemRutinaEjercicioData itemRutinaEjercicioData;

    /**
     * Obtiene todos los ítems de rutina-ejercicio.
     */
    public List<ItemRutinaEjercicio> findAllItems() {
        return itemRutinaEjercicioData.findAll();
    }

    /**
     * Inserta un nuevo ítem de rutina-ejercicio.
     */
    public void insertarItem(ItemRutinaEjercicio item) {
        itemRutinaEjercicioData.guardar(item);
    }

    /**
     * Elimina un ítem de rutina-ejercicio por IDs compuestos.
     */
    public boolean eliminarItem(int idRutina, int idEjercicio) {
        itemRutinaEjercicioData.delete(idRutina, idEjercicio);
        return true;
    }

    /**
     * Modifica un ítem de rutina-ejercicio por ID compuesto.
     */
    public void modificarItemPorId(int idRutina, int idEjercicio, int nuevasSeries, int nuevasRepeticiones, String nuevoEquipo) {
        ItemRutinaEjercicio item = itemRutinaEjercicioData.findById(idRutina, idEjercicio);
        if (item == null) {
            throw new IllegalArgumentException("El ítem con ID rutina " + idRutina + " y ejercicio " + idEjercicio + " no existe.");
        }

        item.setSeriesEjercicio(nuevasSeries);
        item.setRepeticionesEjercicio(nuevasRepeticiones);
        item.setEquipoEjercicio(nuevoEquipo);

        itemRutinaEjercicioData.update(item);
    }

    /**
     * Busca un ítem de rutina-ejercicio por ID compuesto.
     */
    public ItemRutinaEjercicio buscarItemPorId(int idRutina, int idEjercicio) {
        return itemRutinaEjercicioData.findById(idRutina, idEjercicio);
    }

    /**
     * Cuenta el total de ítems en la tabla ItemRutinaEjercicio.
     */
    public int contarItems() {
        return itemRutinaEjercicioData.contarItems();
    }
}
