package pe.com.bn.mgtt.infraestructura.service.internal;

import java.util.List;

 

import pe.com.bn.mgtt.persistence.dto.internal.Cliente;
import pe.com.bn.mgtt.persistence.dto.internal.DatosTarjetaCliente;
import pe.com.bn.mgtt.persistence.dto.internal.EstadoTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.SolicitudTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.Tarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaResumen;

 

public interface TarjetaService {

	public void registrarSolicitudTarjeta(Tarjeta tarjeta, Cliente cliente);

	public List<SolicitudTarjeta> buscarTodosSolicitudTarjetaPendientes();

	public void aprobarSolicitudTarjeta(List<SolicitudTarjeta> solicitudTarjetas);

	public void rechazarSolicitudTarjeta(
			List<SolicitudTarjeta> solicitudTarjetas);

	public Tarjeta buscarTarjetaPorNumeroTarjeta(String numTarjeta);

	public List<Tarjeta> buscarTarjetaPorTipoDocumento(String tipoDocumento,
			String numDocumento);

	public DatosTarjetaCliente buscarDatosTarjetasCliente(String tipoBusqueda,
			String numDocumento, String tipoOperacion);

	public void actualizarEstadoTarjeta(EstadoTarjeta estadoTarjeta);

	public List<TarjetaResumen> obtenerListaTarjetas(String cuentaCorriente, String fechaInicio,
			String fechaFin);

	public void actualizarContacto(Tarjeta tarjeta);
	
	public void actualizarSaldos(Tarjeta tarjeta);
	
	public void actualizarEstadoCuenta(Tarjeta tarjeta);

	public void bloquearTarjetaPorRobo(EstadoTarjeta estadoTarjeta,
			Long idTarjeta, Long idCliente);

	public String verificarSolicitudes(String tipoDocumento, String nroDocuemnto);
	public String verificarTarjetasDisponible(String tipoDocumento, String nroDocuemnto, Tarjeta tarjeta);
	
	
	public Tarjeta 	buscarPrimeraTarjetaCliente(String tipoDocumento, String numeroDocumento);

 
	public Cliente buscarClientePorTipoNumero(Long idcliente);
	
	public Tarjeta buscarTarjetaPorId(Long idTar);
}
