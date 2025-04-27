package com.bulkgym.restcontroller;

import com.bulkgym.business.EjercicioBusiness;
import com.bulkgym.domain.Ejercicio;
import com.bulkgym.dto.EjercicioDTO;
import com.bulkgym.dto.RespuestaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioRestController {

    @Autowired
    private EjercicioBusiness ejercicioBusiness;
    
    @GetMapping("/ping")
    public String ping() {
        return "funciona";
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> insertarEjercicio(@RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = mapearDtoAEjercicio(ejercicioDTO);
        ejercicioBusiness.insertarEjercicio(ejercicio);
        return ResponseEntity.ok(new RespuestaDTO("Ejercicio insertado con éxito."));
    }

    @GetMapping
    public ResponseEntity<List<Ejercicio>> listarEjercicios() {
        List<Ejercicio> ejercicios = ejercicioBusiness.findAllExercises();
        return ResponseEntity.ok(ejercicios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarEjercicio(@PathVariable("id") int id) {
        boolean eliminado = ejercicioBusiness.eliminarEjercicioC(id);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Ejercicio eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Ejercicio no encontrado."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> modificarEjercicio(@PathVariable("id") int id, @RequestBody EjercicioDTO ejercicioDTO) {
        ejercicioBusiness.modificarEjercicioPorId(id, ejercicioDTO.getNombreEjercicio());
        return ResponseEntity.ok(new RespuestaDTO("Ejercicio modificado con éxito."));
    }


    // Función privada para mapear de DTO a Ejercicio
    private Ejercicio mapearDtoAEjercicio(EjercicioDTO dto) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio(dto.getNombreEjercicio());
        ejercicio.setCategoriaEjercicio(dto.getCategoriaEjercicio());
        return ejercicio;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
