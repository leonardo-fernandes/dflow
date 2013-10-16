package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.io.Writeable;

public enum ScopeModifier implements Writeable {
	INSTANCE(""),
	STATIC("static");
	
	private final String javaModifier;
	private ScopeModifier(String javaModifier) {
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
