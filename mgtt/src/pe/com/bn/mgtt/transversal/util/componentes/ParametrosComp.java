package pe.com.bn.mgtt.transversal.util.componentes;

 import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component("parametros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParametrosComp {

	private String urlComp;
	private String errorComp;
	private String desErrorComp;

	// PARAMETROS RENIEC
	public String consultaReniec;
	public String sistemaReniec;
	public String user1Reniec;
	public String userReniec;

	// PARAMETROS MC
	public String codigoEmisorMc;
	public String codigoUsuarioMc;
	public String numTerminalMc;
	public String prefijoNumReferenciaMc;
	public String wsUsuarioMc;
	public String wsClaveMc;
	public String wsSoapMc;
	public String wsComercioMc;
	



	// PARAMETROS TIEMPO
	private String sesionExpiradaTiempo;
	private String conexionTiempo;
	private String respuestaTiempo;
	
	 
 }
