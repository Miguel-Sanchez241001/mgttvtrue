package pe.com.bn.mgtt.persistence.dto.external;

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
public class Ubigeo {
	private Long id;
	private String codDepartamento;
	private String codProvincia;
	private String codDistrito;
	private String descripcion;



}
