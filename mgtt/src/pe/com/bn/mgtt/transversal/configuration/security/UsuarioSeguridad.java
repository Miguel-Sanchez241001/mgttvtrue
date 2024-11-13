package pe.com.bn.mgtt.transversal.configuration.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.com.bn.mgtt.persistence.dto.internal.Permiso;


@Getter
@Setter
@ToString
public class UsuarioSeguridad extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String apPaterno;
	private String apMaterno;
	private String nombres;
	private String tipoDocumento;
	private String numDocumento;
	private List<Permiso> permisos;
	private String rol;
	private String ruc;
	private String flagCambioClave;
	private String nombreArea;
	private String codigoEmpleado;
	private String codigoArea;
	private String dni;
	private String cargo;
	
	public UsuarioSeguridad(String username, String password,
			Collection<? extends GrantedAuthority> authorities, Long id,
			String apPaterno, String apMaterno, String nombres,
			String tipoDocumento, String numDocumento, String ruc,
			List<Permiso> permisos, String rol, String flagCambioClave) {
		super(username, password, authorities);
		this.id = id;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
		this.tipoDocumento = tipoDocumento;
		this.numDocumento = numDocumento;
		this.permisos = permisos;
		this.ruc = ruc;
		this.rol = rol;
		this.flagCambioClave = flagCambioClave;
	}

 

	public UsuarioSeguridad(
			String username, 
			String password,
			Collection<? extends GrantedAuthority> authorities,
			String codigoEmpleado,
			String codigoArea, 
			String nombreArea,
			String apPaterno, 
			String apMaterno,
			String nombres, 
			String dni,
			String cargo, 
			List<Permiso> permisos) {
		super(username, password, authorities);
		this.codigoEmpleado = codigoEmpleado;
		this.codigoArea = codigoArea;
		this.nombreArea = nombreArea;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.nombres = nombres;
		this.dni = dni;
		this.cargo = cargo;
		this.permisos = permisos;
	}



	public String nombreCompleto() {
		return this.nombres + " " + this.apPaterno + " " + this.apMaterno;
	}



}
