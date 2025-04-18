package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.bulkgym.domain.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario user = new Usuario(
            rs.getString("usuario"),
            rs.getString("contrasenia")
        );
        return user;
    }
}
