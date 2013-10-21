package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Argument implements Writeable, Annotated {
	
	private final Type type;
	private final String name;
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	public Argument(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Argument addAnnotation(Annotation annotation) {
		annotations.add(annotation);
		return this;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public void write(Writer writer) {
		for (Annotation a : annotations) {
			writer.write(a).write(" ");
		}

		writer.write(getType()).write(getName());
	}
	
}
