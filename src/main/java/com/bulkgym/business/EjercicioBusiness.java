package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.EjercicioData;
import com.bulkgym.data.FotografiaEjercicioData;
import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.FotografiaEjercicio;

@Service
public class EjercicioBusiness {
	
	@Autowired
	private EjercicioData ejercicioData;
	
    @Autowired
    private FotografiaEjercicioData fotografiaEjercicioData; // <-- AGREGA EL @Autowired AQUÍ
	
	// Obtener todos los ejercicios registrados
    public List<Ejercicio> findAllExercises() {
        return ejercicioData.findAll();
    }

    // Insertar un nuevo ejercicio
    public void insertarEjercicio(Ejercicio ejercicio) {
        ejercicioData.guardar(ejercicio);
    }

    // Eliminar ejercicio por ID
    public boolean eliminarEjercicioC(int codEjercicio) {
        // Si el método delete() retorna void, no hace falta capturar el valor de retorno
        ejercicioData.delete(codEjercicio);
        return true; // Asumimos que siempre será exitoso si no ocurre una excepción
    }

    // Modificar ejercicio por ID (cambiar el nombre)
    public void modificarEjercicioPorId(int codEjercicio, String nuevoNombre) {
        Ejercicio ejercicio = ejercicioData.findById(codEjercicio);
        if (ejercicio != null) {
            ejercicio.setNombreEjercicio(nuevoNombre);
            ejercicioData.update(ejercicio);
        }
    }
    
    // Método para insertar una fotografía asociada a un ejercicio
    public void insertarFotografia(FotografiaEjercicio fotografia) {
        fotografiaEjercicioData.guardar(fotografia); // Llamamos a la capa de datos para insertar la foto
    }
    
    // Método para eliminar una fotografía de un ejercicio
    public void eliminarFotografia(FotografiaEjercicio fotografia) {
        fotografiaEjercicioData.delete(fotografia.getIdFotografia());
    }
}
