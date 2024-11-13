package pe.com.bn.mgtt.persistence.dto.internal;

import java.util.Date;

import lombok.Data;

@Data
public class LogAuditoria {
	
	 private Date fecha;
	    private String hora;
	    private String usuario;
	    private String accion;
	    private String datos;
	    private String codAgencia;
	    private String nameAgencia;
}
