package pe.com.bn.mgtt.aplication.view;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import pe.com.bn.mgtt.aplication.model.CambiarEstadoTarjetaModel;
import pe.com.bn.mgtt.infraestructura.exception.ExternalServiceBnTablasException;
import pe.com.bn.mgtt.infraestructura.exception.ExternalServiceMCProcesosException;
import pe.com.bn.mgtt.infraestructura.exception.ServiceException;
import pe.com.bn.mgtt.infraestructura.facade.BuscarTarjetaFacade;
import pe.com.bn.mgtt.infraestructura.facade.FWMCProcesos;
import pe.com.bn.mgtt.infraestructura.service.external.AgenciaService;
import pe.com.bn.mgtt.infraestructura.service.external.UbigeoService;
import pe.com.bn.mgtt.infraestructura.service.internal.EmpresaService;
import pe.com.bn.mgtt.infraestructura.service.internal.LogService;
import pe.com.bn.mgtt.infraestructura.service.internal.TarjetaService;
import pe.com.bn.mgtt.persistence.dto.external.Agencia;
import pe.com.bn.mgtt.persistence.dto.internal.Cliente;
import pe.com.bn.mgtt.persistence.dto.internal.Empresa;
import pe.com.bn.mgtt.persistence.dto.internal.EstadoTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.Tarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaOpcion;
import pe.com.bn.mgtt.persistence.dto.internal.ws.DTOModificacionTarjeta;
import pe.com.bn.mgtt.transversal.configuration.security.SecurityContextFacade;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.StringsUtils;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.enums.TipoBusqueda;
import pe.com.bn.mgtt.transversal.util.enums.TipoEstadoTarjeta;
import pe.com.bn.mgtt.transversal.util.excepciones.InternalExcepcion;

@Controller("cambiarEstadoTarjetaController")
@Scope("view")
@Getter
@Setter
public class CambiarEstadoTarjetaController implements Serializable {

	private static final Logger logger = LogManager.getLogger(CambiarEstadoTarjetaController.class);
	private static final long serialVersionUID = 1L;

	private CambiarEstadoTarjetaModel cambiarEstadoTarjetaModel;

	private @Autowired TarjetaService tarjetaService;

	private @Autowired FWMCProcesos fwmcProcesos;
	private @Autowired BuscarTarjetaFacade buscarTarjetaFacade;
	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private UbigeoService ubigeoService;

	@Autowired
	private AgenciaService agenciaService;

	@Autowired
	private LogService logService;

	@PostConstruct
	public void init() {
		cambiarEstadoTarjetaModel = new CambiarEstadoTarjetaModel();
		cambiarEstadoTarjetaModel.setBusquedaRealizada(false);
	}

