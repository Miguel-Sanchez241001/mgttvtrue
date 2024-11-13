package pe.com.bn.mgtt.infraestructura.service.internal;

import pe.com.bn.mgtt.transversal.configuration.security.UsuarioSeguridad;

public interface NotificacionService {
	public void enviarMailUsuarioClave(UsuarioSeguridad usuario, String clave);
}
