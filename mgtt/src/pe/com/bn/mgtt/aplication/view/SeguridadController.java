package pe.com.bn.mgtt.aplication.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import pe.com.bn.mgtt.aplication.model.SeguridadModel;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.ConstantesPagina;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.componentes.ParametrosComp;

@Controller("seguridadController")
@Scope("view")
@Getter
@Setter
public class SeguridadController implements Serializable {

 
	  private static final Logger logger = LogManager.getLogger(SeguridadController.class);

	private static final long serialVersionUID = 1L;

	private SeguridadModel seguridadModel;

 

	@Autowired
	ParametrosComp parametros;

	@PostConstruct
	public void init() {
	 
		seguridadModel = new SeguridadModel(
				UsefulWebApplication.obtenerUsuario());
 
	}

	public void cerrarSesion() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		SecurityContextHolder.clearContext();
		UsefulWebApplication.redireccionar(ConstantesPagina.PAGINA_INDEX);
	}

	public void forzarCierreSesion() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		SecurityContextHolder.clearContext();
	}

	/**
	 * @return tiempo de inactividad maximo de una sesión
	 */
	public int tiempoMaximoInactividad() {
		return Integer
				.parseInt(parametros.getSesionExpiradaTiempo() != null ? parametros
						.getSesionExpiradaTiempo() : "10") * 1000 * 60;
	}

	/**
	 * @return mensaje que mostrara el dialogo luego de que expire la sesión
	 */
	public String mensajeSesionExpirada() {
		return ConstantesGenerales.MENSAJE_SESION_EXPIRADA;
	}

	/**
	 * @return url de la pagina de inicio de sesión
	 */
	public String paginaLogin() {
		return ConstantesPagina.PAGINA_INDEX;
	}

	public boolean renderizar(String valor) {
		for (GrantedAuthority sg : SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()) {
			if (valor.equals(sg.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	public void redireccionar(ActionEvent event) {
		String pagina = (String) event.getComponent().getAttributes()
				.get("pagina");
		try {
			UsefulWebApplication.redireccionar(pagina);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

 

	 
}
