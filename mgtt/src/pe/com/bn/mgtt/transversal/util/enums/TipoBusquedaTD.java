package pe.com.bn.mgtt.transversal.util.enums;

import java.util.ArrayList;
import java.util.List;

public enum TipoBusquedaTD {


	POR_TIPODOCUMENTO("1", "POR TIPO DOCUMENTO"), 
	POR_TARJETA("2", "POR TARJETA")   ;
	
	private String id;
	private String descripcion;
	

	private TipoBusquedaTD(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
		
	}

	public static List<TipoBusquedaTD> buscarTipoBusquedaTD() {
		List<TipoBusquedaTD> listaTipoBusquedaTD = new ArrayList<TipoBusquedaTD>();
		listaTipoBusquedaTD.add(TipoBusquedaTD.POR_TIPODOCUMENTO);
		listaTipoBusquedaTD.add(TipoBusquedaTD.POR_TARJETA);
 		return listaTipoBusquedaTD;
	}
	
	public static String descripcionTipoBusquedaTD(String codigo) {
		if (codigo != null)
			for (TipoBusquedaTD tipoTarjeta : values()) {
				if (codigo.equals(tipoTarjeta.getId())) {
					return tipoTarjeta.getDescripcion();
				}
			}
		return "Ninguno";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
