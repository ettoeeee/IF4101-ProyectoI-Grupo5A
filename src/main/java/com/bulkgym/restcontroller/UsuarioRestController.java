package com.bulkgym.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bulkgym.domain.Usuario;
import com.bulkgym.data.UsuarioData;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // poner aca el numerito de angular localhost
public class UsuarioRestController {

    @Autowired
    private UsuarioData usuarioData;

    // POST: Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean valido = usuarioData.validarCredenciales(usuario.getUsuario(), usuario.getContrasenia());

        if (valido) {
            return ResponseEntity.ok("Acceso concedido.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }
    }

    // Obtener datos del usuario por nombre (por siacaso)
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String usuario) {
        Usuario u = usuarioData.obtenerPorUsuario(usuario);
        if (u != null) {
            return ResponseEntity.ok(u);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
