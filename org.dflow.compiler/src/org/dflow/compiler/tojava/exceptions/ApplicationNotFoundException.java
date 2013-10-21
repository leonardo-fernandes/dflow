package org.dflow.compiler.tojava.exceptions;

public class ApplicationNotFoundException extends CompilationException {

	private static final long serialVersionUID = 1L;
	
	public ApplicationNotFoundException(String message) {
		super(message);
	}
	
	public ApplicationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
