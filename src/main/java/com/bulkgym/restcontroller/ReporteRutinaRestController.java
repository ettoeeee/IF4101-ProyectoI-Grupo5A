package com.bulkgym.restcontroller;

import com.bulkgym.service.ReporteRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
//@CrossOrigin(origins = "*")
public class ReporteRutinaRestController {

    @Autowired
    private ReporteRutinaService reporteRutinaService;

    @GetMapping("/rutina/{id}")
    public ResponseEntity<byte[]> descargarReporte(@PathVariable int id) {
        try {
            byte[] pdf = reporteRutinaService.generarReporte(id);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rutina_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}
