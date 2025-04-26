package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bulkgym.domain.MedidaCorporal;

public class MedidaCorporalExtractor implements ResultSetExtractor<List<MedidaCorporal>> {

    @Override
    public List<MedidaCorporal> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<MedidaCorporal> medidas = new ArrayList<>();

        while (rs.next()) {
            MedidaCorporal medida = new MedidaCorporal();
            medida.setIdMedidaCorporal(rs.getInt("codMedida"));
            medida.setTipoMedida(rs.getString("nombreMedida"));
            medida.setUnidad(rs.getString("unidadMedida"));
            medidas.add(medida);
        }

        return medidas;
    }
}
