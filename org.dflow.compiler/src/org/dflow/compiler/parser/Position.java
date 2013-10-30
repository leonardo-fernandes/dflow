package org.dflow.compiler.parser;

public final class Position {
	
	private final int lineno;
	
	public Position(int lineno) {
		this.lineno = lineno;
	}
	
	@Override
	public int hashCode() {
		return lineno;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Position && lineno == ((Position) obj).lineno;
	}

	@Override
	public String toString() {
		return "line " + lineno;
	}
}
