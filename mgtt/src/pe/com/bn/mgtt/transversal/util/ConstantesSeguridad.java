package pe.com.bn.mgtt.transversal.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantesSeguridad {
	
	public static final String ACCESO_OPERACIONES = "ACCESO_OPERACIONES";
	public static final String ACCESO_OPERACIONES_BLOQUEO_TARJETA = "ACCESO_OPERACIONES_BLOQUEO_TARJETA";
	public static final String ACCESO_REPORTE = "ACCESO_REPORTE";
	public static final String ACCESO_REPORTE_CONSULTA = "ACCESO_REPORTE_CONSULTA";
	public static final String ACCESO_LOG = "ACCESO_LOG";
	public static final String ACCESO_LOG_CONSULTA = "ACCESO_LOG_CONSULTA";
	
	public final static Map<String, String> OPCION_ACC = new HashMap<String, String>() {
		{
			put("01S", ACCESO_OPERACIONES);
			put("02S", ACCESO_OPERACIONES_BLOQUEO_TARJETA);
			put("03S", ACCESO_REPORTE);
			put("04S", ACCESO_REPORTE_CONSULTA);
			put("05S", ACCESO_LOG);
			put("06S", ACCESO_LOG_CONSULTA);
 	 
		}
	};

}
