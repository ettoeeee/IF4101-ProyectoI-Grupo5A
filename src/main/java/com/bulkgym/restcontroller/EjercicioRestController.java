package com.bulkgym.restcontroller;

import com.bulkgym.business.EjercicioBusiness;
import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.FotografiaEjercicio;
import com.bulkgym.dto.EjercicioDTO;
import com.bulkgym.dto.RespuestaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioRestController {

    @Autowired
    private EjercicioBusiness ejercicioBusiness;

    // Ruta para verificar el estado del servidor
    @GetMapping("/ping")
    public String ping() {
        return "funciona";
    }

    // Ruta para insertar un nuevo ejercicio
    @PostMapping
    public ResponseEntity<?> insertarEjercicio(@RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = mapearDtoAEjercicio(ejercicioDTO);
        ejercicioBusiness.insertarEjercicio(ejercicio);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Ejercicio insertado con éxito.");
        response.put("idEjercicio", ejercicio.getIdEjercicio()); // aquí devolvemos el ID generado

        return ResponseEntity.ok(response);
    }


    // Ruta para listar todos los ejercicios
    @GetMapping
    public ResponseEntity<List<Ejercicio>> listarEjercicios() {
        List<Ejercicio> ejercicios = ejercicioBusiness.findAllExercises();
        return ResponseEntity.ok(ejercicios);
    }

    // Ruta para eliminar un ejercicio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarEjercicio(@PathVariable("id") int id) {
        boolean eliminado = ejercicioBusiness.eliminarEjercicioC(id);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Ejercicio eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Ejercicio no encontrado."));
        }
    }

    // Ruta para modificar un ejercicio por ID
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> modificarEjercicio(@PathVariable("id") int id, @RequestBody EjercicioDTO ejercicioDTO) {
        ejercicioBusiness.modificarEjercicioPorId(id, ejercicioDTO.getNombreEjercicio());
        return ResponseEntity.ok(new RespuestaDTO("Ejercicio modificado con éxito."));
    }

    // Ruta para obtener las fotografías de un ejercicio
    @GetMapping("/{id}/fotografias")
    public ResponseEntity<List<FotografiaEjercicio>> obtenerFotografiasDeEjercicio(@PathVariable("id") int id) {
        Ejercicio ejercicio = ejercicioBusiness.findAllExercises().stream()
                .filter(e -> e.getIdEjercicio() == id)
                .findFirst()
                .orElse(null);
        if (ejercicio != null) {
            return ResponseEntity.ok(ejercicio.getFotografiasEjercicio());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @PostMapping(value = "/{id}/fotografias", consumes = "multipart/form-data")
    public ResponseEntity<RespuestaDTO> agregarFotografiaAEjercicio(
        @PathVariable("id") int id,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(new RespuestaDTO("Archivo vacío."));
            }

            Ejercicio ejercicio = ejercicioBusiness.findAllExercises().stream()
                    .filter(e -> e.getIdEjercicio() == id)
                    .findFirst()
                    .orElse(null);

            if (ejercicio == null) {
                return ResponseEntity.status(404).body(new RespuestaDTO("Ejercicio no encontrado."));
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads/ejercicios/";
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            java.nio.file.Path filePath = uploadPath.resolve(filename);

            file.transferTo(filePath.toFile());

            FotografiaEjercicio fotografia = new FotografiaEjercicio();
            fotografia.setIdEjercicio(id);
            fotografia.setRutaImagen(filename);  // 👈👈 SOLO el nombre, no todo el path

            ejercicioBusiness.insertarFotografia(fotografia);

            return ResponseEntity.ok(new RespuestaDTO("Fotografía asociada y guardada con éxito."));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new RespuestaDTO("Error al guardar la fotografía: " + e.getMessage()));
        }
    }



    // Ruta para eliminar una fotografía asociada a un ejercicio
    @DeleteMapping("/{id}/fotografias/{fotoId}")
    public ResponseEntity<RespuestaDTO> eliminarFotografia(@PathVariable("id") int id, @PathVariable("fotoId") int fotoId) {
        Ejercicio ejercicio = ejercicioBusiness.findAllExercises().stream()
                .filter(e -> e.getIdEjercicio() == id)
                .findFirst()
                .orElse(null);

        if (ejercicio != null) {
            FotografiaEjercicio fotografia = ejercicio.getFotografiasEjercicio().stream()
                    .filter(f -> f.getIdFotografia() == fotoId)
                    .findFirst()
                    .orElse(null);

            if (fotografia != null) {
                ejercicioBusiness.eliminarFotografia(fotografia);
                return ResponseEntity.ok(new RespuestaDTO("Fotografía eliminada con éxito."));
            } else {
                return ResponseEntity.status(404).body(new RespuestaDTO("Fotografía no encontrada."));
            }
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Ejercicio no encontrado."));
        }
    }

    private Ejercicio mapearDtoAEjercicio(EjercicioDTO dto) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio(dto.getNombreEjercicio());

        // Mapeo de categoría (crear una lista con 1 categoría a partir del ID recibido)
        CategoriaEjercicio categoria = new CategoriaEjercicio();
        categoria.setIdCategoriaEjercicio(dto.getIdCategoria());

        ejercicio.setCategoriaEjercicio(List.of(categoria)); // ✅ Le pasamos una lista con 1 sola categoría

        return ejercicio;
    }


    // Manejo de excepciones
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
