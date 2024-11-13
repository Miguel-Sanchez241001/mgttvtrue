package pe.com.bn.mgtt.aplication.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.bn.mgtt.transversal.configuration.security.UsuarioSeguridad;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SeguridadModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private UsuarioSeguridad usuario;

	 

	public UsuarioSeguridad getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSeguridad usuario) {
		this.usuario = usuario;
	}

}
