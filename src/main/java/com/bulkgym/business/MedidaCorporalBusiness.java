package com.bulkgym.business;

import java.util.ArrayList;
import java.util.List;

import com.bulkgym.domain.MedidaCorporal;

public class MedidaCorporalBusiness {

	private List<MedidaCorporal> medidas = new ArrayList<>();
    private int contadorId = 1; // Simula autoincremento

    // Agregar medidas corporales
    public void agregarMedida(String tipoMedida, String unidad) {
        MedidaCorporal nueva = new MedidaCorporal();
        nueva.setIdMedidaCorporal(contadorId++);
        nueva.setTipoMedida(tipoMedida);
        nueva.setUnidad(unidad);
        medidas.add(nueva);
    }

    // Buscar y retornar toda la lista de las medidas corporales
    public List<MedidaCorporal> obtenerTodas() {
        return medidas;
    }

    // READ (buscar por ID)
    public MedidaCorporal obtenerPorId(int id) {
        for (MedidaCorporal m : medidas) {
            if (m.getIdMedidaCorporal() == id) {
                return m;
            }
        }
        return null;
    }

    // Actualizar medidas corporales
    public boolean actualizarMedida(int id, String nuevoTipo, String nuevaUnidad) {
        MedidaCorporal medida = obtenerPorId(id);
        if (medida != null) {
            medida.setTipoMedida(nuevoTipo);
            medida.setUnidad(nuevaUnidad);
            return true;
        }
        return false;
    }

    // Eliminar medidas corporales
    public boolean eliminarMedida(int id) {
        MedidaCorporal medida = obtenerPorId(id);
        if (medida != null) {
            medidas.remove(medida);
            return true;
        }
        return false;
    }
}
