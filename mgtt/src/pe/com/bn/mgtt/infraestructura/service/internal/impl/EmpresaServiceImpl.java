package pe.com.bn.mgtt.infraestructura.service.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.mgtt.infraestructura.exception.InternalServiceException;
import pe.com.bn.mgtt.infraestructura.service.internal.EmpresaService;
import pe.com.bn.mgtt.persistence.dto.internal.Empresa;
import pe.com.bn.mgtt.persistence.mapper.internal.EmpresaMapper;

 
@Service
public class EmpresaServiceImpl implements EmpresaService {

	private @Autowired
	EmpresaMapper empresaMapper;

	@Override
	public Empresa buscarEmpresaPorRUC(String ruc) {
		try {
			return empresaMapper.buscarEmpresaPorRUC(ruc);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Empresa buscarEmpresaAfiliada(String ruc) {
		try {
			return empresaMapper.buscarEmpresaAfiliada(ruc);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
