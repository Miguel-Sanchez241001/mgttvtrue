package pe.com.bn.mgtt.aplication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pe.com.bn.mgtt.persistence.dto.internal.LogAuditoria;
import pe.com.bn.mgtt.persistence.dto.internal.bloqueo.MotivosBloqueo;
import pe.com.bn.mgtt.transversal.util.enums.Funcionalidad;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogModel {

	private List<String> listaTiposBusqueda;
	private String tiposBusquedaSeleccione;
	private List<Funcionalidad> listaAccion;
	private Funcionalidad accionSeleccionada;
	private String username;
	private String agenciaName;
	private Date fechaInicio;
	private Date fechaFin;

	private List<LogAuditoria> bloqueosRealizados;
	private boolean busquedaPorUsername;
	private boolean busquedaPorFecha;
	private boolean busquedaPorAgencia;
	private boolean busquedaPorAccion;
	private boolean busquedaRealizada;
	public void inicializarFormulario() {
		busquedaPorUsername = false;
		busquedaPorFecha = false;
		busquedaPorAgencia = false;
		busquedaPorAccion = false;
		busquedaRealizada = false;
		listaTiposBusqueda = new ArrayList<String>();
		listaTiposBusqueda.add("Por usuario");
		listaTiposBusqueda.add("Por fecha");
		listaTiposBusqueda.add("Por agencia");
		listaTiposBusqueda.add("Por acción");
		listaAccion = Funcionalidad.obtenerListaFuncionalidades();

	}
    public void actualizarBusquedaPor() {
        busquedaPorUsername = false;
        busquedaPorFecha = false;
        busquedaPorAgencia = false;
        busquedaPorAccion = false;

         switch (tiposBusquedaSeleccione) {
            case "Por usuario":
                busquedaPorUsername = true;
                break;
            case "Por fecha":
                busquedaPorFecha = true;
                break;
            case "Por agencia":
                busquedaPorAgencia = true;
                break;
            case "Por acción":
                busquedaPorAccion = true;
                break;
        }
    }
}
