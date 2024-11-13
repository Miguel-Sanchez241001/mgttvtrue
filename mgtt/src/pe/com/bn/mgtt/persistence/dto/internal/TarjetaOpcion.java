package pe.com.bn.mgtt.persistence.dto.internal;

 

import lombok.Data;

@Data
public class TarjetaOpcion {
	private Long idTar;
	private Long idEmpre;
	private Long idCliente;
	private String numTar;
	private String nomCliente;
	private String tipoTar;
	private String tipoGasto;
	private String estado;
}
