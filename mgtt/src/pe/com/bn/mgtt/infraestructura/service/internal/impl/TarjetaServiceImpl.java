package pe.com.bn.mgtt.infraestructura.service.internal.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.bn.mgtt.infraestructura.exception.InternalServiceException;
import pe.com.bn.mgtt.infraestructura.service.internal.TarjetaService;
import pe.com.bn.mgtt.persistence.dto.internal.Cliente;
import pe.com.bn.mgtt.persistence.dto.internal.DatosTarjetaCliente;
import pe.com.bn.mgtt.persistence.dto.internal.EstadoTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.SolicitudTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.Tarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaResumen;
import pe.com.bn.mgtt.persistence.mapper.internal.ClienteMapper;
import pe.com.bn.mgtt.persistence.mapper.internal.EmpresaMapper;
import pe.com.bn.mgtt.persistence.mapper.internal.TarjetaMapper;
import pe.com.bn.mgtt.transversal.configuration.security.SecurityContextFacade;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.enums.TipoBusqueda;
import pe.com.bn.mgtt.transversal.util.enums.TipoEstadoTarjeta;
import pe.com.bn.mgtt.transversal.util.enums.TipoTarjetaNegocio;

@Service
public class TarjetaServiceImpl implements TarjetaService {

	private final static String FLAG_CAMBIO_CLAVE = "1";

	private static final Logger logger = LogManager.getLogger(TarjetaServiceImpl.class);

	private @Autowired ClienteMapper clienteMapper;

	private @Autowired TarjetaMapper tarjetaMapper;

