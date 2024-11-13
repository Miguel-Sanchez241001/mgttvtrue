package pe.com.bn.mgtt.infraestructura.service.external;

import java.util.List;

import pe.com.bn.mgtt.persistence.dto.external.Ubigeo;

 
public interface UbigeoService {

	public List<Ubigeo> buscarDepartamentos();

	public List<Ubigeo> buscarProvinciasPorDepartamento(String codDepartamento);

	public List<Ubigeo> buscarDistritosPorProvincia(String codDepartamento,
			String codProvincia);
	
	public Ubigeo buscarLocalidad(String ubigeo,int tipo);
}
