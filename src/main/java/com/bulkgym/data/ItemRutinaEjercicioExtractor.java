package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.ItemRutinaEjercicio;

public class ItemRutinaEjercicioExtractor implements ResultSetExtractor<List<ItemRutinaEjercicio>> {

    @Override
    public List<ItemRutinaEjercicio> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, ItemRutinaEjercicio> map = new HashMap<>();
        ItemRutinaEjercicio item = null;

        while (rs.next()) {
            int idRutina = rs.getInt("id_rutina");
            int idEjercicio = rs.getInt("id_ejercicio");
            // La clave compuesta la armamos con un String (puedes cambiar si prefieres otro tipo)
            String key = idRutina + "-" + idEjercicio;
            item = map.get(key);

            if (item == null) {
                item = new ItemRutinaEjercicio();
                item.setIdRutina(idRutina);
                item.setIdEjercicio(idEjercicio);
                item.setSeriesEjercicio(rs.getObject("series_ejercicio") != null ? rs.getInt("series_ejercicio") : null);
                item.setRepeticionesEjercicio(rs.getObject("repeticiones_ejercicio") != null ? rs.getInt("repeticiones_ejercicio") : null);
                item.setEquipoEjercicio(rs.getString("equipo_ejercicio"));

                map.put(key, item);
            }
        }
        return new ArrayList<>(map.values());
    }
}
