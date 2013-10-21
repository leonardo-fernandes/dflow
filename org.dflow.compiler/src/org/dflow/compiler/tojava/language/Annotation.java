package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Annotation implements Writeable {
	
	private final Type type;
	
	public Annotation(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "@" + type.getFullName();
	}
	
	@Override
	public void write(Writer writer) {
		writer.write("@").write(type.getSimplifiedName());
	}
	
}
