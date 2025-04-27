package com.bulkgym.restcontroller;

import com.bulkgym.dto.SolicitudInicioSesionDTO;
import com.bulkgym.dto.RespuestaInicioSesionDTO;
import com.bulkgym.service.LoginService;
import com.bulkgym.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<?> procesarLogin(
            @RequestBody SolicitudInicioSesionDTO dto,
            BindingResult br) {

        Usuario usuarioEntidad = loginService.obtenerUsuarioPorNombre(dto.getUsuario());

        if (usuarioEntidad != null && loginService.validarCredenciales(dto.getUsuario(), dto.getContrasenia())) {
            return ResponseEntity.ok(new RespuestaInicioSesionDTO("Acceso permitido", usuarioEntidad.getRolUsuario()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}

