package org.dflow.compiler.parser;

import java.io.File;

public final class Position {
	
	private final File file;
	private final int lineno;
	private final int colno;
	
	public Position(File file, int lineno, int colno) {
		this.file = file;
		this.lineno = lineno;
		this.colno = colno;
	}
	
	@Override
	public int hashCode() {
		return lineno ^ colno;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Position &&
				equals((Position) obj);
	}
	
	private boolean equals(Position obj) {
		return file.equals(obj.file) &&
				lineno == obj.lineno &&
				colno == obj.colno;
	}

	@Override
	public String toString() {
		return file.getPath() + ":" + lineno;
	}
}
