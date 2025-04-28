package com.bulkgym.business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.bulkgym.domain.MedidaCorporal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedidaCorporalBusinessTest {

    @Autowired
    private MedidaCorporalBusiness medidaCorporalBusiness;

    @Test
    public void testGuardarYObtener() {
    	
    	/*
    	// Lista de nombres de medidas corporales que se van a insertar
        List<String> nombresMedidas = Arrays.asList(
            "Cintura", "Pecho", "Bíceps", "Pierna", "Cuello", 
            "Hombros", "Gemelos", "Antebrazo", "Muñeca", "Tobillo"
        );

        // Insertar cada medida con unidad "cm"
        for (String nombre : nombresMedidas) {
            MedidaCorporal medida = new MedidaCorporal();
            medida.setTipoMedida(nombre);
            medida.setUnidad("cm");
            medidaCorporalBusiness.guardar(medida);
        }

        // Obtener todas las medidas de la base de datos
        List<MedidaCorporal> medidasEnBD = medidaCorporalBusiness.obtenerTodas();

        // Validar que cada medida insertada exista en la BD
        for (String nombre : nombresMedidas) {
            assertTrue(
                medidasEnBD.stream().anyMatch(m -> nombre.equals(m.getTipoMedida())),
                "No se encontró la medida: " + nombre
            );
        }
    */
    }

    @Test
    public void testEliminarMedidasPorNombre() {
    	/*
        // Nombres a eliminar
    	List<String> nombresAEliminar = List.of("Cintura", "Pecho","Cuello");

        // Obtener todas las medidas
        List<MedidaCorporal> medidas = medidaCorporalBusiness.obtenerTodas();

        // Eliminar las que coinciden con los nombres
        for (MedidaCorporal medida : medidas) {
            if (nombresAEliminar.contains(medida.getTipoMedida())) {
                medidaCorporalBusiness.eliminar(medida.getIdMedidaCorporal());
            }
        }

        // Volver a consultar para verificar que ya no estén
        List<MedidaCorporal> medidasActualizadas = medidaCorporalBusiness.obtenerTodas();

        // Comprobar que fueron eliminadas
        for (String nombre : nombresAEliminar) {
            boolean existe = medidasActualizadas.stream()
                .anyMatch(m -> nombre.equals(m.getTipoMedida()));
            assertFalse(existe, "La medida '" + nombre + "' no fue eliminada correctamente.");
        }
        
        */
    }
    
    @Test
    public void testActualizarTodasLasMedidas() {
    	
    	/*
    	// Obtener las medidas actuales
        List<MedidaCorporal> medidas = medidaCorporalBusiness.obtenerTodas();

        for (MedidaCorporal medida : medidas) {
            if (medida.getTipoMedida().equalsIgnoreCase("Cintura Baja")) {
                // Solo cambiar el nombre
                medida.setTipoMedida("Cintura");
               
            } else if (medida.getTipoMedida().equalsIgnoreCase("Pecho")) {
                // Solo cambiar la unidad
                medida.setUnidad("cm");
              
            } else if (medida.getTipoMedida().equalsIgnoreCase("Bíceps Flexionado")) {
                // Cambiar ambos
                medida.setTipoMedida("Bíceps");
                medida.setUnidad("cm");
            }
            medidaCorporalBusiness.actualizar(medida);
        }

        MedidaCorporal cintura = medidaCorporalBusiness.obtenerPorId(6);
        assertEquals("Cintura", cintura.getTipoMedida());
        
        MedidaCorporal pecho = medidaCorporalBusiness.obtenerPorId(7);
        assertEquals("cm", pecho.getUnidad());
        
        MedidaCorporal biceps = medidaCorporalBusiness.obtenerPorId(8);
        assertEquals("Bíceps", biceps.getTipoMedida());
        assertEquals("cm", biceps.getUnidad());
        
        */
    }
    
    @Test
    public void testFindById() {
        // Verificar que existe la medida con ID 6 (Cintura)
        MedidaCorporal medidaCintura = medidaCorporalBusiness.obtenerPorId(6);
        assertNotNull(medidaCintura);
        assertEquals("Cintura", medidaCintura.getNombreMedida());
        assertEquals("cm", medidaCintura.getUnidadMedida());

        // Verificar que existe la medida con ID 7 (Pecho)
        MedidaCorporal medidaPecho = medidaCorporalBusiness.obtenerPorId(7);
        assertNotNull(medidaPecho);
        assertEquals("Pecho", medidaPecho.getNombreMedida());
        assertEquals("cm", medidaPecho.getUnidadMedida());

        // Verificar que existe la medida con ID 8 (Bíceps)
        MedidaCorporal medidaBíceps = medidaCorporalBusiness.obtenerPorId(8);
        assertNotNull(medidaBíceps);
        assertEquals("Bíceps", medidaBíceps.getNombreMedida());
        assertEquals("cm", medidaBíceps.getUnidadMedida());
        
        // Verificar que existe la medida con ID 15 (Cintura)
        MedidaCorporal medidaTobillo = medidaCorporalBusiness.obtenerPorId(15);
        assertNotNull(medidaTobillo);
        assertEquals("Tobillo", medidaTobillo.getNombreMedida());
        assertEquals("cm", medidaTobillo.getUnidadMedida());

        // ID que NO exista
        MedidaCorporal medidaInexistente = medidaCorporalBusiness.obtenerPorId(1000);
        assertNull(medidaInexistente);
    }
    
}
