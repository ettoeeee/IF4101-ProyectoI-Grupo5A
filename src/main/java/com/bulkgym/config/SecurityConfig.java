package com.bulkgym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .securityMatcher("/api/**") 
            .authorizeHttpRequests(auth -> auth

                // Endpoints públicos
                .requestMatchers(
                    "/api/login",
                    "/api/logout",
                    "/uploads/**"
                ).permitAll()

                // Rutas accesibles por ADMINISTRADOR e INSTRUCTOR
                .requestMatchers(
                    "/api/clientes/**",
                    "/api/medidas/**", // ACORDARSE DE ESTE PUNTO
                    "/api/categorias/**",
                    "/api/ejercicios/**"
                ).hasAnyRole("ADMINISTRADOR", "INSTRUCTOR")

                // Rutas exclusivas de ADMINISTRADOR
                .requestMatchers(
                    "/api/empleados/**",
                    "/api/instructores/**"
                ).hasRole("ADMINISTRADOR")

                // Rutas exclusivas de INSTRUCTOR
                .requestMatchers(
                    "/api/itemMedidas/medidasItem/**",
                    "/api/medidasItem/**",
                    "/api/itemMedidas/",
                    "/api/item-rutina-ejercicios",
                    "/api/itemEjercicios",
                    "/api/rutinas/recientes/**",
                    "/api/rutinas/**",
                    "/api/rutinas/*/pdf",
                    "/api/clientes/**",	
                    "/api/clientes/*/rutinas/vigente",
                    "/api/itemMedidas/medidasItem",   
                    "/api/ejercicios/**"        ,      
                   
                    "/api/clientes/**/rutinas/**/pdf"
                ).hasRole("INSTRUCTOR")

                
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/trainer/**").hasRole("INSTRUCTOR")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


