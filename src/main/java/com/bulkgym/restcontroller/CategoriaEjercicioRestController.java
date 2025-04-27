package com.bulkgym.restcontroller;

import com.bulkgym.business.CategoriaBusiness;
import com.bulkgym.domain.CategoriaEjercicio;
import com.bulkgym.dto.RespuestaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaEjercicioRestController {

    @Autowired
    private CategoriaBusiness categoriaBusiness;

    @GetMapping("/ping")
    public String ping() {
        return "funciona";
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> insertarCategoria(@RequestBody CategoriaEjercicio categoriaEjercicio) {
        categoriaBusiness.insertarCategoria(categoriaEjercicio);
        return ResponseEntity.ok(new RespuestaDTO("Categoría de ejercicio insertada con éxito."));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaEjercicio>> listarCategorias() {
        List<CategoriaEjercicio> categorias = categoriaBusiness.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarCategoria(@PathVariable("id") int id) {
        // Eliminar la categoría utilizando el método de negocio
        boolean eliminado = categoriaBusiness.eliminarCategoriaPorId(id);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Categoría de ejercicio eliminada con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Categoría de ejercicio no encontrada."));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> modificarCategoria(@PathVariable("id") int id, @RequestBody CategoriaEjercicio categoriaEjercicio) {
        categoriaEjercicio.setCodCategoria(id);
        categoriaBusiness.modificarCategoria(id);
        return ResponseEntity.ok(new RespuestaDTO("Categoría de ejercicio modificada con éxito."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
