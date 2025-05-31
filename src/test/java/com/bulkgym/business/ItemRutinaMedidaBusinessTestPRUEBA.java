package com.bulkgym.business;



import com.bulkgym.business.ItemRutinaMedidaBusiness;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;
import com.bulkgym.domain.Rutina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRutinaMedidaBusinessTestPRUEBA {

    @Autowired
    private ItemRutinaMedidaBusiness business;

    private ItemRutinaMedida crearItem(double valor, int codMedida, int idRutina) {
        ItemRutinaMedida item = new ItemRutinaMedida();

        MedidaCorporal medida = new MedidaCorporal();
        medida.setCodMedida(codMedida);
        item.setMedidaCorporal(medida);

        Rutina rutina = new Rutina();
        rutina.setIdRutina(idRutina);
        item.setRutina(rutina);

        item.setValorMedida(valor);
        return item;
    }

    @Test
    void testInsertarVariosItems() {
        List<ItemRutinaMedida> items = new ArrayList<>();
        items.add(crearItem(80.0, 6, 5));
        items.add(crearItem(82.5, 7, 5));
        items.add(crearItem(90.2, 8, 5));
        items.add(crearItem(70.0, 9, 5));
        items.add(crearItem(88.8, 10, 5));

        for (ItemRutinaMedida item : items) {
            business.guardar(item);
            assertTrue(item.getIdItemRutinaMedida() > 0, "Debe generarse un ID");
        }
    }

    @Test
    void testBuscarMultiplesItemsPorId() {
        List<ItemRutinaMedida> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemRutinaMedida item = crearItem(60 + i * 2, 6 + i, 5);
            business.guardar(item);
            items.add(item);
        }

        for (ItemRutinaMedida item : items) {
            ItemRutinaMedida encontrado = business.obtenerPorId(item.getIdItemRutinaMedida());
            assertNotNull(encontrado);
            assertEquals(item.getValorMedida(), encontrado.getValorMedida(), 0.01);
        }
    }

    @Test
    void testActualizarVariosItems() {
        List<ItemRutinaMedida> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemRutinaMedida item = crearItem(65 + i, 6 + i, 5);
            business.guardar(item);
            items.add(item);
        }

        for (ItemRutinaMedida item : items) {
            item.setValorMedida(item.getValorMedida() + 10);
            business.actualizar(item);
            ItemRutinaMedida actualizado = business.obtenerPorId(item.getIdItemRutinaMedida());
            assertEquals(item.getValorMedida(), actualizado.getValorMedida(), 0.01);
        }
    }

    @Test
    void testEliminarVariosItems() {
        List<ItemRutinaMedida> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemRutinaMedida item = crearItem(72 + i, 6 + i, 5);
            business.guardar(item);
            items.add(item);
        }

        for (ItemRutinaMedida item : items) {
            business.eliminar(item.getIdItemRutinaMedida());
            ItemRutinaMedida eliminado = business.obtenerPorId(item.getIdItemRutinaMedida());
            assertNull(eliminado, "Debe haberse eliminado correctamente");
        }
    }

    @Test
    void testListarYVerificarMinimoCinco() {
        // Insertar 5 registros
        for (int i = 0; i < 5; i++) {
            ItemRutinaMedida item = crearItem(75 + i, 6 + i, 5);
            business.guardar(item);
        }

        List<ItemRutinaMedida> lista = business.obtenerTodas();
        assertNotNull(lista);
        assertTrue(lista.size() >= 5, "Debe haber al menos 5 registros");
    }
}
