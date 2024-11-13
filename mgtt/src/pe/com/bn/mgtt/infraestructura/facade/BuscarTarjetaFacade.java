package pe.com.bn.mgtt.infraestructura.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaOpcion;
import pe.com.bn.mgtt.persistence.dto.internal.bloqueo.MotivosBloqueo;
import pe.com.bn.mgtt.persistence.mapper.internal.LogMapper;
import pe.com.bn.mgtt.persistence.mapper.internal.PermisosMapper;
import pe.com.bn.mgtt.persistence.mapper.internal.TarjetaMapper;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.excepciones.InternalExcepcion;

@Component
@Getter
@Setter
public class BuscarTarjetaFacade {

	@Autowired
	private PermisosMapper permisosMapper;

	
	@Autowired
	private TarjetaMapper tarjetaMapper;
	
	@Autowired
	private LogMapper logMapper;
	
	private static final Logger logger = LogManager.getLogger(BuscarTarjetaFacade.class);

	public List<MotivosBloqueo> obtenerListaBloqueosCuenta(String estadoCuenta) throws InternalExcepcion {
		try {
			Long rol = Long.parseLong(UsefulWebApplication.obtenerUsuario().getCodigoArea());
			return permisosMapper.buscarMotivosBloqueo(rol);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalExcepcion(ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
		}

	}

	public List<MotivosBloqueo> obtenerListaBloqueosTarjeta(String estadoTarjeta) throws InternalExcepcion {

		try {
			Long rol = Long.parseLong(UsefulWebApplication.obtenerUsuario().getCodigoArea());
			List<MotivosBloqueo> permisosFiltrados = permisosMapper.buscarMotivosBloqueo(rol)
				    .stream()
				    .filter(permiso -> estadoTarjeta.equals(permiso.getBloqueoTipo()))
				    .collect(Collectors.toList());
			return permisosFiltrados;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalExcepcion(ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
		}

	}
	public List<TarjetaOpcion> obtenerListaTarjetasOpcion(String tipoDocumento,String numDocumento ) throws InternalExcepcion {

		try {
 			return tarjetaMapper.cantidadTarjetasCliente(tipoDocumento, numDocumento);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalExcepcion(ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
		}

	}
	
	public List<TarjetaOpcion> obtenerListaTarjetasOpcionTar(String numtar ) throws InternalExcepcion {

		try {
 			return tarjetaMapper.cantidadTarjetasClientePorTar(numtar);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalExcepcion(ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
		}

	}
	
}
