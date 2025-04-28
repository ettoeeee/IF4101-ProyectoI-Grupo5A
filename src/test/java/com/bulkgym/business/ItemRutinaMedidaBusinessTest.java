package com.bulkgym.business;

import com.bulkgym.domain.ItemRutinaMedida;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRutinaMedidaBusinessTest {

    @Test
    public void testInsertarItemRM_Simulada() {
    	 ItemRutinaMedidaBusiness business = new ItemRutinaMedidaBusiness();

         // Insertar, a traves del llamado del metodo en la clase ItemRutinaMedidaBusiness, 3 registros ficticios
         business.insertarItemRM(1, 201); // Altura
         business.insertarItemRM(1, 202); // Peso
         business.insertarItemRM(1, 203); // Cintura

         // Acceder y modificar manualmente los registros 
         List<ItemRutinaMedida> items = business.obtenerItems();

         items.get(0).getMedidaCorporal().setNombreMedida("Altura");
         items.get(0).getMedidaCorporal().setUnidadMedida("cm");
         items.get(0).setValorMedida(175);

         items.get(1).getMedidaCorporal().setNombreMedida("Peso");
         items.get(1).getMedidaCorporal().setUnidadMedida("kg");
         items.get(1).setValorMedida(70);

         items.get(2).getMedidaCorporal().setNombreMedida("Cintura");
         items.get(2).getMedidaCorporal().setUnidadMedida("cm");
         items.get(2).setValorMedida(85);

         // Verificar mediante tests
         assertEquals(3, items.size()); //Tamaño de la lista de registros

         //Recordar que en la lista se comienza por el indice 0, por lo que se va a analizar los test desde esa posicion
         
         assertAll("Verificar datos realistas",
                 () -> {
                	 
                     assertEquals("Altura", items.get(0).getMedidaCorporal().getNombreMedida());
                     assertEquals("cm", items.get(0).getMedidaCorporal().getUnidadMedida());
                     assertEquals(175, items.get(0).getValorMedida());
                 },
                 () -> {
                     assertEquals("Peso", items.get(1).getMedidaCorporal().getNombreMedida());
                     assertEquals("kg", items.get(1).getMedidaCorporal().getUnidadMedida());
                     assertEquals(70, items.get(1).getValorMedida());
                 },
                 () -> {
                     assertEquals("Cintura", items.get(2).getMedidaCorporal().getNombreMedida());
                     assertEquals("cm", items.get(2).getMedidaCorporal().getUnidadMedida());
                     assertEquals(85, items.get(2).getValorMedida());
                 }
         );
     }
}
