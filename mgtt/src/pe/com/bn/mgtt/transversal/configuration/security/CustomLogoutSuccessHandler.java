package pe.com.bn.mgtt.transversal.configuration.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String redirectUrl = determineRedirectUrl(request, authentication);
        response.sendRedirect(redirectUrl);
    }

    private String determineRedirectUrl(HttpServletRequest request, Authentication authentication) {
        return  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pkmslogout"; // Ejemplo de URL
    }
}

