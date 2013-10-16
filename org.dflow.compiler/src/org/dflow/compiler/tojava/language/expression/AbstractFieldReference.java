package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.tojava.language.Field;

public abstract class AbstractFieldReference extends AbstractExpression implements LValue {
	
	protected final Field field;
	
	protected AbstractFieldReference(Field field) {
		this.field = field;
	}
	
}
