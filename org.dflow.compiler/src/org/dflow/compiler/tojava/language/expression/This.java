package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;

public class This extends AbstractExpression {

	@Override
	public void write(Writer writer) {
		writer.write("this");
	}
	
}
