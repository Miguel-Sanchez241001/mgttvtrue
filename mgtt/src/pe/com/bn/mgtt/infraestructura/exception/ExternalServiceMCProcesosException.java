package pe.com.bn.mgtt.infraestructura.exception;

public class ExternalServiceMCProcesosException extends ServiceException{
	
	private static final long serialVersionUID = 1L;

	public ExternalServiceMCProcesosException(String msg) {
		super(msg);
	}
	
	public ExternalServiceMCProcesosException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}