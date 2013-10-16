package org.dflow.compiler.tojava.language.statement;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.expression.AbstractExpression;

public final class ExpressionStatement extends AbstractStatement {
	
	private final AbstractExpression expression;
	
	public ExpressionStatement(AbstractExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(expression).write(";");
	}

}
