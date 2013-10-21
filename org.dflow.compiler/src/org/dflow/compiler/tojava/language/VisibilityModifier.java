package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public enum VisibilityModifier implements Writeable {
	PACKAGE(""),
	PUBLIC("public"),
	PRIVATE("private"),
	PROTECTED("protected");
	
	private final String javaModifier;
	private VisibilityModifier(String javaModifier) {
		this.javaModifier = javaModifier;
	}
	
	@Override
	public String toString() {
		return javaModifier;
	}
	
	public void write(Writer writer) {
		if (!javaModifier.isEmpty()) {
			writer.write(javaModifier + " ");
		}
	}
}
