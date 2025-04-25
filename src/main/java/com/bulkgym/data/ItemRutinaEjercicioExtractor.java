package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.Ejercicio;
import com.bulkgym.domain.ItemRutinaEjercicio;

public class ItemRutinaEjercicioExtractor implements ResultSetExtractor<List<ItemRutinaEjercicio>> {

    @Override
    public List<ItemRutinaEjercicio> extractData(ResultSet rs) throws SQLException {
        Map<Integer, ItemRutinaEjercicio> map = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt("id_item_rutina_ejercicio");

            ItemRutinaEjercicio item = map.get(id);

            if (item == null) {
                item = new ItemRutinaEjercicio();
                item.setIdItemRutinaEjercicio(id);
                item.setSeriesEjercicio(rs.getInt("series_ejercicio"));
                item.setRepeticionesEjercicio(rs.getInt("repeticiones_ejercicio"));

                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setIdEjercicio(rs.getInt("id_ejercicio"));
                ejercicio.setNombreEjercicio(rs.getString("nombre_ejercicio"));
                
                map.put(id, item);
            }
        }

        return new ArrayList<>(map.values());
    }
}
