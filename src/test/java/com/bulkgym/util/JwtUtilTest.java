package com.bulkgym.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerarYValidarToken_Admin() {
        String token = jwtUtil.generarToken("admin", "ADMIN");

        assertTrue(jwtUtil.validarToken(token));
        assertEquals("admin", jwtUtil.obtenerUsername(token));
        assertEquals("ADMIN", jwtUtil.obtenerRol(token));
    }

    @Test
    void testGenerarYValidarToken_Instructor() {
        String token = jwtUtil.generarToken("instructor", "INSTRUCTOR");

        Claims claims = jwtUtil.obtenerClaims(token);
        assertEquals("instructor", claims.getSubject());
        assertEquals("INSTRUCTOR", claims.get("rol"));
    }

    @Test
    void testTokenInvalido() {
        String tokenMalformado = "invalid.token.structure";
        assertFalse(jwtUtil.validarToken(tokenMalformado));
    }
}
