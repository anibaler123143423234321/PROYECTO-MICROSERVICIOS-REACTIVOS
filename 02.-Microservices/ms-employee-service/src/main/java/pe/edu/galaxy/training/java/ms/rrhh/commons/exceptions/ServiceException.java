package pe.edu.galaxy.training.java.ms.rrhh.commons.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 4197562784843371628L;

	public ServiceException() {
		
	}

	public ServiceException(String message) {
		super(message);
		
	}

	public ServiceException(Throwable cause) {
		super(cause);
		
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
