package com.bulkgym.data;

import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRutinaMedidaExtractor implements ResultSetExtractor<List<ItemRutinaMedida>> {

    @Override
    public List<ItemRutinaMedida> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ItemRutinaMedida> items = new ArrayList<>();
        while (rs.next()) {
            ItemRutinaMedida item = new ItemRutinaMedida();
            item.setIdItemRutinaMedida(rs.getInt("idItem"));
            item.setValorMedida(rs.getDouble("valorMedida"));
            
            Rutina rutina= new Rutina();
            rutina.setIdRutina(rs.getInt("id_Rutina"));

            MedidaCorporal medida = new MedidaCorporal();
            medida.setCodMedida(rs.getInt("codMedida"));
            medida.setNombreMedida(rs.getString("nombreMedida"));
            medida.setUnidadMedida(rs.getString("unidadMedida"));
            medida.setImagen(rs.getString("imagen"));

            item.setMedidaCorporal(medida);
            items.add(item);
        }
        return items;
    }
}
