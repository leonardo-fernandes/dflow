package org.dflow.compiler.parser.exceptions;

public class ParseException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private final int lineno;
	
	public ParseException(int lineno) {
		this.lineno = lineno;
	}

	public ParseException(Throwable cause, int lineno) {
		super(cause);
		this.lineno = lineno;
	}
	
	@Override
	public String getMessage() {
		return "Parsing error in line " + lineno;
	}
	
}
