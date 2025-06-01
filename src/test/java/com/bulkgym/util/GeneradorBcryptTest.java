package com.bulkgym.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneradorBcryptTest {

    @Test
    void generarHash1234() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("1234");
        System.out.println("Hash generado para 1234: " + hash);
    }
    
    
    @Test
    void generarHashEntrenador() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("entrenador123");
        System.out.println("Hash generado para entrenador123: " + hash);
    }

    
}
