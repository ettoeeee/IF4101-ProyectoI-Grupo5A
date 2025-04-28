package com.bulkgym.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;  
import com.bulkgym.domain.Cliente;
import com.bulkgym.dto.ClienteDTO;

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

	@Transactional
	public void guardarCliente(Cliente cliente) {
	    if (cliente.getFechaNacimiento() == null) {
	        throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
	    }
	    
	    // 🔥 1. Verificar si ya existe la cédula en Persona
	    Integer count = jdbcTemplate.queryForObject(
	        "SELECT COUNT(*) FROM Persona WHERE id_persona = ?", 
	        Integer.class, 
	        cliente.getIdPersona()
	    );

	    if (count != null && count > 0) {
	        throw new IllegalArgumentException("Ya existe una persona registrada con esta cédula (idPersona).");
	    }

	    String sqlPersona = """
	        INSERT INTO Persona (
	            id_persona, nombre, apellidos, fecha_nacimiento,
	            sexo, telefono, correo_electronico, imagen,
	            direccion, nombre_contacto_emergencia, tel_contacto_emergencia
	        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
	    """;

	    jdbcTemplate.update(sqlPersona,
	        cliente.getIdPersona(),
	        cliente.getNombre(),
	        cliente.getApellidos(),
	        cliente.getFechaNacimiento(),
	        String.valueOf(cliente.getSexo()),
	        cliente.getTelefono(),
	        cliente.getCorreoElectronico(),
	        cliente.getImagenRuta(),
	        cliente.getDireccion(),
	        cliente.getNombreContactoEmergencia(),
	        cliente.getTelContactoEmergencia()
	    );

	    Integer idCliente = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(id_cliente), 0) + 1 FROM Cliente", Integer.class);

	    String sqlCliente = """
	        INSERT INTO Cliente (id_cliente, id_persona, activo)
	        VALUES (?, ?, ?)
	    """;
	    jdbcTemplate.update(sqlCliente, idCliente, cliente.getIdPersona(), cliente.isActivo());

	    cliente.setIdCliente(idCliente);
	}

	
	@Transactional(readOnly = true)
	public List<Cliente> obtenerTodosLosClientes() {
	    String sql = """
	        SELECT c.id_cliente, c.activo,
	               p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
	               p.sexo, p.telefono, p.correo_electronico, p.imagen,
	               p.direccion, p.nombre_contacto_emergencia, p.tel_contacto_emergencia
	        FROM Cliente c
	        JOIN Persona p ON c.id_persona = p.id_persona
	    """;

	    return jdbcTemplate.query(sql, new ClienteExtractor());
	}


	@Transactional
	public boolean eliminarCliente(int idCliente) {
	    try {
	        Integer idPersona = jdbcTemplate.queryForObject(
	            "SELECT id_persona FROM Cliente WHERE id_cliente = ?",
	            Integer.class,
	            idCliente
	        );

	        int filasCliente = jdbcTemplate.update(
	            "DELETE FROM Cliente WHERE id_cliente = ?", idCliente
	        );

	        jdbcTemplate.update(
	            "DELETE FROM Persona WHERE id_persona = ?", idPersona
	        );

	        return filasCliente > 0;

	    } catch (EmptyResultDataAccessException e) {
	        return false;
	    }
	}



	
	@Transactional
	public boolean actualizarCliente(ClienteDTO cliente) {
	    if (cliente.getFechaNacimiento() == null) {
	        throw new IllegalArgumentException("La fecha de nacimiento es obligatoria para actualizar.");
	    }

	    String sql = """
	        UPDATE Persona
	        SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, sexo = ?, telefono = ?, correo_electronico = ?, imagen = ?, direccion = ?, nombre_contacto_emergencia = ?, tel_contacto_emergencia = ?
	        WHERE id_persona = ?
	    """;

	    int result = jdbcTemplate.update(sql,
	        cliente.getNombre(),
	        cliente.getApellidos(),
	        cliente.getFechaNacimiento(),
	        String.valueOf(cliente.getSexo()),
	        cliente.getTelefono(),
	        cliente.getCorreoElectronico(),
	        cliente.getImagenRuta(),
	        cliente.getDireccion(),
	        cliente.getNombreContactoEmergencia(),
	        cliente.getTelContactoEmergencia(),
	        cliente.getIdPersona()
	    );

	    return result > 0;
	}
	 public List<Cliente> obtenerTodosLosClientesP() {
	        String sql = "SELECT * FROM Cliente";
	        return jdbcTemplate.query(sql, new ClienteExtractor());
	    }

	    public int contarClientes() {
	        String sql = "SELECT COUNT(*) FROM Cliente";
	        return jdbcTemplate.queryForObject(sql, Integer.class);
	    }

	    public List<Cliente> obtenerClientesRecientes() {
	        String sql = """
	            SELECT TOP 5 
	                c.id_cliente, c.activo,
	                p.id_persona, p.nombre, p.apellidos, p.fecha_nacimiento,
	                p.sexo, p.telefono, p.correo_electronico, p.imagen,
	                p.direccion, p.nombre_contacto_emergencia, p.tel_contacto_emergencia
	            FROM Cliente c
	            JOIN Persona p ON c.id_persona = p.id_persona
	            ORDER BY c.id_cliente DESC
	        """;
	        return jdbcTemplate.query(sql, new ClienteExtractor());
	    }


	    public int contarClientesActivos() {
	        String sql = "SELECT COUNT(*) FROM Cliente WHERE activo = 1";
	        return jdbcTemplate.queryForObject(sql, Integer.class);
	    }

	    public int contarClientesInactivos() {
	        String sql = "SELECT COUNT(*) FROM Cliente WHERE activo = 0";
	        return jdbcTemplate.queryForObject(sql, Integer.class);
	    }
}// End of class [ClienteData].
