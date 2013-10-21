package org.dflow.compiler.tojava.language.expression;

import org.dflow.compiler.io.writing.Writer;

public class BinaryArithmeticOperation extends AbstractExpression {
	
	private final BinaryArithmeticOperator operator;
	private final AbstractExpression left, right;
	
	private BinaryArithmeticOperation(BinaryArithmeticOperator operator, AbstractExpression left, AbstractExpression right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
	public static BinaryArithmeticOperation add(AbstractExpression left, AbstractExpression right) {
		return new BinaryArithmeticOperation(BinaryArithmeticOperator.ADD, left, right);
	}
	
	public static BinaryArithmeticOperation subtract(AbstractExpression left, AbstractExpression right) {
		return new BinaryArithmeticOperation(BinaryArithmeticOperator.SUBTRACT, left, right);
	}
	
	public static BinaryArithmeticOperation multiply(AbstractExpression left, AbstractExpression right) {
		return new BinaryArithmeticOperation(BinaryArithmeticOperator.MULTIPLY, left, right);
	}
	
	public static BinaryArithmeticOperation divide(AbstractExpression left, AbstractExpression right) {
		return new BinaryArithmeticOperation(BinaryArithmeticOperator.DIVIDE, left, right);
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(left).write(" ").write(operator).write(" ").write(right);
	}

}
