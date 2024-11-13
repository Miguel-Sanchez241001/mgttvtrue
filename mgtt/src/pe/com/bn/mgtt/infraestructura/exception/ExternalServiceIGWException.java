package pe.com.bn.mgtt.infraestructura.exception;

public class ExternalServiceIGWException extends ServiceException{
	
	private static final long serialVersionUID = 1L;

	public ExternalServiceIGWException(String msg) {
		super(msg);
	}
	
	public ExternalServiceIGWException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}