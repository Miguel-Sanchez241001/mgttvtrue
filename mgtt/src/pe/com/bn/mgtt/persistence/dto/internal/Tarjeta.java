package pe.com.bn.mgtt.persistence.dto.internal;

import java.util.Date;

import lombok.Data;

@Data
public class Tarjeta {
	private Long id;
	private Long idEmpresa;
	private Long idUsu;
	private Long idCli;
	private String numTarjeta;  
	private String disposicionEfectivo;
	private String porcentajeDisposicionEfectivo;
	private String entregaAgenciaBNombre;
	private String usoDispocionEfectivo;
	private String usoExtranjero;
	private String usoComprasWeb;
	private String tipoTarjeta;
	private String diseno;
	private String observaciones;
	private String tipoMoneda;
	private String entregaUbicacion;
	private String entregaAgenciaBN;
	private String entregaDepartamento;
	private String entregaProvincia;
	private String entregaDistrito;
	private String entregaUbigeo;
	private String entregaReferencia;
	private String entregaDireccion;
	private String nroAutorizacion;
	private Date fechaAutorizacion;
	private Date fechaCreacion;
	private String usuarioCreacion;
	private Date fechaInicioLinea;
	private Date fechaTerminoLinea;
	private Date fechaVencimientoTar;
	private Double montoLineaAsignado;
	private Double montoLineaActual;
	private Double montoCompraUsado;
	private Double montoPorProcesar;
	private Double dispEfectivoUsado;
	private String estadoCuenta;
	private String numeroCuenta;
	private Date fechaAperturaCuenta;
	private String flagActualizarEstadoCuenta;
	private String descripcionUbicacion;
	private String empresa;
	private String estado;
	private String motivoBloqueo;
	private String usuarioBloqueo;
	private Date fechaBloqueo;
	private String tipoDocumento;
	private String numeroDocumento;
	private String Nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String operadorCelular;
	private String email;
	private String numeroCelular;
	private String flagActualizarContacto;
}
