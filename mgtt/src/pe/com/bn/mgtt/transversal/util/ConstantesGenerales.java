package pe.com.bn.mgtt.transversal.util;

public class ConstantesGenerales {
	// PARAMETRO COMP

		// GRUPO RENIEC
		public final static String GRUPO_CONEXION_RENIEC = "CONEXION_RENIEC";
		public final static String PARAM_CONSULTRENIEC = "CONSULTARENIEC";
		public final static String PARAM_SISTEMARENIEC = "SISTEMARENIEC";
		public final static String PARAM_USER1RENIEC = "USER1RENIEC";
		public final static String PARAM_USERRENIEC = "USERRENIEC";

		// GRUPO MC
		public final static String GRUPO_CONEXION_MC = "CONEXION_MC";
		public final static String PARAM_CODIGOEMISOR = "CODIGOEMISORMC";
		public final static String PARAM_CODIGOUSUARIO = "CODIGOUSUARIOMC";
		public final static String PARAM_NUMTERMINAL = "NUMTERMINALMC";
		public final static String PARAM_PREFIJONUMREFERENCIA = "PREFIJONUMREFERENCIAMC";
		public final static String PARAM_WSUSUARIOMC = "WSUSUARIOMC";
		public final static String PARAM_WSCLAVEMC = "WSCLAVEMC";
		public static final String PARAM_WSURLSOAPMC = "WSURLSOAPMC";
		public final static String PARAM_COMERCIO = "COMERCIO";


		// GRUPO TIEMPO
		public final static String GRUPO_TIEMPO = "TIEMPO";
		public final static String PARAM_SESIONEXPIRADATIEMPO = "SESIONEXPIRADATIEMPO";
		public final static String PARAM_CONEXIONTIEMPO = "CONEXIONTIEMPO";
		public final static String PARAM_RESPUESTATIEMPO = "RESPUESTATIEMPO";
		public static final String MENSAJE_SESION_EXPIRADA = null;
		public static String certificadoIzipay = null;

		public static final int SEVERITY_INFO = 1;
		public static final int SEVERITY_WARN = 2;
		public static final int SEVERITY_ERROR = 3;
		public static final int SEVERITY_FATAL = 4;
		
		
		public static String ERROR_PERSISTENCE_GENERAL = "Error en la BD, consulte con el administrador.";
		public static String ERROR_PERSISTENCE_INTERNAL = "Error en la BDSATE, consulte con el administrador.";
		public static String ERROR_PERSISTENCE_EXTERNAL_BN_TABLAS = "Error en la BDSATE, consulte con el administrador.";
		public static String ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_SIMM = "Error con WS Correos, consulte con el administrador.";
 		public static String ERROR_PERSISTENCE_EXTERNAL_WEB_SERVICE_MC = "Error con WS MC, consulte con el administrador.";
 		
 		public static final String ENTREGA_AGENCIA_BN = "4";
 		public static final String ENTREGA_UNIDAD_EJECUTORA = "3";
 		public static final String TARJETA_NO_VALID = "El solicitante ya tiene asignada una tarjeta empresarial del mismo tipo de gasto en la unidad ejecutora o empresa.";
 
}
