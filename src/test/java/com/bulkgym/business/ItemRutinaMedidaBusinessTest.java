package com.bulkgym.business;

import com.bulkgym.domain.ItemRutinaMedida;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRutinaMedidaBusinessTest {

    @Test
    public void testInsertarItemRM_Simulada() {
        ItemRutinaMedidaBusiness business = new ItemRutinaMedidaBusiness();

        business.insertarItemRM(1, 101);
        business.insertarItemRM(2, 102);

        List<ItemRutinaMedida> items = business.obtenerItems();

        assertEquals(2, items.size());
        assertEquals(101, items.get(0).getMedidaCorporal().getIdMedidaCorporal());
        assertEquals(102, items.get(1).getMedidaCorporal().getIdMedidaCorporal());
        assertNotNull(items.get(0).getFechaMedicion());
    }
}
