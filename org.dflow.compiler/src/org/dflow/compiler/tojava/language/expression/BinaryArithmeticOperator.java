package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public enum BinaryArithmeticOperator implements Writeable {
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVIDE("/");
	
	private final String javaOperator;
	private BinaryArithmeticOperator(String javaOperator) {
		this.javaOperator = javaOperator;
	}
	
	@Override
	public String toString() {
		return javaOperator;
	} 
	
	@Override
	public void write(Writer writer) {
		writer.write(javaOperator);
	}
}
