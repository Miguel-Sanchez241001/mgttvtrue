package pe.com.bn.mgtt.infraestructura.service.external.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.mgtt.infraestructura.exception.ExternalServiceBnTablasException;
import pe.com.bn.mgtt.infraestructura.service.external.UbigeoService;
import pe.com.bn.mgtt.persistence.dto.external.Ubigeo;
import pe.com.bn.mgtt.persistence.mapper.external.UbigeoMapper;

 
@Service
public class UbigeoServiceImpl implements UbigeoService {

	private @Autowired
	UbigeoMapper ubigeoMapper;

	@Override
	public List<Ubigeo> buscarDepartamentos() {
		try {
			return ubigeoMapper.buscarDepartamentos();
		} catch (Exception ex) {
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Ubigeo> buscarProvinciasPorDepartamento(String codDepartamento) {
		try {
			return ubigeoMapper
					.buscarProvinciasPorDepartamento(codDepartamento);
		} catch (Exception ex) {
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Ubigeo> buscarDistritosPorProvincia(String codDepartamento,
			String codProvincia) {
		try {
			return ubigeoMapper.buscarDistritosPorProvincia(codDepartamento,
					codProvincia);
		} catch (Exception ex) {
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

	@Override
	public Ubigeo buscarLocalidad(String ubigeo, int tipo) {
		try {
			if (tipo == 1) {
				return ubigeoMapper.buscarDepartamento(ubigeo);
			} else if (tipo == 2) {
				return ubigeoMapper.buscarProvincia(ubigeo);
			} else if (tipo == 3) {
				return ubigeoMapper.buscarDistrito(ubigeo);
			}
			return null;
		} catch (Exception ex) {
			throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
		}
	}

}
