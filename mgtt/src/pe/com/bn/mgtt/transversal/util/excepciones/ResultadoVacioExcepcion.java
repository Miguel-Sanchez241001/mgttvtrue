package pe.com.bn.mgtt.transversal.util.excepciones;

public class ResultadoVacioExcepcion extends Exception{
	public ResultadoVacioExcepcion() {
		
	}

	public ResultadoVacioExcepcion(String message) {
		super(message);
		
	}

	public ResultadoVacioExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
