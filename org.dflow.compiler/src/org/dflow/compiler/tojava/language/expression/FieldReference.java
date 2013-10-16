package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.Field;

public final class FieldReference extends AbstractFieldReference {

	private final AbstractExpression instance;
	
	public FieldReference(AbstractExpression instance, Field field) {
		super(field);
		this.instance = instance;
	}
	
	public FieldReference(Field field) {
		this(new This(), field);
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(instance).write("." + field.getName());
	}
	
}
