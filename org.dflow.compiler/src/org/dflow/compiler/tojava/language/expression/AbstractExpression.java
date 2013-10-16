package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writeable;
import org.dflow.compiler.tojava.language.statement.ExpressionStatement;

public abstract class AbstractExpression implements Writeable {
	
	public final ExpressionStatement toStatement() {
		return new ExpressionStatement(this);
	}

}
