package com.bulkgym.restcontroller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulkgym.business.RutinaBusiness;
import com.bulkgym.domain.Rutina;

@RestController
@RequestMapping("/api/rutinas/recientes")
public class RutinasRecientesController {

    @Autowired
    private RutinaBusiness rutinaBusiness;

    @GetMapping
    public ResponseEntity<List<Rutina>> getRutinasRecientes(
            @RequestParam("fechaLimite") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date fechaLimite) {
        
        // Convertir a java.sql.Date si es necesario
        java.sql.Date sqlDate = new java.sql.Date(fechaLimite.getTime());
        List<Rutina> rutinas = rutinaBusiness.obtenerRutinasDesdeFecha(sqlDate);
        return ResponseEntity.ok(rutinas);
    }
}