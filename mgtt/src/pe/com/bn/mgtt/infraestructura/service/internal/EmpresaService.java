package pe.com.bn.mgtt.infraestructura.service.internal;

import pe.com.bn.mgtt.persistence.dto.internal.Empresa;

public interface EmpresaService {

	public Empresa buscarEmpresaPorRUC(String ruc);
	
	public Empresa buscarEmpresaAfiliada(String ruc);
}
