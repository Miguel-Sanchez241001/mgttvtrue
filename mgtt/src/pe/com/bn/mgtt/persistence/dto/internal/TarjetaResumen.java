package pe.com.bn.mgtt.persistence.dto.internal;

import java.util.Date;

import lombok.Data;
@Data
public class TarjetaResumen {

	private Long id;
	private String razonSocialUE;
	private String usuarioCreacion;
	private Date fechaCreacion;
	private String estado;
	private Date fechaEstado;
	private String tipoTarjeta;
	private String numTarjeta;
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String apellidos;

	 

}
