package pe.com.bn.mgtt.persistence.dto.internal;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {

	private Long id;
	private String nombres;
	private String apPaterno;
	private String apMaterno; //null
	private String tipoDocumento;
	private String nroDocumento;
	private Date fechaNacimiento;
	private String estadoCivil;
	private String sexo;
	private String telefonoCasa;
	private String direccion;//null
	private String ubigeo;//null
	private String referencia;//null
 
}
