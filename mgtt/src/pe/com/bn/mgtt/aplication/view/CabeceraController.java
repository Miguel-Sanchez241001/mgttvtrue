package pe.com.bn.mgtt.aplication.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import pe.com.bn.mgtt.aplication.model.CabeceraModel;
import pe.com.bn.mgtt.transversal.util.DateFormaterUtil;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;

@Controller("cabeceraController")
@Scope("view")
@Getter
@Setter
public class CabeceraController {

	private CabeceraModel cabeceraModel;

	@PostConstruct
	public void init() {
		cabeceraModel = new CabeceraModel();
		obtenerDatosUsuario();
		obtenerFechaHoy();
		UsefulWebApplication.ejecutar("esUsuarioNuevo =  false");
	}

	public void obtenerDatosUsuario() {
		cabeceraModel.setUsuario(UsefulWebApplication.obtenerUsuario());
	}

	public void obtenerFechaHoy() {
		this.cabeceraModel.setFecha(DateFormaterUtil.fechaHoy());
	}

}
