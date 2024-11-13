package pe.com.bn.mgtt.transversal.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

 
public class CustomRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private static final Logger log = LogManager.getLogger(CustomRequestHeaderAuthenticationFilter.class);

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        try {
        	// TODO > AUTENTICACION CON ISAN
            /*String username = request.getHeader("iv-user");
            String grupos = request.getHeader("iv-groups");
            String codTrabajador = request.getHeader("codtrabajador");
            String dniTrabajador = request.getHeader("dnitrabajador");*/
        	
    		String username = "dcribillero";
			String grupos = "grupoUsuariosWeb,grbn_BolPag_User,grbn_repr_administrador,grbn_repr_consulta,grbn_sate_administrador";
			String codTrabajador = "0312991";
			String dniTrabajador = "40300639"; 
			
            if (username == null || username.isEmpty()) {
                log.error("El encabezado 'iv-user' está ausente o vacío.");
                return null; // O lanzar una excepción si lo prefieres
            }

            // Manejo de encabezados faltantes opcionales
            grupos = (grupos != null) ? grupos.replace("\"", "") : "";
            codTrabajador = (codTrabajador != null) ? codTrabajador : "";
            dniTrabajador = (dniTrabajador != null) ? dniTrabajador : "";

            log.info("getPreAuthenticatedPrincipal HEADERS");
            log.info("iv-user: " + username);
            log.info("iv-groups: " + grupos);
            log.info("codtrabajador: " + codTrabajador);
            log.info("dnitrabajador: " + dniTrabajador);

            String principal = username + "|" + grupos + "|" + codTrabajador + "|" + dniTrabajador;

            log.info("Principal construido: " + principal);

            return principal;

        } catch (Exception e) {
            log.error("Error al obtener el principal preautenticado", e);
            return null; // O lanzar una excepción si lo prefieres
        }
    }
}

