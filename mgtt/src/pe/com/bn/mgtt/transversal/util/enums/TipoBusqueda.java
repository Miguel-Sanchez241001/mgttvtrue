package pe.com.bn.mgtt.transversal.util.enums;
import java.util.ArrayList;
import java.util.List;

public enum TipoBusqueda {

 
	NUM_TARJETA("N", "NÚMERO DE TARJETA", 16),
	DNI("1", "DNI", 8), 
	CARNET_EXTRANJERIA( "4", "CARNET DE EXTRANJERÍA", 12);
	
	private String id;
	private String descripcion;
	private int length;

	private TipoBusqueda(String id, String descripcion, int length) {
		this.id = id;
		this.descripcion = descripcion;
		this.length = length;
	}

	public static String tipoBusquedaLetras(String tipoBusqueda) {
		if (tipoBusqueda != null)
			for (TipoBusqueda tipoBusquedaEnum : TipoBusqueda.values()) {
				if (tipoBusqueda.equals(tipoBusquedaEnum.getId()))
					return tipoBusquedaEnum.getDescripcion();
			}
		return "";
	}

	public static int obtenerLength(String tipoBusqueda) {
		if (tipoBusqueda != null)
			for (TipoBusqueda tipoBusquedaEnum : TipoBusqueda.values()) {
				if (tipoBusqueda.equals(tipoBusquedaEnum.getId()))
					return tipoBusquedaEnum.getLength();
			}
		return 0;
	}
	
	
	 public static List<TipoBusqueda> obtenerTiposDocumento() {
	        List<TipoBusqueda> tiposDocumento = new ArrayList<>();
	        for (TipoBusqueda tipo : TipoBusqueda.values()) {
	            if (!tipo.equals(NUM_TARJETA)) {  
	                tiposDocumento.add(tipo);
	            }
	        }
	        return tiposDocumento;
	    }

	    public static List<TipoBusqueda> obtenerTiposNumeroTarjeta() {
	        List<TipoBusqueda> tiposNumeroTarjeta = new ArrayList<>();
	        for (TipoBusqueda tipo : TipoBusqueda.values()) {
	            if (tipo.equals(NUM_TARJETA)) { 
	                tiposNumeroTarjeta.add(tipo);
	            }
	        }
	        return tiposNumeroTarjeta;
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
