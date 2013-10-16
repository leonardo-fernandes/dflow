package org.dflow.unittest.compiler.tojava.language;

import static org.junit.Assert.assertEquals;

import org.dflow.compiler.tojava.language.Annotation;
import org.dflow.compiler.tojava.language.Argument;
import org.dflow.compiler.tojava.language.Class;
import org.dflow.compiler.tojava.language.Constructor;
import org.dflow.compiler.tojava.language.Field;
import org.dflow.compiler.tojava.language.Method;
import org.dflow.compiler.tojava.language.Type;
import org.dflow.compiler.tojava.language.VisibilityModifier;
import org.dflow.compiler.tojava.language.expression.ArgumentReference;
import org.dflow.compiler.tojava.language.expression.Assignment;
import org.dflow.compiler.tojava.language.expression.FieldReference;
import org.dflow.compiler.tojava.language.statement.ReturnStatement;
import org.dflow.unittest.compiler.io.AbstractWriterTest;
import org.junit.Test;

public class ClassTests extends AbstractWriterTest {
	
	@Test
	public void write() {
		Class c = new Class("org.dflow.unittest", "MyClass");
		writer.write(c);
		assertEquals(expected(), written());
	}
	
	@Test
	public void modifiers() {
		Class c = new Class(VisibilityModifier.PACKAGE, "org.dflow.unittest", "MyPackageClass");
		writer.write(c);
		assertEquals(expected(), written());
	}
	
	@Test
	public void annotations() {
		Class c = new Class("org.dflow.unittest", "MyDeprecatedTest");
		c.addAnnotation(new Annotation(new Type(Deprecated.class)));
		writer.write(c);
		assertEquals(expected(), written());
	}

	@Test
	public void person() {
		Class c = new Class("org.dflow.unittest", "Person");
		
		Field nameField = new Field(Type.STRING, "name");
		c.addField(nameField);
		Field ageField = new Field(Type.INT, "age");
		c.addField(ageField);
		
		Constructor ctor = new Constructor();
		c.addConstructor(ctor);
		Argument nameArg = new Argument(Type.STRING, "name");
		Argument ageArg = new Argument(Type.INT, "age");
		ctor.addArgument(nameArg);
		ctor.addArgument(ageArg);
		ctor.getBody().addStatement(
			new Assignment(new FieldReference(nameField), new ArgumentReference(nameArg)).toStatement()
		);
		ctor.getBody().addStatement(
			new Assignment(new FieldReference(ageField), new ArgumentReference(ageArg)).toStatement()
		);
		
		c.addMethod(Method.getter(nameField)).addMethod(Method.setter(nameField));
		c.addMethod(Method.getter(ageField)).addMethod(Method.setter(ageField));
		
		Method toStringMethod = new Method(Type.STRING, "toString");
		c.addMethod(toStringMethod);
		toStringMethod.addAnnotation(new Annotation(new Type(Override.class)));
		toStringMethod.getBody().addStatement(
			new ReturnStatement(new FieldReference(nameField))
		);
		
		writer.write(c);
		assertEquals(expected(), written());
	}
}
