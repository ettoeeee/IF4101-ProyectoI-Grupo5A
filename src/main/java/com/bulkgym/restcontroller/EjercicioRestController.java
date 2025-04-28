package com.bulkgym.restcontroller;

import com.bulkgym.business.EjercicioBusiness;
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
import java.util.List;

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
    public ResponseEntity<RespuestaDTO> insertarEjercicio(@RequestBody EjercicioDTO ejercicioDTO) {
        Ejercicio ejercicio = mapearDtoAEjercicio(ejercicioDTO);
        ejercicioBusiness.insertarEjercicio(ejercicio);
        return ResponseEntity.ok(new RespuestaDTO("Ejercicio insertado con éxito."));
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
        @RequestParam("file") MultipartFile file,
        @RequestParam("ejercicio") String ejercicioJson
    ) {
        try {
            // Procesar el ejercicio (suponiendo que es un JSON que necesita ser deserializado)
            ObjectMapper objectMapper = new ObjectMapper();
            EjercicioDTO ejercicioDTO = objectMapper.readValue(ejercicioJson, EjercicioDTO.class);

            // Buscar el ejercicio
            Ejercicio ejercicio = ejercicioBusiness.findAllExercises().stream()
                    .filter(e -> e.getIdEjercicio() == id)
                    .findFirst()
                    .orElse(null);

            if (ejercicio == null) {
                return ResponseEntity.status(404).body(new RespuestaDTO("Ejercicio no encontrado."));
            }

            // Guardar la foto en el sistema
            String uploadDir = "uploads/ejercicios/";
            String originalFilename = file.getOriginalFilename();
            String filePath = uploadDir + System.currentTimeMillis() + "_" + originalFilename;

            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            java.nio.file.Path path = java.nio.file.Paths.get(filePath);
            file.transferTo(path.toFile());

            // Asociar la fotografía al ejercicio y guardar en la base de datos
            FotografiaEjercicio fotografia = new FotografiaEjercicio();
            fotografia.setIdEjercicio(id);
            fotografia.setRutaImagen(filePath);
            ejercicioBusiness.insertarFotografia(fotografia);

            return ResponseEntity.ok(new RespuestaDTO("Fotografía asociada y guardada con éxito."));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(new RespuestaDTO("Error al guardar la fotografía."));
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

    // Función privada para mapear de DTO a Ejercicio
    private Ejercicio mapearDtoAEjercicio(EjercicioDTO dto) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombreEjercicio(dto.getNombreEjercicio());
        ejercicio.setCategoriaEjercicio(dto.getCategoriaEjercicio());
        return ejercicio;
    }

    // Manejo de excepciones
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
