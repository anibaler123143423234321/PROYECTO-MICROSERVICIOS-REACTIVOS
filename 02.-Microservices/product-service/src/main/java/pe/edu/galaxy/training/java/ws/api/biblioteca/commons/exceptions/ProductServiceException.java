package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions;

public class ProductServiceException extends Exception {

	private static final long serialVersionUID = 4197562784843371628L;

	public ProductServiceException() {
		
	}

	public ProductServiceException(String message) {
		super(message);
		
	}

	public ProductServiceException(Throwable cause) {
		super(cause);
		
	}

	public ProductServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ProductServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
