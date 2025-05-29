package com.bulkgym.business;

import com.bulkgym.data.ItemRutinaMedidaData;
import com.bulkgym.data.MedidaCorporalData;
import com.bulkgym.domain.ItemRutinaMedida;
import com.bulkgym.domain.MedidaCorporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemRutinaMedidaBusiness {

    @Autowired
    private ItemRutinaMedidaData itemData;

    @Autowired
    private MedidaCorporalData medidaData; 

    public List<ItemRutinaMedida> obtenerTodas() {
        return itemData.findAll();
    }

    public List<MedidaCorporal> obtenerTodasLasMedidas() {
        return medidaData.findAll(); 
    }

    public ItemRutinaMedida obtenerPorId(int id) {
        return itemData.findById(id);
    }

    public void guardar(ItemRutinaMedida item) {
        itemData.guardar(item);
    }

    public void actualizar(ItemRutinaMedida item) {
        itemData.update(item);
    }

    public void eliminar(int id) {
        itemData.delete(id);
    }
}
