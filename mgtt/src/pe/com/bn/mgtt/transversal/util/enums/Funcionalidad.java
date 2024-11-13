package pe.com.bn.mgtt.transversal.util.enums;

import java.util.Arrays;
import java.util.List;

public enum Funcionalidad {
	
	   CONSULTAS_TARJETA("CONSULTA DATOS DE TARJETA"),
	    CONSULTAS_CLIENTE("CONSULTA DATOS DE CLIENTE"),
	    OPERACIONES_BLOQUEAR("BLOQUEO TARJETA"),
	    OPERACIONES_SOLICITAR_TARJETA("SOLICITAR TARJETA"),
	    OPERACIONES_MODIFICAR_DIRECCION("MODIFICAR DIRECCION"),
	    REPORTE_TARJETA_BLOQUEOS("CONSULTA REPORTE DE TARJETAS BLOQUEADAS"),
	    REPORTE_TARJETA_BLOQUEOS_EXPORTA("EXPORTA REPORTE DE TARJETAS BLOQUEADAS"),
	    CONSULTAS_LOG("CONSULTA REGISTROS LOGS"),
	    CONSULTAS_LOG_EXPORTA("EXPORTA REGISTROS LOGS");

	    private final String descripcion;

	    Funcionalidad(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }
	    public static List<Funcionalidad> obtenerListaFuncionalidades() {
	        return Arrays.asList(Funcionalidad.values());
	    }
}
