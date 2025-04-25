package com.bulkgym.data;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bulkgym.domain.MedidaCorporal;

@Repository
public class MedidaCorporalData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MedidaCorporal> findAll() {
        String sql = "SELECT codMedida, nombreMedida, unidadMedida FROM MedidaCorporal";
        return jdbcTemplate.query(sql, new MedidaCorporalExtractor());
    }
    @Transactional
    public void guardar(MedidaCorporal medida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("InsertMedidaCorporal")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                    new SqlParameter("nombreMedida", Types.VARCHAR),
                    new SqlParameter("unidadMedida", Types.VARCHAR),
                    new SqlOutParameter("codMedida", Types.INTEGER)
                );

        Map<String, Object> params = Map.of(
            "nombreMedida", medida.getTipoMedida(),
            "unidadMedida", medida.getUnidad()
        );

        Map<String, Object> result = jdbcCall.execute(params);

        medida.setIdMedidaCorporal((Integer) result.get("codMedida"));
    }

    public void update(MedidaCorporal medida) {
        String sql = "UPDATE MedidaCorporal SET nombreMedida = ?, unidadMedida = ? WHERE codMedida = ?";
        jdbcTemplate.update(sql, medida.getTipoMedida(), medida.getUnidad(), medida.getIdMedidaCorporal());
    }

    public void delete(int idMedidaCorporal) {
        String sql = "DELETE FROM MedidaCorporal WHERE codMedida = ?";
        jdbcTemplate.update(sql, idMedidaCorporal);
    }

    public MedidaCorporal findById(int id) {
        String sql = "SELECT codMedida, nombreMedida, unidadMedida FROM MedidaCorporal WHERE codMedida = ?";
        List<MedidaCorporal> medidas = jdbcTemplate.query(sql, new MedidaCorporalExtractor(), id);
        return medidas.isEmpty() ? null : medidas.get(0);
    }
}
