package org.dflow.compiler.tojava.language.statement;

import org.dflow.compiler.io.writing.Writer;
import org.dflow.compiler.tojava.language.expression.AbstractExpression;

public final class ReturnStatement extends AbstractStatement {
	
	private final AbstractExpression value;
	
	public ReturnStatement(AbstractExpression value) {
		this.value = value;
	}
	
	@Override
	public void write(Writer writer) {
		writer.write("return ").write(value).write(";");
	}

}
