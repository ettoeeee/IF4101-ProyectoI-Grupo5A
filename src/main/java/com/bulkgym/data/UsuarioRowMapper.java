package com.bulkgym.data;

import com.bulkgym.domain.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUsuario(rs.getString("nombre_usuario"));
        usuario.setContrasenia(rs.getString("contrasenia_usuario"));
        usuario.setRolUsuario(rs.getString("rol_usuario"));
        return usuario;
    }
}

