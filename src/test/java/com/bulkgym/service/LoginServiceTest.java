package com.bulkgym.service;

import com.bulkgym.data.UsuarioData;
import com.bulkgym.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private UsuarioData usuarioData;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    public LoginServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidarCredenciales_Correctas() {
        String usuario = "admin";
        String contrasenia = "admin123";
        String hash = "$2a$10$wvhOaKoK0LgUlku2wMZcz.wetUVQDBBkxB6i9EmDi1RGOx/Nhn/YO"; // hash de admin123

        Usuario user = new Usuario(usuario, hash);
        user.setRolUsuario("ADMIN");

        when(usuarioData.obtenerPorUsuario(usuario)).thenReturn(user);
        when(passwordEncoder.matches(contrasenia, hash)).thenReturn(true);

        assertTrue(loginService.validarCredenciales(usuario, contrasenia));
    }

    @Test
    void testValidarCredenciales_Incorrectas() {
        when(usuarioData.obtenerPorUsuario("admin")).thenReturn(null);
        assertFalse(loginService.validarCredenciales("admin", "wrongpass"));
    }

    @Test
    void testObtenerUsuarioPorNombre() {
        Usuario user = new Usuario("instructor", "entrenador123");
        when(usuarioData.obtenerPorUsuario("instructor")).thenReturn(user);

        Usuario resultado = loginService.obtenerUsuarioPorNombre("instructor");
        assertEquals("instructor", resultado.getUsuario());
    }
}
