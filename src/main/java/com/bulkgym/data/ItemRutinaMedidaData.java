package com.bulkgym.data;

import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ItemRutinaMedidaData {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final String SQL_POR_RUTINA = """
            SELECT irm.idItem,
                   irm.valorMedida,
                   irm.codMedida,
                   irm.id_Rutina,
                   irm.codMedida,
                   mc.imagen,
                   mc.codMedida,
                   mc.nombreMedida,
                   mc.unidadMedida
              FROM ItemRutinaMedida irm
              JOIN MedidaCorporal mc ON irm.codMedida = mc.codMedida
             WHERE irm.id_Rutina = ?
        """;

    @Transactional(readOnly = true)
    public List<ItemRutinaMedida> findByRutinaId(int idRutina) {
       return jdbcTemplate.query(SQL_POR_RUTINA, new ItemRutinaMedidaExtractor(), idRutina);
    }

    
    @Transactional
    public void eliminarPorRutina(int idRutina) {
        String sql = "DELETE FROM ItemRutinaMedida WHERE id_Rutina = ?";
        jdbcTemplate.update(sql, idRutina);
    }



    @Transactional(readOnly = true)
    public List<ItemRutinaMedida> findAll() {
        String sql = """
            SELECT i.idItem, i.valorMedida, i.codMedida, i.id_Rutina,
                   m.nombreMedida, m.unidadMedida, m.imagen
            FROM ItemRutinaMedida i
            JOIN MedidaCorporal m ON i.codMedida = m.codMedida
        """;
        return jdbcTemplate.query(sql, new ItemRutinaMedidaExtractor());
    }

    @Transactional(readOnly = true)
    public ItemRutinaMedida findById(int idItem) {
        String sql = """
            SELECT i.idItem, i.valorMedida, i.codMedida, i.id_Rutina,
                   m.nombreMedida, m.unidadMedida, m.imagen
            FROM ItemRutinaMedida i
            JOIN MedidaCorporal m ON i.codMedida = m.codMedida
            WHERE i.idItem = ?
        """;
        List<ItemRutinaMedida> lista = jdbcTemplate.query(sql, new ItemRutinaMedidaExtractor(), idItem);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Transactional
    public void guardar(ItemRutinaMedida item) {
        String sql = """
            INSERT INTO ItemRutinaMedida (valorMedida, codMedida, id_Rutina)
            VALUES (?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, item.getValorMedida());
            ps.setInt(2, item.getMedidaCorporal().getCodMedida());
            ps.setInt(3, item.getRutina().getIdRutina()); // ✅ accede a idRutina correctamente
            return ps;
        }, keyHolder);

        Number idGenerado = keyHolder.getKey();
        if (idGenerado != null) {
            item.setIdItemRutinaMedida(idGenerado.intValue());
        }
    }

    @Transactional
    public boolean update(ItemRutinaMedida item) {
        String sql = """
            UPDATE ItemRutinaMedida
            SET valorMedida = ?, codMedida = ?, id_Rutina = ?
            WHERE idItem = ?
        """;
        int rows = jdbcTemplate.update(sql,
                item.getValorMedida(),
                item.getMedidaCorporal().getCodMedida(),
                item.getRutina().getIdRutina(), // ✅ correcto uso de objeto
                item.getIdItemRutinaMedida());
        return rows > 0;
    }

    @Transactional
    public boolean delete(int idItem) {
        String sql = "DELETE FROM ItemRutinaMedida WHERE idItem = ?";
        return jdbcTemplate.update(sql, idItem) > 0;
    }
}
