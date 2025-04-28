package com.bulkgym.restcontroller;

import com.bulkgym.business.CategoriaBusiness;
import com.bulkgym.domain.CategoriaEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaEjercicioRestController {

    @Autowired
    private CategoriaBusiness categoriaBusiness;

    @GetMapping
    public ResponseEntity<List<CategoriaEjercicio>> obtenerTodas() {
        return ResponseEntity.ok(categoriaBusiness.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEjercicio> obtenerPorId(@PathVariable int id) {
        CategoriaEjercicio categoria = categoriaBusiness.obtenerPorId(id);
        return (categoria != null) ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoriaEjercicio> guardar(@RequestBody CategoriaEjercicio categoria) {
        categoriaBusiness.guardar(categoria);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable int id, @RequestBody CategoriaEjercicio categoria) {
        CategoriaEjercicio existente = categoriaBusiness.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        categoria.setIdCategoriaEjercicio(id);
        categoriaBusiness.actualizar(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        CategoriaEjercicio existente = categoriaBusiness.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        categoriaBusiness.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
