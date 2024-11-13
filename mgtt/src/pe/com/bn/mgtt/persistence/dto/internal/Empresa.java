package pe.com.bn.mgtt.persistence.dto.internal;

 
import lombok.Data;

@Data
public class Empresa {

	private Long id;
	private String cuentaCorriente;
	private String ruc;
	private String tipo;
	private String razonSocial;
	private String direccion;
	private String ubigeo;
	private String cic;
	private String referencia;
	private String telefonoFijo;
	private String nombreCorto;

 

}
