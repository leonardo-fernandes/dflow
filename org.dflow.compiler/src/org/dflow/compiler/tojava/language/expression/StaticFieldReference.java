package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.Field;

public final class StaticFieldReference extends AbstractFieldReference {
	
	public StaticFieldReference(Field field) {
		super(field);
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(field.getDeclaringClass().getFullName() + "." + field.getName());
	}

}
