package com.bulkgym.restcontroller;

import com.bulkgym.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logout")
public class LogoutRestController {

    @Autowired
    private LogoutService logoutService;

    @PostMapping
    public ResponseEntity<String> procesarLogout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.cerrarSesion(request, response);
        return ResponseEntity.ok("Sesión cerrada exitosamente.");
    }
}
