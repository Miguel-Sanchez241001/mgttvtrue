package pe.com.bn.mgtt.transversal.util.enums;

public enum TipoMoneda {

	MONEDA_SOLES("604", "Soles"), MONEDA_DOLARES("840", "Dolares");

	private String id;
	private String descripcion;

	private TipoMoneda(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
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
	
	public static String decripcionTipoMoneda(String id){
		for(TipoMoneda tipoMoneda:values()){
			if(tipoMoneda.getId().equals(id)){
				return tipoMoneda.getDescripcion();
			}
		}
		return null;
	}

}
