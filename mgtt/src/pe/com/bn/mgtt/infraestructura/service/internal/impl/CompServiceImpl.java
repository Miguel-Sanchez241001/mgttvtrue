package pe.com.bn.mgtt.infraestructura.service.internal.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pe.com.bn.mgtt.infraestructura.service.external.domain.ParametroInterfazKeyProxy;
import pe.com.bn.mgtt.infraestructura.service.external.domain.SistemaParametro;
import pe.com.bn.mgtt.infraestructura.service.internal.CompService;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.componentes.ParametrosComp;

@Service
public class CompServiceImpl implements CompService {

	private static final Logger logger = LogManager.getLogger(CompServiceImpl.class);

	@Autowired
	ParametrosComp parametros;
	@Value("${sistema}")
	private String sistema;

	@Value("${cuenta}")
	private String cuenta;

	@Value("${semilla}")
	private String semilla;

	@Value("${idUsuario}")
	private String idUsuario;

	@Value("${rutaClaveSegura}")
	private String rutaClaveSegura;
	@Value("${ruta.certificado}")
	private String rutaCertificado;
	
	@PostConstruct
	public void init() {

	}

	@Override
	public void asignarParametros() throws Exception {
		try {
			byte[] llave = leerClavesSegurades();

			ParametroInterfazKeyProxy proxyComp = new ParametroInterfazKeyProxy();

			SistemaParametro sParam = proxyComp.datoParametroService(sistema, cuenta, semilla, llave, idUsuario);

			logger.info("Codigo de proceso : " + sParam.getProceso().getCodigo());
			parametros.setErrorComp(sParam.getProceso().getCodigo());
			parametros.setDesErrorComp(sParam.getProceso().getDescripcion());

			if (sParam.getProceso().getCodigo().equals("00000")) {
				for (int n = 0; n < sParam.getGrupoParametro().getGrupoParametro().size(); n++) {
					int cantFilas = sParam.getGrupoParametro().getGrupoParametro().get(n).getParametro().getParametro()
							.size() - 1;
					for (int j = 0; j < cantFilas + 1; j++) {
						String param = sParam.getGrupoParametro().getGrupoParametro().get(n).getParametro()
								.getParametro().get(j).getAliasParam();
						String valor = sParam.getGrupoParametro().getGrupoParametro().get(n).getParametro()
								.getParametro().get(j).getValorParam();
						if (!param.equals("")) {
							this.setParametros(sParam.getGrupoParametro().getGrupoParametro().get(n).getAliasGrupo(),
									param, valor);
						}
					}
				}
				logger.info("Asignacion de parametros correctamente.");
				ConstantesGenerales.certificadoIzipay = rutaCertificado;
			} else {
				throw new Exception("No se pudo obtener los datos Comp " + sParam.getProceso().getCodigo());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	private void setParametros(String aliasGrupo, String param, String valor) {
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_CONEXION_RENIEC)) {
			this.setDatosReniec(param, valor);
		}
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_CONEXION_MC)) {
			this.setDatosMC(param, valor);
		}
		if (aliasGrupo.equals(ConstantesGenerales.GRUPO_TIEMPO)) {
			this.setDatosTiempo(param, valor);
		}
	}

	private void setDatosReniec(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_CONSULTRENIEC)) {
			parametros.setConsultaReniec(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_SISTEMARENIEC)) {
			parametros.setSistemaReniec(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_USER1RENIEC)) {
			parametros.setUser1Reniec(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_USERRENIEC)) {
			parametros.setUserReniec(valor);
		}
	}

	private void setDatosMC(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_CODIGOEMISOR)) {
			parametros.setCodigoEmisorMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_CODIGOUSUARIO)) {
			parametros.setCodigoUsuarioMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_NUMTERMINAL)) {
			parametros.setNumTerminalMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_PREFIJONUMREFERENCIA)) {
			parametros.setPrefijoNumReferenciaMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_WSUSUARIOMC)) {
			parametros.setWsUsuarioMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_WSCLAVEMC)) {
			parametros.setWsClaveMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_WSURLSOAPMC)) {
			parametros.setWsSoapMc(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_COMERCIO)) {
			parametros.setWsComercioMc(valor);
		}
	}

	private void setDatosTiempo(String param, String valor) {
		if (param.equals(ConstantesGenerales.PARAM_SESIONEXPIRADATIEMPO)) {
			parametros.setSesionExpiradaTiempo(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_CONEXIONTIEMPO)) {
			parametros.setConexionTiempo(valor);
		} else if (param.equals(ConstantesGenerales.PARAM_RESPUESTATIEMPO)) {
			parametros.setRespuestaTiempo(valor);
		}
	}

	public byte[] leerClavesSegurades() throws Exception {
		try {
			FileInputStream fis = new FileInputStream(new File(rutaClaveSegura));
			return IOUtils.toByteArray(fis);
		} catch (IOException ioe) {
			throw new Exception("Error con la lectura del archivo clavesegurades.key", ioe);
		}
	}
}
