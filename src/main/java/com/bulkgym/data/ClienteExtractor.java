package com.bulkgym.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class ClienteExtractor implements ResultSetExtractor<List<Cliente>> {

    @Override
    public List<Cliente> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Cliente> mapa = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt("id_cliente");

            Cliente cliente = mapa.get(id);

            if (cliente == null) {
                cliente = new Cliente();
                cliente.setIdCliente(id);
                cliente.setActivo(rs.getBoolean("activo"));

                // Datos desde Persona
                cliente.setIdPersona(rs.getInt("id_persona"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                cliente.setSexo(rs.getString("sexo").charAt(0));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreoElectronico(rs.getString("correo_electronico"));
                cliente.setImagenRuta(rs.getString("imagen"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setNombreContactoEmergencia(rs.getString("nombre_contacto_emergencia"));
                cliente.setTelContactoEmergencia(rs.getString("tel_contacto_emergencia"));

                mapa.put(id, cliente);
            }
        }

        return new ArrayList<>(mapa.values());
    }
}
