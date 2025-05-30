package com.bulkgym.restcontroller;

import com.bulkgym.business.ItemRutinaEjercicioBusiness;
import com.bulkgym.domain.ItemRutinaEjercicio;
import com.bulkgym.dto.RespuestaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/itemEjercicios")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemRutinaEjercicioRestController {

    @Autowired
    private ItemRutinaEjercicioBusiness business;

    @GetMapping
    public ResponseEntity<List<ItemRutinaEjercicio>> listarTodos() {
        return ResponseEntity.ok(business.findAllItems());
    }

    @GetMapping("/buscar")
    public ResponseEntity<ItemRutinaEjercicio> buscarPorId(
            @RequestParam int idRutina,
            @RequestParam int idEjercicio) {
        ItemRutinaEjercicio item = business.buscarItemPorId(idRutina, idEjercicio);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> insertar(@RequestBody ItemRutinaEjercicio item) {
        business.insertarItem(item);
        return ResponseEntity.ok(new RespuestaDTO("Ítem insertado con éxito."));
    }

    @PutMapping
    public ResponseEntity<RespuestaDTO> modificar(
            @RequestParam int idRutina,
            @RequestParam int idEjercicio,
            @RequestBody Map<String, Object> body) {
        int nuevasSeries = (int) body.get("series");
        int nuevasRepeticiones = (int) body.get("repeticiones");
        String nuevoEquipo = (String) body.get("equipo");

        business.modificarItemPorId(idRutina, idEjercicio, nuevasSeries, nuevasRepeticiones, nuevoEquipo);
        return ResponseEntity.ok(new RespuestaDTO("Ítem modificado con éxito."));
    }

    @DeleteMapping
    public ResponseEntity<RespuestaDTO> eliminar(
            @RequestParam int idRutina,
            @RequestParam int idEjercicio) {
        boolean eliminado = business.eliminarItem(idRutina, idEjercicio);
        if (eliminado) {
            return ResponseEntity.ok(new RespuestaDTO("Ítem eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body(new RespuestaDTO("Ítem no encontrado."));
        }
    }

    @GetMapping("/cantidad")
    public Map<String, Integer> contarItems() {
        int cantidad = business.contarItems();
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("cantidadItemRutinaEjercicio", cantidad);
        return respuesta;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaDTO> manejarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new RespuestaDTO(ex.getMessage()));
    }
}
