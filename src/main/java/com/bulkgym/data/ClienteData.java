package com.bulkgym.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.Cliente;
import java.util.List;

@Repository
public class ClienteData {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(int idCliente) {
	    String sql = """
	        SELECT c.id_cliente, c.activo,
	               p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
	               p.sexo, p.telefono, p.correo_electronico, p.imagen
	        FROM Cliente c
	        JOIN Persona p ON c.id_persona = p.id_persona
	        WHERE c.id_cliente = ?
	    """;

	    List<Cliente> lista = jdbcTemplate.query(sql, new ClienteExtractor(), idCliente);
	    return lista.isEmpty() ? null : lista.get(0);
	}

	
}// End of class [ClienteData].
