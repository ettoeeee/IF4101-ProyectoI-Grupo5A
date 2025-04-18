package com.bulkgym.restcontroller;

import com.bulkgym.data.UsuarioData;
import com.bulkgym.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioRestController.class)
public class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean // Si se puede cambiar esto en el futuro porque está rezagado y ahorita lo quitan
    private UsuarioData usuarioData;

    @Test
    void loginConCredencialesValidas_deberiaRetornar200() throws Exception {
        // Simula credenciales válidas
        when(usuarioData.validarCredenciales("admin", "1234")).thenReturn(true);

        String jsonBody = """
            {
              "usuario": "admin",
              "contrasenia": "1234"
            }
        """;

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Acceso concedido."));
    }

    @Test
    void loginConCredencialesInvalidas_deberiaRetornar401() throws Exception {
        // Simula credenciales inválidas
        when(usuarioData.validarCredenciales("admin", "wrong")).thenReturn(false);

        String jsonBody = """
            {
              "usuario": "admin",
              "contrasenia": "wrong"
            }
        """;

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales inválidas."));
    }

    @Test
    void obtenerUsuarioExistente_deberiaRetornarUsuarioY200() throws Exception {
        Usuario usuario = new Usuario("juan", "secreta");

        when(usuarioData.obtenerPorUsuario("juan")).thenReturn(usuario);

        mockMvc.perform(get("/api/usuario/juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("juan"))
                .andExpect(jsonPath("$.contrasenia").value("secreta"));
    }

    @Test
    void obtenerUsuarioInexistente_deberiaRetornar404() throws Exception {
        when(usuarioData.obtenerPorUsuario("noexiste")).thenReturn(null);

        mockMvc.perform(get("/api/usuario/noexiste"))
                .andExpect(status().isNotFound());
    }
}
