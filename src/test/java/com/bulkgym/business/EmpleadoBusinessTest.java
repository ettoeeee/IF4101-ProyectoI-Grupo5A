package com.bulkgym.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.bulkgym.data.EmpleadoData;
import com.bulkgym.domain.Empleado;
import com.bulkgym.dto.EmpleadoDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmpleadoBusinessTest {

    @Mock
    private EmpleadoData empleadoData;

    @InjectMocks
    private EmpleadoBusiness empleadoBusiness;

    private Empleado e1, e2;
    private EmpleadoDTO dto;

    @BeforeEach
    void setUp() {
        e1 = new Empleado();
        e1.setIdEmpleado(1);
        e1.setIdPersona(10);
        e1.setRolEmpleado("Instructor");
        e1.setNombre("Ana");
        e1.setApellidos("Pérez");

        e2 = new Empleado();
        e2.setIdEmpleado(2);
        e2.setIdPersona(20);
        e2.setRolEmpleado("Conserje");
        e2.setNombre("Luis");
        e2.setApellidos("García");

        dto = new EmpleadoDTO();
        dto.setIdEmpleado(1);
        dto.setIdPersona(10);
        dto.setRolEmpleado("Supervisor");
        dto.setNombre("Ana");
        dto.setApellidos("Pérez");
    }

    @Test
    void findAll_delegaEnData_yDevuelveLista() {
        List<Empleado> mockList = Arrays.asList(e1, e2);
        when(empleadoData.findAll()).thenReturn(mockList);

        List<Empleado> result = empleadoBusiness.findAll();

        assertSame(mockList, result);
        verify(empleadoData).findAll();
    }

    @Test
    void findById_delegaEnData_yDevuelveEmpleado() {
        when(empleadoData.findById(1)).thenReturn(e1);

        Empleado result = empleadoBusiness.findById(1);

        assertSame(e1, result);
        verify(empleadoData).findById(1);
    }

    @Test
    void saveEmpleado_delegaEnData() {
        // Simplemente verifica que llame al método correcto.
        empleadoBusiness.saveEmpleado(e1);
        verify(empleadoData).saveEmpleado(e1);
    }

    @Test
    void updateEmpleado_delegaEnData_yPropagaElBooleano() {
        when(empleadoData.updateEmpleado(dto)).thenReturn(true, false);

        assertTrue(empleadoBusiness.updateEmpleado(dto));
        assertFalse(empleadoBusiness.updateEmpleado(dto));

        verify(empleadoData, times(2)).updateEmpleado(dto);
    }

    @Test
    void deleteEmpleado_delegaEnData_yPropagaElBooleano() {
        when(empleadoData.deleteEmpleado(1)).thenReturn(true, false);

        assertTrue(empleadoBusiness.deleteEmpleado(1));
        assertFalse(empleadoBusiness.deleteEmpleado(1));

        verify(empleadoData, times(2)).deleteEmpleado(1);
    }

    @Test
    void saveAndUpdateArguments_correctos() {
        // Captura argumentos al llamar a saveEmpleado
        empleadoBusiness.saveEmpleado(e1);
        ArgumentCaptor<Empleado> capt = ArgumentCaptor.forClass(Empleado.class);
        verify(empleadoData).saveEmpleado(capt.capture());
        assertEquals("Ana", capt.getValue().getNombre());
        assertEquals("Instructor", capt.getValue().getRolEmpleado());

        // Captura al updateEmpleado
        empleadoBusiness.updateEmpleado(dto);
        ArgumentCaptor<EmpleadoDTO> captDto = ArgumentCaptor.forClass(EmpleadoDTO.class);
        verify(empleadoData).updateEmpleado(captDto.capture());
        assertEquals(1, captDto.getValue().getIdEmpleado());
        assertEquals("Supervisor", captDto.getValue().getRolEmpleado());
    }
}
