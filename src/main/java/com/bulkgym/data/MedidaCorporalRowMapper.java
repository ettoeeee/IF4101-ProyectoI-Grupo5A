package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.bulkgym.domain.MedidaCorporal;

public class MedidaCorporalRowMapper implements RowMapper<MedidaCorporal> {

    @Override
    public MedidaCorporal mapRow(ResultSet rs, int rowNum) throws SQLException {
        MedidaCorporal medida = new MedidaCorporal();

        medida.setCodMedida(rs.getInt("id_media_corporal"));
        medida.setNombreMedida(rs.getString("tipo_medida"));
        medida.setUnidadMedida(rs.getString("unidad"));

        return medida;
    }
}

