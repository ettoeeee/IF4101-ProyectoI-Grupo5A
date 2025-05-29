package com.bulkgym.restcontroller;

import com.bulkgym.business.ItemRutinaMedidaBusiness;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemMedidas")
@CrossOrigin(origins = "http://localhost:4200") 
public class ItemRutinaMedidaRestController {

    @Autowired
    private ItemRutinaMedidaBusiness business;
    
    @GetMapping("/medidasItem")
    public ResponseEntity<List<MedidaCorporal>> obtenerTodasLasMedidas() {
        return ResponseEntity.ok(business.obtenerTodasLasMedidas());
    }
    
    @GetMapping
    public ResponseEntity<List<ItemRutinaMedida>> obtenerTodas() {
        return ResponseEntity.ok(business.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRutinaMedida> obtenerPorId(@PathVariable int id) {
        ItemRutinaMedida item = business.obtenerPorId(id);
        return (item != null) ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ItemRutinaMedida> guardar(@RequestBody ItemRutinaMedida item) {
        business.guardar(item);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable int id, @RequestBody ItemRutinaMedida item) {
        ItemRutinaMedida existente = business.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        item.setIdItemRutinaMedida(id);
        business.actualizar(item);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        ItemRutinaMedida existente = business.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        business.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
