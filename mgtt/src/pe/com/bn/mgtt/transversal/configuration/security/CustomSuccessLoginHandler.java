package pe.com.bn.mgtt.transversal.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;


/**
 * CustomAuthenticationSuccessHandler maneja las acciones a realizar 
 * cuando la autenticación es exitosa.
 */
@Component
public class CustomSuccessLoginHandler implements AuthenticationSuccessHandler {
 
    private static final Logger logger = LogManager.getLogger(CustomSuccessLoginHandler.class);

	 /**
     * Método invocado cuando la autenticación es exitosa.
     * Redirige al usuario a la página principal.
     *
     * @param request        el HttpServletRequest
     * @param response       el HttpServletResponse
     * @param authentication el Authentication
     * @throws IOException      si ocurre un error de entrada/salida
     * @throws ServletException si ocurre un error en el servlet
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Autenticación exitosa para el usuario: " + authentication.getName());
        logger.info("Redireccionando a la página principal.");
        UsefulWebApplication.redireccionar("/secure/principal.jsf");
    }
}