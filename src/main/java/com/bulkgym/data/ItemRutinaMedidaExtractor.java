package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;

public class ItemRutinaMedidaExtractor implements ResultSetExtractor<List<ItemRutinaMedida>> {

    private final MedidaCorporalRowMapper medidaMapper = new MedidaCorporalRowMapper();

    @Override
    public List<ItemRutinaMedida> extractData(ResultSet rs) throws SQLException {
        Map<Integer, ItemRutinaMedida> map = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt("id_item_rutina_medida");

            ItemRutinaMedida item = map.get(id);

            if (item == null) {
                item = new ItemRutinaMedida();
                item.setIdItemRutinaMedida(id);
                item.setValorMedida(rs.getDouble("valor_medida"));
                item.setFechaMedicion(rs.getDate("fecha_medicion").toLocalDate());

                // Usamos el row mapper para construir la plantilla
                MedidaCorporal medida = medidaMapper.mapRow(rs, rs.getRow());
                item.setMedidaCorporal(medida);

                map.put(id, item);
            }
        }

        return new ArrayList<>(map.values());
    }
}

