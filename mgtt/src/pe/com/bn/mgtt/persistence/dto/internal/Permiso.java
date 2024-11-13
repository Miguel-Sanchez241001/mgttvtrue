package pe.com.bn.mgtt.persistence.dto.internal;

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
public class Permiso {

	private Long id;
	private Long idRol;
	private String codFuncionalidad;
	private String descripcion;
	private String estado;

 
}
