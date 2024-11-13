package pe.com.bn.mgtt.infraestructura.facade;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import pe.com.bn.mgtt.infraestructura.exception.ExternalServiceMCProcesosException;
import pe.com.bn.mgtt.persistence.dto.internal.ws.DTOModificacionTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.ws.DTOwservice;
import pe.com.bn.mgtt.persistence.mapper.internal.ParametroMapper;
import pe.com.bn.mgtt.transversal.util.ConstantesWS;
import pe.com.bn.mgtt.transversal.util.NumeroALetras;
import pe.com.bn.mgtt.transversal.util.SoapClientUtil;
import pe.com.bn.mgtt.transversal.util.StringsUtils;
import pe.com.bn.mgtt.transversal.util.componentes.ParametrosComp;
import pe.com.bn.mgtt.transversal.util.excepciones.InternalExcepcion;

@Component
public class FWMCProcesos {

	private @Autowired ParametroMapper parametroMapper;

	private @Autowired ParametrosComp parametros;
	private static final Logger logger = LogManager.getLogger(FWMCProcesos.class);
 

	// Modificación de Tarjeta ok
	public DTOModificacionTarjeta modificacionTarjeta(String tipoMoneda, String numTarjeta, String codMotivo,
			String desMotivo) throws InternalExcepcion {
		// MGL
		/* obtener valores */
		String wsdlUrl = parametros.getWsSoapMc();
		String wsdlAS = parametros.getPrefijoNumReferenciaMc();

		String codEmisor = parametros.getCodigoEmisorMc();
		String codUsuario = parametros.getCodigoUsuarioMc();
		String numTerminal = parametros.getNumTerminalMc();
		String comercio = parametros.getWsComercioMc();
		String numReferenciaWS = wsdlAS
				+ NumeroALetras.llenarCerosAlaIzquierda(Long.toString(parametroMapper.obtenerNumeroReferenciaWS()), 10);

		String usuario = parametros.getWsUsuarioMc();
		String clave = parametros.getWsClaveMc();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fechaTerminal = sdf.format(new Date());
		System.out.println("fechaTerminal:" + fechaTerminal);

		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		String horaTerminal = dateFormat.format(new Date());
		System.out.println("horaTerminal:" + horaTerminal);

		DTOwservice dto = new DTOwservice(ConstantesWS.SOACTION_BLOQUEO_TARJETA);
		Class<DTOModificacionTarjeta> dtoClass = DTOModificacionTarjeta.class;
		DTOModificacionTarjeta responseDTO = null;

		Map<String, String> inputRequest = ConstantesWS.getModificacionTarjetaMap();

		String valor = "0005309270300003841";

		String numeroTarjeta = StringsUtils.quitarCeroIzquierdaString(valor);

		inputRequest.put(ConstantesWS.COD_EMISOR, codEmisor);
		inputRequest.put(ConstantesWS.COD_USUARIO, codUsuario);
		inputRequest.put(ConstantesWS.NUM_TERMINAL, numTerminal);
		inputRequest.put(ConstantesWS.NUM_REFERENCIA, numReferenciaWS);

		inputRequest.put(ConstantesWS.MONEDA_PRODUCTO, ConstantesWS.TIPO_MONEDA);
		inputRequest.put(ConstantesWS.NUM_TARJETA_MONEDERO, numeroTarjeta);
		inputRequest.put(ConstantesWS.CODIGO_BLOQUEO, codMotivo);
		inputRequest.put(ConstantesWS.MOTIVO_BLOQUEO, desMotivo);

		inputRequest.put(ConstantesWS.FECHA_TXN_TERMINAL, fechaTerminal);
		inputRequest.put(ConstantesWS.HORA_TXN_TERMINAL, horaTerminal);
		inputRequest.put(ConstantesWS.WS_USUARIO, usuario);
		inputRequest.put(ConstantesWS.WS_CLAVE, clave);
		inputRequest.put(ConstantesWS.RESERVADO, "");

		String soapRequestPrevie = ConstantesWS.generarXml(ConstantesWS.MODIFICACION_TARJETA_XML, inputRequest);

		String soapRequest = dto.getSoapTemplate().replace("SOAP_CONTENT", soapRequestPrevie);

		// logger.info("Request generado: " + soapRequest);

		int maxRetries = 5;
		int attempt = 0;
		boolean success = false;

		while (attempt < maxRetries && !success) {
			attempt++;
			String dataWS = "";
			try {
				dataWS = SoapClientUtil.sendSoapRequest(wsdlUrl, dto.getSoapAction(), soapRequest);
			} catch (ExternalServiceMCProcesosException e) {
				throw e;
			}

			try {

				logger.info("Respuesta del servidor:");
				logger.info(dataWS);

				Document documentoXML = SoapClientUtil.parseXmlResponse(dataWS.toString());
				String resultado = SoapClientUtil.getTextFromElement(documentoXML, dto.getResultTag());

				DTOwservice dto2 = new DTOwservice(ConstantesWS.SOACTION_MODIFICACION_TARJETA);

				String contenidoXML = resultado.substring(resultado.indexOf(dto2.getStartTag()), // <Modificacion_Tarjeta>
						resultado.indexOf(dto2.getEndTag()) // </Modificacion_Tarjeta>
								+ dto2.getEndTag().length());

				responseDTO = SoapClientUtil.convertirXMLAObjeto(new StringReader(contenidoXML), dtoClass);

				logger.info("Objeto de respuesta: " + responseDTO);
				success = true;
				logger.info("Conexión exitosa en el intento " + attempt);
			} catch (InternalExcepcion e) {
				throw e;
			}

		}
		return responseDTO;
	}

}
