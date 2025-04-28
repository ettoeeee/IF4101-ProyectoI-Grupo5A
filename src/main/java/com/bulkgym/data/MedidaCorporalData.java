package com.bulkgym.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import com.bulkgym.domain.MedidaCorporal;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MedidaCorporalData {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional(readOnly = true)
    public MedidaCorporal findById(int codMedida) {
        String sql = """
            SELECT codMedida, nombreMedida, unidadMedida, imagen 
            FROM MedidaCorporal 
            WHERE codMedida = ?
        """;

        List<MedidaCorporal> lista = jdbcTemplate.query(sql, new MedidaCorporalExtractor(), codMedida);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Transactional
    public void guardar(MedidaCorporal medida) {
        String sql = """
            INSERT INTO MedidaCorporal (nombreMedida, unidadMedida, imagen)
            VALUES (?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, medida.getNombreMedida());
            ps.setString(2, medida.getUnidadMedida());
            ps.setString(3, medida.getImagen());
            return ps;
        }, keyHolder);

        Number idGenerado = keyHolder.getKey();
        if (idGenerado != null) {
            medida.setCodMedida(idGenerado.intValue());
        }
    }

    
    @Transactional(readOnly = true)
    public List<MedidaCorporal> findAll() {
        String sql = """
            SELECT codMedida, nombreMedida, unidadMedida, imagen 
            FROM MedidaCorporal
        """;

        return jdbcTemplate.query(sql, new MedidaCorporalExtractor());
    }

    @Transactional
    public boolean delete(int codMedida) {
        try {
            int filasAfectadas = jdbcTemplate.update(
                "DELETE FROM MedidaCorporal WHERE codMedida = ?", 
                codMedida
            );
            return filasAfectadas > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
    
    @Transactional
    public boolean update(MedidaCorporal medida) {
        String sql = """
            UPDATE MedidaCorporal
            SET nombreMedida = ?, unidadMedida = ?, imagen = ?
            WHERE codMedida = ?
        """;

        int result = jdbcTemplate.update(sql,
            medida.getNombreMedida(),
            medida.getUnidadMedida(),
            medida.getImagen(),
            medida.getCodMedida()
        );

        return result > 0;
    }
    
    public int contarMedidas() {
        String sql = "SELECT COUNT(*) FROM MedidaCorporal";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<MedidaCorporal> obtenerMedidasRecientes() {
        String sql = """
            SELECT TOP 5 *
            FROM MedidaCorporal
            ORDER BY codMedida DESC
        """;
        return jdbcTemplate.query(sql, new MedidaCorporalExtractor());
    }
}