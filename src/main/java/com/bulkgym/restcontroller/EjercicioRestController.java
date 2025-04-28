package com.bulkgym.restcontroller;

import com.bulkgym.business.EjercicioBusiness;
import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;
import com.bulkgym.dto.EjercicioDTO;
import com.bulkgym.dto.RespuestaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> insertarEjercicio(@RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = mapearDtoAEjercicio(ejercicioDTO);
        ejercicio.setImagen(ejercicioDTO.getImagen()); // 🔥 Imagen en Base64
        ejercicioBusiness.insertarEjercicio(ejercicio);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Ejercicio insertado con éxito.");
        response.put("idEjercicio", ejercicio.getIdEjercicio());

        return ResponseEntity.ok(response);
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
        ejercicioBusiness.modificarEjercicioPorId(
            id,
            ejercicioDTO.getNombreEjercicio(),
            ejercicioDTO.getImagen(),
            ejercicioDTO.getIdCategoria()
        );
        return ResponseEntity.ok(new RespuestaDTO("Ejercicio modificado con éxito."));
    }



    private Ejercicio mapearDtoAEjercicio(EjercicioDTO dto) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio(dto.getNombreEjercicio());

        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoriaEjercicio(dto.getIdCategoria());
        ejercicio.setCategoriaEjercicio(List.of(categoria));

        return ejercicio;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
    
    //panel 
    
    @GetMapping("/cantidad")
    public Map<String, Integer> contarEjercicios() {
        int cantidad = ejercicioBusiness.contarEjercicios();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("cantidadEjercicios", cantidad);
        return respuesta;
    }

    @GetMapping("/categorias/cantidad")
    public Map<String, Integer> contarCategorias() {
        int cantidad = ejercicioBusiness.contarCategorias();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("cantidadCategorias", cantidad);
        return respuesta;
    }

}
