package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;

public class Assignment extends AbstractExpression {
	
	private final LValue lvalue;
	private final AbstractExpression rvalue;
	
	public Assignment(LValue lvalue, AbstractExpression rvalue) {
		this.lvalue = lvalue;
		this.rvalue = rvalue;
	}

	@Override
	public void write(Writer writer) {
		writer.write(lvalue).write(" = ").write(rvalue);
	}

	
}
