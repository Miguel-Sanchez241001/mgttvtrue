package pe.com.bn.mgtt.transversal.configuration.security;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import pe.com.bn.mgtt.infraestructura.service.internal.CompService;
import pe.com.bn.mgtt.persistence.dto.internal.Permiso;
import pe.com.bn.mgtt.transversal.util.ConstantesSeguridad;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

    @Autowired
    private CompService compService;
    
    @Value("${area}")
    private String area;
    
    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {

        log.info("loadUserByUsername principal: " + principal);

        if (principal == null || principal.isEmpty()) {
            log.error("El principal es nulo o vacío.");
            throw new UsernameNotFoundException("Principal nulo o vacío.");
        }

        try {
            String[] datos = principal.split("\\|");

            if (datos.length < 4) {
                log.error("El formato del principal es incorrecto.");
                throw new UsernameNotFoundException("Formato de principal incorrecto.");
            }
          
            String username = datos[0];
            String grupos = datos[1];
            String codEmpleado = datos[2];
            String dniUsuario = datos[3];
            
            String rolSistema = ""; // Si es necesario, asignar un valor adecuado

            log.info("USUARIO: " + username);
            log.info("CODIGO DE EMPLEADO: " + codEmpleado);
            log.info("NRO DNI USUARIO: " + dniUsuario);

            String regex = "\\d+";
            if (!codEmpleado.trim().matches(regex)) {
                codEmpleado = dniUsuario;
            }

            String[] gruposArray = grupos.split(",");
            if (gruposArray.length == 0) {
                log.error("El usuario no tiene grupos asignados.");
                throw new BadCredentialsException("Usuario no tiene grupos autorizados.");
            }

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            for (String grupo : gruposArray) {
                if (grupo.endsWith("sate_bloqueo")) {
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("01S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("02S")));
                    rolSistema = "Operador Calcenter";
                }
                if (grupo.endsWith("sate_administrador")) {
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("01S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("02S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("03S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("04S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("05S")));
                    authorities.add(new SimpleGrantedAuthority(ConstantesSeguridad.OPCION_ACC.get("06S")));
                    rolSistema = "Auditor";
                }
                // Agrega más condiciones si es necesario
            }

            if (authorities.isEmpty()) {
                log.error("El usuario no tiene permisos asignados.");
                throw new BadCredentialsException("Usuario no tiene permisos autorizados.");
            }
            String[] areas = area.split("-"); 
            UsuarioSeguridad customUser = new UsuarioSeguridad(
                    username,
                    "",
                    authorities,
                    codEmpleado, 
                    areas[0],
                    areas[1],
                    "",
                    "",
                    username,
                    dniUsuario,
                    rolSistema,
                    new ArrayList<Permiso>()
            );

            try {
                compService.asignarParametros();
            } catch (Exception e) {
                log.error("Error al asignar parámetros en compService", e);
                // Decide si lanzar una excepción o continuar
            }

            log.info("CustomUserDetailsService | Login correcto: " + username);

            return customUser;

        } catch (Exception e) {
            log.error("Error en loadUserByUsername", e);
            throw new UsernameNotFoundException("Error al cargar el usuario", e);
        }
    }
}
