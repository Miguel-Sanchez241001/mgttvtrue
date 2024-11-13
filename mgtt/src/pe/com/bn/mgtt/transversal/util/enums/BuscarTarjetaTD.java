package pe.com.bn.mgtt.transversal.util.enums;

import java.util.ArrayList;
 
import java.util.List;

public enum BuscarTarjetaTD {	 	
	 	NUM_TARJETA("N", "NÚMERO DE TARJETA", "2", 16), 
	 	DNI("1", "DNI", "1", 8), 
	 	CARNET_EXTRANJERIA("4", "CARNET DE EXTRANJERÍA", "1", 12)   ;

	 	private String id;
		private String descripcion;
		private String codigo;
		private int length;
		
	 	
	

	private BuscarTarjetaTD(String id, String descripcion,String codigo, int length) {
		this.id = id;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.length = length;
		
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}	
	
	
	public static List<BuscarTarjetaTD> buscarTarjetaTDTarjeta() {
		List<BuscarTarjetaTD> listaBuscarTarjetaTD = new ArrayList<BuscarTarjetaTD>();
		listaBuscarTarjetaTD.add(BuscarTarjetaTD.NUM_TARJETA);
 		return listaBuscarTarjetaTD;
	}
	
	public static List<BuscarTarjetaTD> buscarTarjetaTDTipoDoc() {
		List<BuscarTarjetaTD> listaBuscarTarjetaTD = new ArrayList<BuscarTarjetaTD>();
 		listaBuscarTarjetaTD.add(BuscarTarjetaTD.DNI);
		listaBuscarTarjetaTD.add(BuscarTarjetaTD.CARNET_EXTRANJERIA);
 		return listaBuscarTarjetaTD;
	}
	
	
	
	
	
	

}
