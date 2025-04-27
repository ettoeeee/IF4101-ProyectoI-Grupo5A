package com.bulkgym.service;

import com.bulkgym.data.UsuarioData;
import com.bulkgym.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioData usuarioData;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validarCredenciales(String usuario, String contrasenia) {
        Usuario usuarioEntidad = usuarioData.obtenerPorUsuario(usuario);
        if (usuarioEntidad != null) {
            String hashGuardado = usuarioEntidad.getContrasenia();
            return passwordEncoder.matches(contrasenia, hashGuardado);
        }
        return false;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return usuarioData.obtenerPorUsuario(nombreUsuario);
    }
}

