package com.bulkgym.data;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.bulkgym.domain.MedidaCorporal;

public class MedidaCorporalExtractor {

    public MedidaCorporal extraerMedida(ResultSet rs) throws SQLException {
        MedidaCorporal m = new MedidaCorporal();
        m.setIdMedidaCorporal(rs.getInt("idMedidaCorporal"));
        m.setTipoMedida(rs.getString("tipoMedida"));
        m.setUnidad(rs.getString("unidad"));
        return m;
    }
}

