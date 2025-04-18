package com.bulkgym.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.ItemRutinaMedida;

@Repository
public class ItemRutinaMedidaData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<ItemRutinaMedida> obtenerMedidasPorRutina(int idRutina) {
        String sql = """
            SELECT irm.id_item_rutina_medida,
                   irm.valor_medida,
                   irm.fecha_medicion,
                   mc.id_media_corporal,
                   mc.tipo_medida,
                   mc.unidad
            FROM ItemRutinaMedida irm
            JOIN MedidaCorporal mc ON irm.id_medida_corporal = mc.id_media_corporal
            WHERE irm.id_rutina = ?
        """;

        return jdbcTemplate.query(sql, new ItemRutinaMedidaExtractor(), idRutina);
    }
}
