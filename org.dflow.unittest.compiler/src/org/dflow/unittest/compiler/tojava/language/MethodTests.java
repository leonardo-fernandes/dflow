package org.dflow.unittest.compiler.tojava.language;

import static org.junit.Assert.assertEquals;

import org.dflow.compiler.tojava.language.Annotation;
import org.dflow.compiler.tojava.language.Argument;
import org.dflow.compiler.tojava.language.Method;
import org.dflow.compiler.tojava.language.ScopeModifier;
import org.dflow.compiler.tojava.language.Type;
import org.dflow.compiler.tojava.language.VisibilityModifier;
import org.dflow.compiler.tojava.language.expression.ArgumentReference;
import org.dflow.compiler.tojava.language.expression.BinaryArithmeticOperation;
import org.dflow.compiler.tojava.language.statement.ReturnStatement;
import org.dflow.unittest.compiler.io.AbstractWriterTest;
import org.junit.Test;

public class MethodTests extends AbstractWriterTest {
	
	@Test
	public void write() {
		Method m = new Method(Type.VOID, "myMethod");
		m.addArgument(new Argument(Type.STRING, "arg"));
		writer.write(m);
		assertEquals(expected(), written());
	}

	@Test
	public void modifiers() {
		Method m = new Method(ScopeModifier.STATIC, VisibilityModifier.PRIVATE, Type.VOID, "privateStaticMethod");
		writer.write(m);
		assertEquals(expected(), written());
	}
	
	@Test
	public void annotations() {
		Method m = new Method(Type.VOID, "myMethod");
		m.addArgument(new Argument(Type.STRING, "arg"));
		m.addAnnotation(new Annotation(new Type(Override.class)));
		writer.write(m);
		assertEquals(expected(), written());
	}
	
	@Test
	public void body() {
		Method m = new Method(Type.INT, "add");
		Argument a = new Argument(Type.INT, "a");
		Argument b = new Argument(Type.INT, "b");
		m.addArgument(a);
		m.addArgument(b);
		m.getBody().addStatement(
			new ReturnStatement(BinaryArithmeticOperation.add(new ArgumentReference(a), new ArgumentReference(b)))
		);
		writer.write(m);
		assertEquals(expected(), written());
	}
}
