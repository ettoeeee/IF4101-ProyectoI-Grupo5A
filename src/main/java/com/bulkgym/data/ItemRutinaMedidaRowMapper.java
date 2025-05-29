package com.bulkgym.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;

import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;

public class ItemRutinaMedidaRowMapper implements RowMapper {

    public ItemRutinaMedida mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Crear objeto ItemRutinaMedida
        ItemRutinaMedida item = new ItemRutinaMedida();
        item.setIdItemRutinaMedida(rs.getInt("id_item_rutina_medida"));
        item.setValorMedida(rs.getDouble("valor_medida"));
    

        // Crear la plantilla de medida (MedidaCorporal)
        MedidaCorporal plantilla = new MedidaCorporal();
        plantilla.setCodMedida(rs.getInt("id_media_corporal"));
        plantilla.setNombreMedida(rs.getString("tipo_medida"));
        plantilla.setUnidadMedida(rs.getString("unidad"));

        // Asignar plantilla al item
        item.setMedidaCorporal(plantilla);

        return item;
    }// End of method [mapRow].

	@Override
	public int[] getRowsForPaths(TreePath[] path) {
		// TODO Auto-generated method stub
		return null;
	}// End of method [getRowsForPaths].
}// End of class [ItemRutinaMedidaRowMapper].
