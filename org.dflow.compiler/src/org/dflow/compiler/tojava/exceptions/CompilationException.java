package org.dflow.compiler.tojava.exceptions;

public class CompilationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CompilationException(String message) {
		super(message);
	}
	
	public CompilationException(String message, Throwable cause) {
		super(message, cause);
	}

}
