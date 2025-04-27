package com.bulkgym.data;

import com.bulkgym.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario obtenerPorUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario = ?";
        List<Usuario> lista = jdbcTemplate.query(sql, new UsuarioRowMapper(), nombreUsuario);
        return lista.isEmpty() ? null : lista.get(0);
    }
}
