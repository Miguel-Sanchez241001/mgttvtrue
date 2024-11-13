package pe.com.bn.mgtt.transversal.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import pe.com.bn.mgtt.transversal.util.ConstantesPagina;
import pe.com.bn.mgtt.transversal.util.ConstantesSeguridad;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**", "/error/*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers()
                .httpStrictTransportSecurity()
           
                .and()
            .sessionManagement()
                .maximumSessions(1)
                .expiredUrl(ConstantesPagina.PAGINA_INDEX)
                .and()
                .invalidSessionUrl(ConstantesPagina.PAGINA_INDEX)
                .and()
            .addFilterBefore(customRequestHeaderAuthenticationFilter(), BasicAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(ConstantesPagina.PAGINA_OPERACIONES_BLOQUEO_TARJETA)
                    .hasAnyAuthority(ConstantesSeguridad.ACCESO_OPERACIONES_BLOQUEO_TARJETA)
                .antMatchers(ConstantesPagina.PAGINA_REPORTE_CONSULTA)
                    .hasAnyAuthority(ConstantesSeguridad.ACCESO_REPORTE_CONSULTA)
                .antMatchers(ConstantesPagina.PAGINA_LOG_CONSULTA)
                    .hasAnyAuthority(ConstantesSeguridad.ACCESO_LOG_CONSULTA)
                .anyRequest().authenticated()
                .and()
            .exceptionHandling()
                .accessDeniedPage(ConstantesPagina.PAGINA_ACCESO_DENEGADO)
                .and()
            .logout()
                .logoutUrl(ConstantesPagina.LOGIN_URL_CERRAR_SESION)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .and()
            .csrf().disable();
    }

    @Bean
    public CustomRequestHeaderAuthenticationFilter customRequestHeaderAuthenticationFilter() throws Exception {
        CustomRequestHeaderAuthenticationFilter filter = new CustomRequestHeaderAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setExceptionIfHeaderMissing(false);
        filter.setPrincipalRequestHeader("iv-user"); // Establece el encabezado para el principal
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(preauthAuthProvider());
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
        provider.setThrowExceptionWhenTokenRejected(false); // Evitar excepciones si el token es rechazado
        return provider;
    }

    @Bean
    public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
        wrapper.setUserDetailsService(customUserDetailsService);
        return wrapper;
    }
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