	private @Autowired EmpresaMapper empresaMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void registrarSolicitudTarjeta(Tarjeta tarjeta, Cliente cliente) {
		try {
			logger.info("Inicio metodo registrarSolicitudTarjeta ");

			if (verificarTarjetasDisponiblesPorTipoTar(cliente.getTipoDocumento(), cliente.getNroDocumento(),
					SecurityContextFacade.getAuthenticatedUser().getRuc())) {
				if (cliente.getId() == null) {

					clienteMapper.registrarCliente(cliente);
					logger.info("Exito registro Cliente");
				} else {
					clienteMapper.actualizarCliente(cliente);
					logger.info("Exito actualizacion Cliente");

				}

				tarjeta.setIdEmpresa(empresaMapper
						.buscarEmpresaPorRUC(SecurityContextFacade.getAuthenticatedUser().getRuc()).getId());

				tarjeta.setIdUsu(SecurityContextFacade.getAuthenticatedUser().getId());
				cliente = clienteMapper.buscarCliente(cliente.getTipoDocumento(), cliente.getNroDocumento());
				logger.info("Exito trayendo cliente ");

				tarjeta.setIdCli(cliente.getId());
				tarjeta.setFechaCreacion(new Date());
				tarjeta.setFlagActualizarContacto("0");
				tarjetaMapper.registrarTarjeta(tarjeta);
				logger.info("Exito registro Tarjeta");
				tarjeta = tarjetaMapper.buscarTarjeta(tarjeta.getIdEmpresa(), tarjeta.getIdUsu(), cliente.getId());

				EstadoTarjeta estadoTarjeta = new EstadoTarjeta();
				estadoTarjeta.setIdTarjeta(tarjeta.getId());
				estadoTarjeta.setEstado(TipoEstadoTarjeta.SOLICITUD_TARJETA_REGISTRADA.getCod());
				estadoTarjeta.setFechaRegistro(new Date());
				estadoTarjeta.setUsuarioRegistro(UsefulWebApplication.obtenerUsuario().getUsername());

				tarjetaMapper.registrarEstadoTarjeta(estadoTarjeta);
				logger.info("Exito registro Estado Tarjeta");

			} else {
				throw new InternalServiceException(
						"Este cliente ya tiene una solicitud pendiente o una tarjeta activada");
			}
			logger.info("Fin metodo registrarSolicitudTarjeta ");

		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	private boolean verificarTarjetasDisponiblesPorTipoTar(String tipoDocumento, String nroDocuemnto, String ruc) {
		List<Tarjeta> tarjetasDelCliente = tarjetaMapper.buscarTarjetaPorTipoDocumento(tipoDocumento, nroDocuemnto );
		int countTarjetasActivadas = 0;
		for (Tarjeta tarjetaExistente : tarjetasDelCliente) {
			// Verificar si el estado de la tarjeta es activada, bloqueada o cancelada
			boolean tarjetaActiva = TipoEstadoTarjeta.TARJETA_ACTIVADA.getCod().equals(tarjetaExistente.getEstado())
					|| TipoEstadoTarjeta.TARJETA_BLOQUEADA.getCod().equals(tarjetaExistente.getEstado())
					|| TipoEstadoTarjeta.TARJETA_CANCELADA.getCod().equals(tarjetaExistente.getEstado());
			if (tarjetaActiva) {
				countTarjetasActivadas++;
			}

		}
		// TODO MAXIMO DE TARJETAS DISPONIBLES POR ENTIDAD
		if (countTarjetasActivadas >= 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<SolicitudTarjeta> buscarTodosSolicitudTarjetaPendientes() {
		try {
			return tarjetaMapper.buscarTodosSolicitudesTarjetaPendientes(
					TipoEstadoTarjeta.SOLICITUD_TARJETA_REGISTRADA.getCod(),
					UsefulWebApplication.obtenerUsuario().getRuc());
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void aprobarSolicitudTarjeta(List<SolicitudTarjeta> solicitudTarjetas) {
		try {
			for (SolicitudTarjeta solicitudTarjeta : solicitudTarjetas) {
				EstadoTarjeta estadoTarjeta = new EstadoTarjeta();
				estadoTarjeta.setIdTarjeta(solicitudTarjeta.getId());
				estadoTarjeta.setEstado(TipoEstadoTarjeta.SOLICITUD_TARJETA_AUTORIZADA.getCod());
				estadoTarjeta.setFechaRegistro(new Date());
				estadoTarjeta.setUsuarioRegistro(UsefulWebApplication.obtenerUsuario().getUsername());
				tarjetaMapper.registrarEstadoTarjeta(estadoTarjeta);
			}
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void rechazarSolicitudTarjeta(List<SolicitudTarjeta> solicitudTarjetas) {
		try {
			for (SolicitudTarjeta solicitudTarjeta : solicitudTarjetas) {
				EstadoTarjeta estadoTarjeta = new EstadoTarjeta();
				estadoTarjeta.setIdTarjeta(solicitudTarjeta.getId());
				estadoTarjeta.setEstado(TipoEstadoTarjeta.SOLICITUD_TARJETA_CANCELADA.getCod());
				estadoTarjeta.setFechaRegistro(new Date());
				estadoTarjeta.setUsuarioRegistro(UsefulWebApplication.obtenerUsuario().getUsername());
				tarjetaMapper.registrarEstadoTarjeta(estadoTarjeta);
			}
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public Tarjeta buscarTarjetaPorNumeroTarjeta(String numTarjeta) {
		try {
			return tarjetaMapper.buscarTarjetaPorNumeroTarjeta(numTarjeta );
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<Tarjeta> buscarTarjetaPorTipoDocumento(String tipoDocumento, String numDocumento) {
		try {
			return tarjetaMapper.buscarTarjetaPorTipoDocumento(tipoDocumento, numDocumento );
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public DatosTarjetaCliente buscarDatosTarjetasCliente(String tipoBusqueda, String numDocumento,
			String tipoOperacion) {
		try {
			// Obteniendo los objetos necesarios
			String rucUsuario = UsefulWebApplication.obtenerUsuario().getRuc();

			// Inicializando el objeto de retorno
			DatosTarjetaCliente datosTarjetaCliente = new DatosTarjetaCliente();

			// Variables locales para almacenar los datos
			Cliente cliente = null;
			Tarjeta tarjeta = null;
			List<Tarjeta> tarjetas = null;

			// Lógica de búsqueda según el tipo de búsqueda
			if (tipoBusqueda.equals(TipoBusqueda.NUM_TARJETA.getId())) {
				// Búsqueda por número de tarjeta
				cliente = clienteMapper.buscarClientePorNumTajeta(numDocumento);
				// MGL
				tarjeta = tarjetaMapper.buscarTarjetaPorNumeroTarjeta(numDocumento);
				datosTarjetaCliente.setCliente(cliente);
				datosTarjetaCliente.setTarjeta(tarjeta);
			} else if (tipoBusqueda.equals(TipoBusqueda.DNI.getId())
					|| tipoBusqueda.equals(TipoBusqueda.CARNET_EXTRANJERIA.getId())) {
				// Búsqueda por DNI o Carnet de Extranjería
				cliente = clienteMapper.buscarCliente(tipoBusqueda, numDocumento);
				datosTarjetaCliente.setCliente(cliente);

				// Búsqueda de tarjetas según la operación
				if (tipoOperacion.equals("B")) { // Bloqueo
					tarjetas = tarjetaMapper.buscarTarjetaPorTipoDocumento(tipoBusqueda, numDocumento);
				} else if (tipoOperacion.equals("C")) { // Cancelación
					tarjetas = tarjetaMapper.buscarTarjetaPorTipoDocumentoValidosParaBloqueo(tipoBusqueda, numDocumento);
				}
				datosTarjetaCliente.setTarjetas(tarjetas);
			}

			return datosTarjetaCliente;
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void actualizarEstadoTarjeta(EstadoTarjeta estadoTarjeta) {
		try {
			tarjetaMapper.registrarEstadoTarjeta(estadoTarjeta);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<TarjetaResumen> obtenerListaTarjetas(String cuentaCorriente, String fechaInicio, String fechaFin) {
		try {
			return tarjetaMapper.obtenerListaTarjetas(cuentaCorriente, fechaInicio, fechaFin);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}

	}

	@Override
	public void actualizarContacto(Tarjeta tarjeta) {
		try {
			tarjeta.setFlagActualizarContacto("1");
			tarjetaMapper.actualizarContacto(tarjeta);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void actualizarSaldos(Tarjeta tarjeta) {
		try {
			tarjetaMapper.actualizarSaldos(tarjeta);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void bloquearTarjetaPorRobo(EstadoTarjeta estadoTarjeta, Long idTarjeta, Long idCliente) {
		try {
			Tarjeta nuevaTarjeta = tarjetaMapper.buscarTarjetaPorId(idTarjeta);
			System.out.println("nuevaTarjeta:" + nuevaTarjeta.toString());
			tarjetaMapper.registrarEstadoTarjeta(estadoTarjeta);
			nuevaTarjeta.setIdEmpresa(
					empresaMapper.buscarEmpresaPorRUC(SecurityContextFacade.getAuthenticatedUser().getRuc()).getId());
			nuevaTarjeta.setIdUsu(SecurityContextFacade.getAuthenticatedUser().getId());
			nuevaTarjeta.setFechaCreacion(new Date());
			tarjetaMapper.registrarTarjeta(nuevaTarjeta);
			EstadoTarjeta estadoTarjetaCreacion = new EstadoTarjeta();
			estadoTarjetaCreacion.setIdTarjeta(nuevaTarjeta.getId());
			estadoTarjetaCreacion.setEstado(TipoEstadoTarjeta.SOLICITUD_TARJETA_REGISTRADA.getCod());
			estadoTarjetaCreacion.setFechaRegistro(new Date());
			estadoTarjetaCreacion.setUsuarioRegistro(UsefulWebApplication.obtenerUsuario().getUsername());
			tarjetaMapper.registrarEstadoTarjeta(estadoTarjetaCreacion);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void actualizarEstadoCuenta(Tarjeta tarjeta) {
		try {
			tarjeta.setFlagActualizarEstadoCuenta(FLAG_CAMBIO_CLAVE);
			tarjetaMapper.actualizarestadoCuenta(tarjeta);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public String verificarSolicitudes(String tipoDocumento, String nroDocumento) {
		// Obtiene el RUC del usuario autenticado
		String ruc = SecurityContextFacade.getAuthenticatedUser().getRuc();

		// Buscar solicitudes en estado "registrada"
		List<SolicitudTarjeta> solicitudesRegistradas = tarjetaMapper
				.buscarTodosSolicitudesTarjetaPendientes(TipoEstadoTarjeta.SOLICITUD_TARJETA_REGISTRADA.getCod(), ruc);

		// Buscar solicitudes en estado "autorizada"
		List<SolicitudTarjeta> solicitudesAutorizadas = tarjetaMapper
				.buscarTodosSolicitudesTarjetaPendientes(TipoEstadoTarjeta.SOLICITUD_TARJETA_AUTORIZADA.getCod(), ruc);

		// Buscar solicitudes en estado "enviada"
		List<SolicitudTarjeta> solicitudesEnviadas = tarjetaMapper
				.buscarTodosSolicitudesTarjetaPendientes(TipoEstadoTarjeta.SOLICITUD_TARJETA_ENVIADA.getCod(), ruc);

		// Verificar si el tipo y número de documento están en alguna de las listas
		if (existeEnLista(solicitudesRegistradas, tipoDocumento, nroDocumento)) {
			return "Este cliente ya tiene una solicitud pendiente de autorizar";
		}
		if (existeEnLista(solicitudesAutorizadas, tipoDocumento, nroDocumento)) {
			return "Este cliente ya tiene una solicitud autorizada pendiente de envio";
		}
		if (existeEnLista(solicitudesEnviadas, tipoDocumento, nroDocumento)) {
			return "Este cliente ya tiene una solicitud enviada en espera";
		}

		// Si no está en ninguna lista, devuelve null
		return null;
	}

	@Override
	public String verificarTarjetasDisponible(String tipoDocumento, String nroDocuemnto, Tarjeta tarjeta) {
		String ruc = SecurityContextFacade.getAuthenticatedUser().getRuc();
		TipoTarjetaNegocio typeTarActual = TipoTarjetaNegocio.fromCodigoYDiseno(tarjeta.getTipoTarjeta(),
				tarjeta.getDiseno());

		List<Tarjeta> tarjetasDelCliente = tarjetaMapper.buscarTarjetaPorTipoDocumento(tipoDocumento, nroDocuemnto );

		if (existeEnListaTarjetas(typeTarActual, tarjetasDelCliente)) {
			return ConstantesGenerales.TARJETA_NO_VALID;
		}

		return null;
	}

	private boolean existeEnListaTarjetas(TipoTarjetaNegocio typeTarActual, List<Tarjeta> tarjetasDelCliente) {

		if (typeTarActual == null) {
			return false; // Si el tipo de tarjeta actual no es válido, no se puede hacer la verificación.
		}

		// Recorrer la lista de tarjetas existentes del cliente
		for (Tarjeta tarjetaExistente : tarjetasDelCliente) {
			// Obtener el tipo de tarjeta de cada tarjeta existente
			TipoTarjetaNegocio tipoTarjetaExistente = TipoTarjetaNegocio
					.fromCodigoYDiseno(tarjetaExistente.getTipoTarjeta(), tarjetaExistente.getDiseno());
			// Verificar si el estado de la tarjeta es activada, bloqueada o cancelada
			boolean tarjetaActiva = TipoEstadoTarjeta.TARJETA_ACTIVADA.getCod().equals(tarjetaExistente.getEstado())
					|| TipoEstadoTarjeta.TARJETA_BLOQUEADA.getCod().equals(tarjetaExistente.getEstado())
					|| TipoEstadoTarjeta.TARJETA_CANCELADA.getCod().equals(tarjetaExistente.getEstado());

			// Comparar si coinciden el tipo de tarjeta y el diseño
			if (typeTarActual.getDiseno().equals(tipoTarjetaExistente.getDiseno()) && tarjetaActiva) {
				return true; // Ya existe una tarjeta del mismo tipo y diseño
			}
		}

		return false; // No se encontró una tarjeta del mismo tipo y diseño
	}

	/**
	 * Verifica si el tipo y número de documento están en la lista de solicitudes.
	 *
	 * @param solicitudes
	 *            La lista de solicitudes a verificar.
	 * @param tipoDocumento
	 *            El tipo de documento a buscar.
	 * @param nroDocumento
	 *            El número de documento a buscar.
	 * @return true si el documento está en la lista, de lo contrario false.
	 */
	private boolean existeEnLista(List<SolicitudTarjeta> solicitudes, String tipoDocumento, String nroDocumento) {
		for (SolicitudTarjeta solicitud : solicitudes) {
			if (solicitud.getTipoDocumento().equals(tipoDocumento)
					&& solicitud.getNumDocumento().equals(nroDocumento)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Tarjeta buscarPrimeraTarjetaCliente(String tipoDocumento, String numeroDocumento) {
		// TODO Auto-generated method stub
		try {
			return tarjetaMapper.buscarPrimeraTarjetaCliente(tipoDocumento, numeroDocumento);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}

	}


	@Override
	public Cliente buscarClientePorTipoNumero(Long  idcliente) {
		try {
			return clienteMapper.buscarClientePorid(idcliente);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}

	}

	@Override
	public Tarjeta buscarTarjetaPorId(Long idTar) {
		try {
			return tarjetaMapper.buscarTarjetaPorIdValidosParaBloqueo(idTar);
		} catch (Exception ex) {
			throw new InternalServiceException(ex.getMessage(), ex);
		}
	}

}
