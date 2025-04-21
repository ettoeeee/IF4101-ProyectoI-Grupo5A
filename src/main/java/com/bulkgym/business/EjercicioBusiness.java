package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.EjercicioData;
import com.bulkgym.domain.Ejercicio;

@Service
public class EjercicioBusiness {
	
	@Autowired
	private EjercicioData ejercicioData;
	
	 // Obtener todos los ejercicios registrados
    public List<Ejercicio> findAllExercises() {
        return ejercicioData.buscarTodosEjercicios();
    }

    // Insertar un nuevo ejercicio
    public void insertarEjercicio(Ejercicio ejercicio) {
        ejercicioData.insertarEjercicio(ejercicio);
    }

    // Eliminar ejercicio por ID
    public boolean eliminarEjercicioC(int codEjercicio) {
        return ejercicioData.eliminarEjercicio(codEjercicio);
    }

    // Modificar un ejercicio por ID (actualmente simulado en EjercicioData)
    public void modificarEjercicioPorId(int codEjercicio) {
        ejercicioData.modificarEjercicio(codEjercicio);
    }
	

}
