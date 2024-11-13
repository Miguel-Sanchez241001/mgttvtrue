package pe.com.bn.mgtt.infraestructura.service.external.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.mgtt.infraestructura.exception.ExternalServiceBnTablasException;
import pe.com.bn.mgtt.infraestructura.service.external.AgenciaService;
import pe.com.bn.mgtt.persistence.dto.external.Agencia;
import pe.com.bn.mgtt.persistence.mapper.external.AgenciaMapper;

 

@Service
public class AgenciaServiceImpl implements AgenciaService {

	private @Autowired
	AgenciaMapper agenciaMapper;

	@Override
	public List<Agencia> buscarAgenciasPorUbigeo(String codDepartamento,
	                                             String codProvincia, String codDistrito) {
	    try {
 	        String agenciaUbigeoReal = agenciaMapper.buscarUbigeoReal(codDepartamento, codProvincia, codDistrito);
	        
 	        if (agenciaUbigeoReal != null && agenciaUbigeoReal.length() == 6) {
 	            String departamento = agenciaUbigeoReal.substring(0, 2);
	            String provincia = agenciaUbigeoReal.substring(2, 4);
	            String distrito = agenciaUbigeoReal.substring(4, 6);
	            
 	            return agenciaMapper.buscarAgenciasPorUbigeo(departamento, provincia, distrito);
	        }
	        
 	        return new ArrayList<>();
	        
	    } catch (Exception ex) {
 	        throw new ExternalServiceBnTablasException(ex.getMessage(), ex);
	    }
	}

	@Override
	public Agencia buscarAgenciaPorCodAgencia(String codAgencia) {
		try {
			List<Agencia> listaAgencia = agenciaMapper
					.buscarAgenciaPorCodAgencia(String.format("%04d",
							Long.valueOf(codAgencia)));
			return listaAgencia == null || listaAgencia.isEmpty() ? new Agencia()
					: listaAgencia.get(0);
		} catch (Exception ex) {
			throw new ExternalServiceBnTablasException(ex.getMessage(),ex);
		}
	}

}
