package pe.com.bn.mgtt.infraestructura.service.external;

import java.util.List;

import pe.com.bn.mgtt.persistence.dto.external.Agencia;

public interface AgenciaService {

	public List<Agencia> buscarAgenciasPorUbigeo(String codDepartamento,
			String codProvincia, String codDistrito);

	public Agencia buscarAgenciaPorCodAgencia(String agencia);
}