	public void buscarTarjeta() {

		logger.info("Iniciando búsqueda de tarjeta...");
		logger.info("Tipo de Búsqueda Por: {}", cambiarEstadoTarjetaModel.getTipoBusquedaPor());
		logger.info("Tipo de Búsqueda : {}", cambiarEstadoTarjetaModel.getTipoBusqueda());
		logger.info("Número de Documento : {}", cambiarEstadoTarjetaModel.getNumDocumento());
		String tipoBusqueda = cambiarEstadoTarjetaModel.getTipoBusqueda();
		try {
			if (tipoBusqueda.equals(TipoBusqueda.NUM_TARJETA.getId())) {
				String tarjeta19 = StringsUtils.llenarCerosAlaIzquierdaV2(cambiarEstadoTarjetaModel.getNumDocumento(),
						19);

				cambiarEstadoTarjetaModel
						.setListaTarjetasOpcion(buscarTarjetaFacade.obtenerListaTarjetasOpcionTar(tarjeta19));

				logService.logConsultaTarjeta(tarjeta19);
			 

			} else if (tipoBusqueda.equals(TipoBusqueda.DNI.getId())
					|| tipoBusqueda.equals(TipoBusqueda.CARNET_EXTRANJERIA.getId())) {
				cambiarEstadoTarjetaModel.setListaTarjetasOpcion(buscarTarjetaFacade
						.obtenerListaTarjetasOpcion(tipoBusqueda, cambiarEstadoTarjetaModel.getNumDocumento()));
				logService.logConsultaCliente(cambiarEstadoTarjetaModel.getNumDocumento());
			
			}
			if (cambiarEstadoTarjetaModel.getListaTarjetasOpcion().isEmpty()) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_INTERNAL,
						"No se enceontraron tarjetas disponiles para el tipo de busqueda");
			} else {
				UsefulWebApplication.ejecutar("wvSeleccionarTajeta.show()");
				UsefulWebApplication.actualizarComponente("formSeleccionarTarjeta");
			}


		} catch (Exception e) {
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_INTERNAL, ConstantesGenerales.ERROR_PERSISTENCE_INTERNAL);
			logger.error(e.getMessage());
		}

	}

	public void seleccionarTarjeta() throws InternalExcepcion {
		cambiarEstadoTarjetaModel.setBusquedaRealizada(true);
		TarjetaOpcion opcionTar = cambiarEstadoTarjetaModel.getTarjetaOpcionSeleccionada();
		Cliente clienteSeleccionado = tarjetaService.buscarClientePorTipoNumero(opcionTar.getIdCliente());
		Tarjeta tarSeleccionado = tarjetaService.buscarTarjetaPorId(opcionTar.getIdTar());

		cambiarEstadoTarjetaModel.getDatosTarjetaCliente().setCliente(clienteSeleccionado);
		cambiarEstadoTarjetaModel.getDatosTarjetaCliente().setTarjeta(tarSeleccionado);

		cambiarEstadoTarjetaModel.setMotivosBloqueoTarjetas(buscarTarjetaFacade.obtenerListaBloqueosTarjeta("T"));
		cambiarEstadoTarjetaModel.setMotivosBloqueoCuenta(buscarTarjetaFacade.obtenerListaBloqueosTarjeta("C"));
		UsefulWebApplication.ejecutar("wvSeleccionarTajeta.hide()");
		UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta");

	}

	public void cambiarEstadoTarjeta() {

		try {
			DTOModificacionTarjeta modificacionTarjeta = new DTOModificacionTarjeta();

			cambiarEstadoTarjetaModel.iniciarEstadoTarjeta();

			String tipoMoneda = cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getTipoMoneda().trim();
			System.out.println("tipoMoneda:" + tipoMoneda);
			String numTarjeta = cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getNumTarjeta().trim();
			System.out.println("numTarjeta:" + numTarjeta);

			if (cambiarEstadoTarjetaModel.getTipoBloqueoSeleccionado().equals("T")) {

				String motivo = cambiarEstadoTarjetaModel.getMotivoSeleccionado();
				char guion = '-';
				int posicion = motivo.indexOf(guion);

				String codMotivo = motivo.substring(0, posicion).trim();
				String desMotivo = motivo.substring(posicion + 1).trim();

				System.out.println("codMotivo:" + codMotivo);
				System.out.println("desMotivo:" + desMotivo);

				System.out.println(cambiarEstadoTarjetaModel.getEstadoTarjeta().toString());

				/*
				 * if (codMotivo.equals(MotivosBloqueoTarjeta.ROBO.getId())) {
				 * cambiarEstadoTarjetaModel.inicializarFormularioEntrega(); try {
				 * cambiarEstadoTarjetaModel.setDepartamentos(ubigeoService.buscarDepartamentos(
				 * )); } catch (InternalServiceException ise) { logger.error(ise.getMessage());
				 * } catch (ServiceException se) { logger.error(se.getMessage()); }
				 * this.mostrarOpcionPorTipoUbicacion();
				 * 
				 * 
				 * UsefulWebApplication.ejecutar("dgSolicitarTarjeta.show()");
				 * UsefulWebApplication.actualizarComponente("formSolicitarTarjeta"); } else {
				 * 
				 * try { //modificacionTarjeta =
				 * fwmcProcesos.modificacionTarjeta(tipoMoneda,numTarjeta,codMotivo,desMotivo);
				 * modificacionTarjeta.setCodRespuesta("0000");
				 * System.out.println("codRespuesta:"+modificacionTarjeta.getCodRespuesta());
				 * //System.out.println("desRespuesta:"+modificacionTarjeta.getDescRespuesta());
				 * 
				 * if (modificacionTarjeta.getCodRespuesta().equals("0000")) {
				 * 
				 * tarjetaService.actualizarEstadoTarjeta(cambiarEstadoTarjetaModel.
				 * getEstadoTarjeta());
				 * 
				 * cambiarEstadoTarjetaModel.inicializarFormulario();
				 * 
				 * UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_INFO,
				 * "","Se cambio de estado exitosamente");
				 * UsefulWebApplication.actualizarComponente("msgs");
				 * UsefulWebApplication.actualizarComponente(
				 * "formCambiarEstadoTarjeta:pgResultado");
				 * 
				 * }else{ UsefulWebApplication .mostrarMensajeJSF(
				 * ConstantesGenerales.SEVERITY_ERROR, modificacionTarjeta.getDescRespuesta(),
				 * ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
				 * UsefulWebApplication.actualizarComponente(
				 * "formCambiarEstadoTarjeta:pgResultado");
				 * 
				 * } } catch (ExternalServiceMCProcesosException este) { UsefulWebApplication
				 * .mostrarMensajeJSF( ConstantesGenerales.SEVERITY_ERROR,
				 * ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC,
				 * ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
				 * logger.error(este.getMessage()); UsefulWebApplication.actualizarComponente(
				 * "formCambiarEstadoTarjeta:pgResultado");
				 * 
				 * } }
				 */
			} else {

				try {

					String motivo = cambiarEstadoTarjetaModel.getEstadoCuentaSeleccionado();
					char guion = '-';
					int posicion = motivo.indexOf(guion);

					String codMotivo = motivo.substring(0, posicion).trim();
					String desMotivo = motivo.substring(posicion + 1).trim();

					System.out.println("codMotivo:" + codMotivo);
					System.out.println("desMotivo:" + desMotivo);

					// modificacionTarjeta =
					// fwmcProcesos.modificacionTarjeta(tipoMoneda,numTarjeta,codMotivo,desMotivo);
					modificacionTarjeta.setCodRespuesta("0000");
					System.out.println("codRespuesta:" + modificacionTarjeta.getCodRespuesta());
					System.out.println("desRespuesta:" + modificacionTarjeta.getDescRespuesta());

					if (modificacionTarjeta.getCodRespuesta().equals("0000")) {

						cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta()
								.setEstadoCuenta(cambiarEstadoTarjetaModel.getEstadoCuentaSeleccionado());

						tarjetaService.actualizarEstadoCuenta(
								cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta());

						cambiarEstadoTarjetaModel.inicializarFormulario();

						UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_INFO, "",
								"Se cambio de estado exitosamente");
						UsefulWebApplication.actualizarComponente("msgs");
						UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

					} else {
						UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
								modificacionTarjeta.getDescRespuesta(),
								ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
						UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

					}
				} catch (ExternalServiceMCProcesosException este) {
					UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
							ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC,
							ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
					logger.error(este.getMessage());
					UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

				}

			}
		} catch (ExternalServiceMCProcesosException este) {
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
			logger.error(este.getMessage());
			UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");
		}

	}

	public void bloquearTarjetaRobo() throws InternalExcepcion {

		String tipoMoneda = cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getTipoMoneda().trim();
		System.out.println("tipoMoneda:" + tipoMoneda);
		String numTarjeta = cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getNumTarjeta().trim();
		System.out.println("numTarjeta:" + numTarjeta);

		DTOModificacionTarjeta modificacionTarjeta = new DTOModificacionTarjeta();

		System.out.println("idtarjeta:" + cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getId());
		System.out.println("idtarjeta:" + cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getCliente().getId());

		String motivo = cambiarEstadoTarjetaModel.getMotivoSeleccionado();
		char guion = '-';
		int posicion = motivo.indexOf(guion);

		String codMotivo = motivo.substring(0, posicion).trim();
		String desMotivo = motivo.substring(posicion + 1).trim();

		System.out.println("codMotivo:" + codMotivo);
		System.out.println("desMotivo:" + desMotivo);

		try {

			modificacionTarjeta = fwmcProcesos.modificacionTarjeta(tipoMoneda, numTarjeta, codMotivo, desMotivo);
			// modificacionTarjeta.setCodRespuesta("0000");
			System.out.println("codRespuesta:" + modificacionTarjeta.getCodRespuesta());

			if (modificacionTarjeta.getCodRespuesta().equals("0000")) {

				System.out.println();

				tarjetaService.bloquearTarjetaPorRobo(cambiarEstadoTarjetaModel.getEstadoTarjeta(),
						cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getTarjeta().getId(),
						cambiarEstadoTarjetaModel.getDatosTarjetaCliente().getCliente().getId());

				cambiarEstadoTarjetaModel.inicializarFormulario();

				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_INFO, "",
						"Se cambio de estado exitosamente");
				UsefulWebApplication.actualizarComponente("msgs");
				UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

			} else {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						modificacionTarjeta.getDescRespuesta(),
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
				UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

			}
		} catch (ExternalServiceMCProcesosException este) {
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC);
			logger.error(este.getMessage());
			UsefulWebApplication.actualizarComponente("formCambiarEstadoTarjeta:pgResultado");

		}

	}

	public void buscarTipoBusqueda() {
		if (cambiarEstadoTarjetaModel.getTipoBusquedaPor().equals("Por Documento")) {
			cambiarEstadoTarjetaModel.setListaTipoBusqueda(TipoBusqueda.obtenerTiposDocumento());
		} else if (cambiarEstadoTarjetaModel.getTipoBusquedaPor().equals("Por Tarjeta")) {
			cambiarEstadoTarjetaModel.setListaTipoBusqueda(TipoBusqueda.obtenerTiposNumeroTarjeta());
		} else {
			cambiarEstadoTarjetaModel.setListaTipoBusqueda(null);
		}
	}

	/**
	 * Muestra opciones según el tipo de ubicación de entrega seleccionada.
	 */
	public void mostrarOpcionPorTipoUbicacion() {
		try {
			if (cambiarEstadoTarjetaModel.getTarjeta().getEntregaUbicacion()
					.equals(ConstantesGenerales.ENTREGA_AGENCIA_BN)) {
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDireccion(null);
				cambiarEstadoTarjetaModel.setEsEntregaBN(true);
				cambiarEstadoTarjetaModel.setEsEntregaUE(false);
				cambiarEstadoTarjetaModel.setEsEntregaReferencia(true);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDepartamento(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaProvincia(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
			} else if (cambiarEstadoTarjetaModel.getTarjeta().getEntregaUbicacion()
					.equals(ConstantesGenerales.ENTREGA_UNIDAD_EJECUTORA)) {

				Empresa empresa = empresaService
						.buscarEmpresaPorRUC(SecurityContextFacade.getAuthenticatedUser().getRuc());
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDireccion(empresa.getDireccion());
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaUbigeo(empresa.getUbigeo());
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaAgenciaBN("0000");
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(empresa.getReferencia());
				cambiarEstadoTarjetaModel.setAgenciasBN(null);

				cambiarEstadoTarjetaModel.setEsEntregaBN(false);
				cambiarEstadoTarjetaModel.setEsEntregaUE(true);
				cambiarEstadoTarjetaModel.setEsEntregaReferencia(true);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDepartamento(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaProvincia(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
				cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
			} else {
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDireccion(null);
				cambiarEstadoTarjetaModel.setAgenciasBN(null);

				cambiarEstadoTarjetaModel.setEsEntregaBN(false);
				cambiarEstadoTarjetaModel.setEsEntregaUE(false);
				cambiarEstadoTarjetaModel.setEsEntregaReferencia(false);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDepartamento(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaProvincia(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
				cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
			}
		} catch (ExternalServiceBnTablasException se) {
			logger.error(se.getMessage());
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS);
		}
	}

	/**
	 * Busca agencias según el ubigeo seleccionado.
	 */
	public void buscarAgenciasPorUbigeo() {
		logger.info("[SolicitarTarjetaController] Inicio metodo buscarAgenciasPorUbigeo");
		String provincia = cambiarEstadoTarjetaModel.getTarjeta().getEntregaProvincia();
		String departamento = cambiarEstadoTarjetaModel.getTarjeta().getEntregaDepartamento();
		String distrito = cambiarEstadoTarjetaModel.getTarjeta().getEntregaDistrito();
		logger.info("[SolicitarTarjetaController] valor departamento: " + departamento);
		logger.info("[SolicitarTarjetaController] valor Provincia: " + provincia);
		logger.info("[SolicitarTarjetaController] valor distrito: " + distrito);

		if (distrito == null) {
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaAgenciaBN(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
		} else {
			try {
				cambiarEstadoTarjetaModel
						.setAgenciasBN(agenciaService.buscarAgenciasPorUbigeo(departamento, provincia, distrito));
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaAgenciaBN(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
			} catch (ExternalServiceBnTablasException este) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS);
				logger.error(este.getMessage());
			} catch (ServiceException es) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_GENERAL, ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
				logger.error(es.getMessage());
			}
		}
		logger.info("[SolicitarTarjetaController] Fin metodo buscarAgenciasPorUbigeo");
	}

	/**
	 * Busca los datos de una agencia según el código de agencia seleccionado.
	 */
	public void buscarDatosAgencia() {
		try {
			logger.info("[SolicitarTarjetaController] Inicio metodo buscarDatosAgencia");
			Agencia agencia = agenciaService
					.buscarAgenciaPorCodAgencia(cambiarEstadoTarjetaModel.getAgenciaSeleccionada().getCodAgencia());
			cambiarEstadoTarjetaModel.getTarjeta()
					.setEntregaDireccion(agencia == null ? "No hay dirección registrada" : agencia.getDireccion());
			logger.info("[SolicitarTarjetaController] fin metodo buscarDatosAgencia");
		} catch (ExternalServiceBnTablasException este) {
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS,
					ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS);
			logger.error(este.getMessage());
		} catch (ServiceException es) {
			UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
					ConstantesGenerales.ERROR_PERSISTENCE_GENERAL, ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
			logger.error(es.getMessage());
		}
	}

	/**
	 * Busca las provincias según el departamento seleccionado.
	 */
	public void buscarProvincias() {
		logger.info("[SolicitarTarjetaController] Inicio metodo buscarProvincias");
		String departamento = cambiarEstadoTarjetaModel.getTarjeta().getEntregaDepartamento();
		logger.info("[SolicitarTarjetaController] valor departamento: " + departamento);

		if (departamento == null) {
			cambiarEstadoTarjetaModel.setProvincias(null);
			cambiarEstadoTarjetaModel.setDistritos(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaProvincia(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
			cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
		} else {
			try {
				cambiarEstadoTarjetaModel.setProvincias(ubigeoService.buscarProvinciasPorDepartamento(departamento));
				cambiarEstadoTarjetaModel.setDistritos(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaProvincia(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
				cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
			} catch (ExternalServiceBnTablasException este) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS);
				logger.error(este.getMessage());
			} catch (ServiceException es) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_GENERAL, ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
				logger.error(es.getMessage());
			}
		}
		logger.info("[SolicitarTarjetaController] Fin metodo buscarProvincias");
	}

	/**
	 * Busca los distritos según la provincia seleccionada.
	 */
	public void buscarDistritos() {
		logger.info("[SolicitarTarjetaController] Inicio metodo buscarDistritos");
		String provincia = cambiarEstadoTarjetaModel.getTarjeta().getEntregaProvincia();
		String departamento = cambiarEstadoTarjetaModel.getTarjeta().getEntregaDepartamento();
		logger.info("[SolicitarTarjetaController] valor Provincia: " + provincia);
		logger.info("[SolicitarTarjetaController] valor departamento: " + departamento);

		if (provincia == null) {
			logger.info("[SolicitarTarjetaController] Provincia nulo");
			cambiarEstadoTarjetaModel.setDistritos(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
			cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
			cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
		} else {
			try {
				cambiarEstadoTarjetaModel
						.setDistritos(ubigeoService.buscarDistritosPorProvincia(departamento, provincia));
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaDistrito(null);
				cambiarEstadoTarjetaModel.setAgenciaSeleccionada(null);
				cambiarEstadoTarjetaModel.getTarjeta().setEntregaReferencia(null);
			} catch (ExternalServiceBnTablasException este) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS,
						ConstantesGenerales.ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS);
				logger.error(este.getMessage());
			} catch (ServiceException es) {
				UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR,
						ConstantesGenerales.ERROR_PERSISTENCE_GENERAL, ConstantesGenerales.ERROR_PERSISTENCE_GENERAL);
				logger.error(es.getMessage());
			}
		}
		logger.info("[SolicitarTarjetaController] Fin metodo buscarDistritos");
	}

}
