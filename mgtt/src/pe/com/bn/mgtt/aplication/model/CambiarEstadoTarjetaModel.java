package pe.com.bn.mgtt.aplication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.com.bn.mgtt.persistence.dto.external.Agencia;
import pe.com.bn.mgtt.persistence.dto.external.Ubigeo;
import pe.com.bn.mgtt.persistence.dto.internal.DatosTarjetaCliente;
import pe.com.bn.mgtt.persistence.dto.internal.EstadoTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.Tarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaOpcion;
import pe.com.bn.mgtt.persistence.dto.internal.bloqueo.MotivosBloqueo;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.enums.CodDocumentoWebservice;
import pe.com.bn.mgtt.transversal.util.enums.TipoBusqueda;
import pe.com.bn.mgtt.transversal.util.enums.TipoEstadoTarjeta;
import pe.com.bn.mgtt.transversal.util.enums.TipoMoneda;
import pe.com.bn.mgtt.transversal.util.enums.TipoTarjeta;
import pe.com.bn.mgtt.transversal.util.enums.TipoTarjetaNegocio;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CambiarEstadoTarjetaModel {

	private List<TipoBusqueda> listaTipoBusqueda;
	private List<String> listaTipoBusquedaPor;
	private String tipoBusquedaPor;
	private String tipoBusqueda;
	private String numDocumento;

	
	
	private DatosTarjetaCliente datosTarjetaCliente;
	private List<TarjetaOpcion> listaTarjetasOpcion;
	private TarjetaOpcion tarjetaOpcionSeleccionada;
	private Tarjeta tarjetaSeleccionada;
	private boolean busquedaRealizada;


	private boolean busquedaBloqueoTarjeta;

	private List<MotivosBloqueo> motivosBloqueoTarjetas;
	private List<MotivosBloqueo> motivosBloqueoCuenta;
	private EstadoTarjeta estadoTarjeta;
	private String motivoSeleccionado;
	private String estadoCuentaSeleccionado;
	private String tipoBloqueoSeleccionado;
	
	
  
	private List<Ubigeo> departamentos;
	private List<Ubigeo> provincias;
	private List<Ubigeo> distritos;
	private List<Agencia> agenciasBN;
	private Agencia agenciaSeleccionada;
	private boolean esEntregaBN;
	private Tarjeta tarjeta;
	private boolean esEntregaUE;
	private boolean esEntregaReferencia;
	
	
	
	
	
	public CambiarEstadoTarjetaModel() {
 		datosTarjetaCliente = new DatosTarjetaCliente();
		tipoBusqueda = TipoBusqueda.NUM_TARJETA.getId();	
		listaTipoBusquedaPor = new ArrayList<>();
        listaTipoBusquedaPor.add("Por Documento");
        listaTipoBusquedaPor.add("Por Tarjeta");
		tipoBloqueoSeleccionado = "T";
 	}

 

	 

	public String descripcionTipoDocumento(String codigo) {
		return CodDocumentoWebservice.descripcionCodDocumentoWebservice(codigo);
	}

	public String descripcionTipoTarjeta(String codigo, String diseno) {
		return TipoTarjetaNegocio.descripcionTipotarjeta(codigo, diseno);
	}
	public String descripcionTipoTarjetaMC(String codigo) {
		return TipoTarjeta.descripcionTipotarjeta(codigo);
	}
	public String descripcionEstadoTarjeta(String codigo) {
		return TipoEstadoTarjeta.descripcionTipoEstadoTarjeta(codigo);
	}

	public String descripcionMotivoBloqueotarjeta(MotivosBloqueo motivosBloqueo) {
		return motivosBloqueo.getBloqueoDes();
	}

	public String descripcionNumeroTarjeta(String numTarjeta) {
		return UsefulWebApplication.formatoNumTarjeta(numTarjeta);
	}

	public void iniciarEstadoTarjeta() {
		estadoTarjeta = new EstadoTarjeta();
		if (busquedaBloqueoTarjeta) {
			estadoTarjeta.setFechaRegistro(new Date());
			estadoTarjeta
					.setEstado(TipoEstadoTarjeta.TARJETA_ACTIVADA.getCod());
			estadoTarjeta.setMotivo("");  
			estadoTarjeta.setUsuarioRegistro(UsefulWebApplication
					.obtenerUsuario().getUsername());
			estadoTarjeta
					.setIdTarjeta(datosTarjetaCliente.getTarjeta().getId());
		} else {
			estadoTarjeta.setFechaRegistro(new Date());
			estadoTarjeta.setEstado(TipoEstadoTarjeta.TARJETA_BLOQUEADA
					.getCod());
			estadoTarjeta.setMotivo("");  
			estadoTarjeta.setUsuarioRegistro(UsefulWebApplication
					.obtenerUsuario().getUsername());
			estadoTarjeta
					.setIdTarjeta(datosTarjetaCliente.getTarjeta().getId());
		}
	}

	public void inicializarFormulario() {
		datosTarjetaCliente = new DatosTarjetaCliente();
		motivoSeleccionado = null;
		numDocumento = null;
		tipoBusqueda = null;
		busquedaRealizada = false;
		busquedaBloqueoTarjeta = false;
		estadoTarjeta = null;
		estadoCuentaSeleccionado = null;
		tipoBusqueda = "N";
		tipoBloqueoSeleccionado = "T";

	}

	public int obtenerBusquedaLength() {

		return TipoBusqueda.obtenerLength(tipoBusqueda);
	}

	public String obtenerBusquedaValidatorMessage() {
		return "El " + TipoBusqueda.tipoBusquedaLetras(tipoBusqueda)
				+ " debe  tener " + TipoBusqueda.obtenerLength(tipoBusqueda)
				+ " dígitos";
	}

	public String obtenerBusquedaRequiredMessage() {
		return "Ingrese un número de "
				+ TipoBusqueda.tipoBusquedaLetras(tipoBusqueda);
	}

	 

 

 

 

	public String valorBoton() {

		if (tipoBloqueoSeleccionado.equals("T"))
			if (datosTarjetaCliente.getTarjeta().getEstado().equals(TipoEstadoTarjeta.TARJETA_BLOQUEADA.getCod()))
				return "Activar";
			else
				return "Bloquear";
		else if (datosTarjetaCliente.getTarjeta().getEstadoCuenta().equals("K"))
			return "Activar";
		else
			return "Bloquear";

	}
	
	public String obtenerMotivo(String id,String descripcion){
		return (id+"-"+descripcion);
	}
	
	public String obtenerEstadoCuenta(String id,String descripcion){
		return (id+"-"+descripcion);
	}

 
 

 
 

 
 
 
 

 
 

 

 
 
	
	public void inicializarFormularioEntrega() {
		tarjeta = new Tarjeta();
		tarjeta.setTipoMoneda(TipoMoneda.MONEDA_SOLES.getId());
		tarjeta.setEntregaUbicacion("4");			
		esEntregaBN = true;
		esEntregaUE = false;
		esEntregaReferencia = false;
		provincias = null;
		distritos = null;
		agenciasBN = null;
		agenciaSeleccionada = null;
		
	}
	
	
}
