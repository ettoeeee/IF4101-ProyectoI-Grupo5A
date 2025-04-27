package com.bulkgym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/logout").permitAll()
                .requestMatchers("/api/clientes/**").permitAll()  //o .hasRole("ADMINISTRADOR")
                .requestMatchers("/api/medidas/**").permitAll()
                .requestMatchers("/api/instructores/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/trainer/**").hasRole("ENTRENADOR")
                .anyRequest().authenticated()
            )
           
            .logout(logout -> logout
                .logoutUrl("/api/logout") // NUEVO: Logout en /api/logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .build();
    }
}


