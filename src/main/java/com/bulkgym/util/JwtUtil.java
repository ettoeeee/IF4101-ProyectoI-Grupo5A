package com.bulkgym.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {
	
	private final String secret = "Jq8LdF93vZpX3mTqE9wS6rR7FbMvC4UyHtNgAjKlZiWxPbQrSnBvLtGhMdNyZpKs"; // Debe ser >=256 bits
    private final long expiracion = 1000 * 60 * 25; // 25 minutos

    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generarToken(String username, String rol) {
        return Jwts.builder()
            .setSubject(username)
            .claim("rol", rol)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiracion))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validarToken(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String obtenerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }

    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }

}