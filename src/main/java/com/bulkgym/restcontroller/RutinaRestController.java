package com.bulkgym.restcontroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bulkgym.business.RutinaBusiness;
import com.bulkgym.domain.Rutina;
import com.bulkgym.dto.ReporteRutinaDTO;
import com.bulkgym.dto.RutinaCompletaDTO;
import com.bulkgym.util.ReportePdfUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/clientes/{idCliente}/rutinas")
public class RutinaRestController {

    @Autowired
    private RutinaBusiness rutinaBusiness;
    
    @Autowired
    private ReportePdfUtil reportePdfUtil;

    @GetMapping
    public List<Rutina> listarRutinas(@PathVariable int idCliente) {
        return rutinaBusiness.obtenerRutinas(idCliente);
    }
    
    @GetMapping("/buscar")
    public List<Rutina> buscarRutinasPorNombreCliente(@RequestParam String nombreCliente) {
        return rutinaBusiness.obtenerRutinasPorNombreCliente(nombreCliente);
    }

    @GetMapping("/{idRutina}")
    public ResponseEntity<Rutina> obtenerRutina(
        @PathVariable int idCliente,
        @PathVariable int idRutina
    ) {
        Rutina r = rutinaBusiness.buscarRutina(idRutina);
        if (r == null || r.getIdCliente() != idCliente) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Rutina> crearRutina(
        @PathVariable int idCliente,
        @RequestBody Rutina rutina
    ) {
        // Fuerza el uso de idCliente que viene en la ruta, 
        // ignorando cualquier otro valor que el JSON pudiera incluir.
        rutina.setIdCliente(idCliente);

        Rutina creada = rutinaBusiness.crearRutina(idCliente, rutina);

        // Construye la URL relativa sin el contexto de /bulk-gym, 
        // Spring agregará context-path automáticamente si está configurado.
        URI location = URI.create(
          String.format("/api/clientes/%d/rutinas/%d", idCliente, creada.getIdRutina())
        );
        return ResponseEntity.created(location).body(creada);
    }
    
    @GetMapping("/{idRutina}/pdf")
    public ResponseEntity<byte[]> descargarPdfRutina(
            @PathVariable("idCliente") int idCliente,
            @PathVariable("idRutina")  int idRutina
    ) {
    	System.out.println("[DEBUG] >>> LLEGÓ A descargarPdfRutina: clienteId=" 
    		    + idCliente + ", rutinaId=" + idRutina);
    		Rutina r = rutinaBusiness.buscarRutina(idRutina);
    		System.out.println("[DEBUG]    → Objeto Rutina cargado: " + r);
    		System.out.println("[DEBUG]      r.getIdRutina()=" + r.getIdRutina() 
    		    + ",  r.getIdCliente()=" + r.getIdCliente());
    		if (r == null || r.getIdCliente() != idCliente) {
    		    System.out.println("[DEBUG]    → Devolviendo 404 porque r es null o idCliente no coincide. "
    		        + "r.getIdCliente()=" + (r == null ? "null" : r.getIdCliente()) 
    		        + ", idCliente(path)=" + idCliente);
    		    return ResponseEntity.notFound().build();
    		}


        ReporteRutinaDTO dto = rutinaBusiness.obtenerReporteRutinaDTO(idRutina);
        if (dto == null) {
            System.out.println("[DEBUG]  → Devolviendo 404 porque obtenerReporteRutinaDTO retornó null");
            return ResponseEntity.notFound().build();
        }

        System.out.println("[DEBUG]  → Generando PDF para rutina " + idRutina);
        byte[] pdfBytes = reportePdfUtil.generarPdfDesdeRutina(dto);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"rutina_" + idRutina + ".pdf\"")
                .body(pdfBytes);
    }

    
    //VEROO
    @PostMapping("/completa")
    public ResponseEntity<Rutina> crearRutinaCompleta(
        @PathVariable int idCliente,
        @RequestBody RutinaCompletaDTO dto
    ) {
        Rutina creada = rutinaBusiness.crearRutinaCompleta(idCliente, dto);
        URI location = URI.create("/api/clientes/" + idCliente + "/rutinas/" + creada.getIdRutina());
        return ResponseEntity.created(location).body(creada);
    }

}
