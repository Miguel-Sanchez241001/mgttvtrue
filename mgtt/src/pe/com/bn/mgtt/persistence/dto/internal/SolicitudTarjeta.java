package pe.com.bn.mgtt.persistence.dto.internal;

import lombok.Data;

@Data
public class SolicitudTarjeta {

	private Long id;
	private String tipoDocumento;
	private String numDocumento;
	private String nombres;
	private String apellidos;
	private String dispEfectivo;
	private String usoExtranjero;
	private String tipoTarjeta;
	private String diseno;
 

}
