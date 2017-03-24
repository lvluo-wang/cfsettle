package com.upg.ucars.framework.exception;

public class AdapterRuntimeException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AdapterRuntimeException(String message, RuntimeException cause) {
		super(message, cause);
		
	}
	public AdapterRuntimeException(RuntimeException cause) {
		super(cause);
	}
	@Override
	public RuntimeException getCause() {
		return (RuntimeException)super.getCause();
	}
	
	
	

}
