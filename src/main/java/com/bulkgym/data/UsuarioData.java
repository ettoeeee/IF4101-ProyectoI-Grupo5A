package com.bulkgym.data;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.Usuario;

import java.util.List;

@Repository
public class UsuarioData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public boolean validarCredenciales(String usuario, String contrasenia) {
        String sql = """
            SELECT * FROM Usuario
            WHERE usuario = ? AND contrasenia = ?
        """;

        @SuppressWarnings("deprecation")
		List<Usuario> lista = jdbcTemplate.query(sql, new Object[]{usuario, contrasenia}, new UsuarioRowMapper());
        return !lista.isEmpty();
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorUsuario(String usuario) {
        String sql = """
            SELECT * FROM Usuario
            WHERE usuario = ?
        """;

        @SuppressWarnings("deprecation")
		List<Usuario> lista = jdbcTemplate.query(sql, new Object[]{usuario}, new UsuarioRowMapper());
        return lista.isEmpty() ? null : lista.get(0);
    }
}
