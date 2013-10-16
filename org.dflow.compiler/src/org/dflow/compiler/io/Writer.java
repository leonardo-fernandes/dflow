package org.dflow.compiler.io;

import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	
	private static final String INDENTATION = "    ";
	
	private final PrintWriter writer;
	private int indentation = 0;
	private boolean inNewline = true;
	
	public Writer(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void indent() {
		indentation++;
	}
	
	public void unindent() {
		if (indentation == 0) {
			throw new IllegalStateException("Cannot unindent");
		}
		indentation--;
	}
	
	private void writeIndentation() {
		for (int i = 0; i < indentation; i++) {
			writer.write(INDENTATION);
		}
	}
	
	public void println(String string) {
		write(string);
		println();
	}
	
	public void println() {
		writer.println();
		inNewline = true;
	}
	
	public Writer write(String string) {
		if (inNewline) {
			writeIndentation();
		}
		writer.write(string);
		inNewline = inNewline && string.isEmpty();
		return this;
	}
	
	public Writer write(Writeable writeable) {
		writeable.write(this);
		return this;
	}
	
	public Writer write(Iterable<? extends Writeable> writeableList) {
		return write(writeableList, "");
	}

	public Writer write(Iterable<? extends Writeable> writeableList, String separator) {
		boolean first = true;
		for (Writeable writeable : writeableList) {
			if (!first) {
				write(separator);
			}
			write(writeable);
			first = false;
		}
		return this;
	}
	
	public void close() throws IOException {
		if (writer.checkError()) {
			throw new IndentingWriterException();
		}
		if (indentation != 0) {
			throw new IllegalStateException("Indentation level was expected to be 0, but it's still " + indentation + " - did you forgot to unindent()?");
		}
		writer.close();
	}

	private static class IndentingWriterException extends IOException {
		private static final long serialVersionUID = 1L;
	}
}
