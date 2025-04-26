package com.bulkgym.restcontroller;



import com.bulkgym.business.MedidaCorporalBusiness;
import com.bulkgym.domain.MedidaCorporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medidas")
@CrossOrigin(origins = "http://localhost:4200") 
public class MedidaCorporalRestController {

    @Autowired
    private MedidaCorporalBusiness medidaBusiness;

    @GetMapping
    public ResponseEntity<List<MedidaCorporal>> obtenerTodas() {
        return ResponseEntity.ok(medidaBusiness.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedidaCorporal> obtenerPorId(@PathVariable int id) {
        MedidaCorporal medida = medidaBusiness.obtenerPorId(id);
        return (medida != null) ? ResponseEntity.ok(medida) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MedidaCorporal> guardar(@RequestBody MedidaCorporal medida) {
        medidaBusiness.guardar(medida);
        return ResponseEntity.ok(medida); // devuelve el objeto con el ID generado
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable int id, @RequestBody MedidaCorporal medida) {
        MedidaCorporal existente = medidaBusiness.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        medida.setIdMedidaCorporal(id);
        medidaBusiness.actualizar(medida);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        MedidaCorporal existente = medidaBusiness.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        medidaBusiness.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}