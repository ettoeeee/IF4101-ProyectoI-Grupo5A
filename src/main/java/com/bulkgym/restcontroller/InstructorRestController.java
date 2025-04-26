package com.bulkgym.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bulkgym.data.InstructorData;
import com.bulkgym.domain.Instructor;

import java.util.List;

@RestController
@RequestMapping("/api/instructores")
//@CrossOrigin(origins = "*")
public class InstructorRestController {

    @Autowired
    private InstructorData instructorData;

    // Obtener todos los instructores
    @GetMapping
    public ResponseEntity<List<Instructor>> listarInstructores() {
        List<Instructor> lista = instructorData.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Obtener un instructor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Instructor> obtenerInstructor(@PathVariable int id) {
        Instructor instructor = instructorData.buscarPorId(id);
        if (instructor != null) {
            return ResponseEntity.ok(instructor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo instructor
    @PostMapping
    public ResponseEntity<Void> crearInstructor(@RequestBody Instructor instructor) {
        instructorData.crear(instructor);
        return ResponseEntity.ok().build();
    }

    // Actualizar un instructor existente
    @PutMapping
    public ResponseEntity<Void> actualizarInstructor(@RequestBody Instructor instructor) {
        instructorData.actualizar(instructor);
        return ResponseEntity.ok().build();
    }

    // Eliminar un instructor
    @DeleteMapping("/{idInstructor}/{idPersona}")
    public ResponseEntity<Void> eliminarInstructor(@PathVariable int idInstructor, @PathVariable int idPersona) {
        instructorData.eliminar(idInstructor, idPersona);
        return ResponseEntity.ok().build();
    }
}// End of class [InstructorRestController].
