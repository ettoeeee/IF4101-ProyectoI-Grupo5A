package com.bulkgym.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulkgym.data.EmpleadoData;
import com.bulkgym.domain.Empleado;
import com.bulkgym.dto.EmpleadoDTO;

@Service
public class EmpleadoBusiness {
	
	@Autowired
	private EmpleadoData empleadoData;
	
	public List<Empleado> findAll() {
		return empleadoData.findAll();
	}
	
	public Empleado findById(int idEmpleado) {
		return empleadoData.findById(idEmpleado);
	}
	
	public void saveEmpleado(Empleado empleado) {
		empleadoData.saveEmpleado(empleado);
	}
	
	public boolean updateEmpleado(EmpleadoDTO empleado) {
		return empleadoData.updateEmpleado(empleado);
	}
	
	public boolean deleteEmpleado(int idEmpleado) {
		return empleadoData.deleteEmpleado(idEmpleado);
	}
}
