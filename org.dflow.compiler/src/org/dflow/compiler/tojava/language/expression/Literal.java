package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.Writer;

public class Literal extends AbstractExpression {
	
	private final String javaLiteral;
	
	private Literal(String javaLiteral) {
		this.javaLiteral = javaLiteral;
	}
	
	public static Literal intLiteral(int value) {
		return new Literal(Integer.toString(value));
	}
	
	public static Literal stringLiteral(String value) {
		return new Literal("\"" + value.replace("\"", "\\\"") + "\"");
	}
	
	public static Literal nullLiteral() {
		return new Literal("null");
	}

	@Override
	public void write(Writer writer) {
		writer.write(javaLiteral);
	}

}
