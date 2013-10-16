package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.Argument;

public class ArgumentReference extends AbstractExpression implements LValue {
	
	private final Argument argument;
	
	public ArgumentReference(Argument argument) {
		this.argument = argument;
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(argument.getName());
	}

}
