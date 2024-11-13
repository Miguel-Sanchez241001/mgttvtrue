package pe.com.bn.mgtt.aplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.bn.mgtt.transversal.configuration.security.UsuarioSeguridad;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CabeceraModel {
	private UsuarioSeguridad usuario;
	private String fecha;
}
