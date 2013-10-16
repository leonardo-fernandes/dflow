package org.dflow.unittest.compiler.tojava.language;

import static org.junit.Assert.assertEquals;

import org.dflow.compiler.tojava.language.Annotation;
import org.dflow.compiler.tojava.language.Field;
import org.dflow.compiler.tojava.language.ScopeModifier;
import org.dflow.compiler.tojava.language.Type;
import org.dflow.compiler.tojava.language.VisibilityModifier;
import org.dflow.unittest.compiler.io.AbstractWriterTest;
import org.junit.Test;

public class FieldTests extends AbstractWriterTest {
	
	@Test
	public void write() {
		Field f = new Field(Type.STRING, "myField");
		writer.write(f);
		assertEquals(expected(), written());
	}
	
	@Test
	public void modifiers() {
		Field f = new Field(ScopeModifier.STATIC, VisibilityModifier.PROTECTED, Type.INT, "protectedStaticField");
		writer.write(f);
		assertEquals(expected(), written());
	}
	
	@Test
	public void annotations() {
		Field f = new Field(Type.INT, "myField");
		f.addAnnotation(new Annotation(new Type(Deprecated.class)));
		writer.write(f);
		assertEquals(expected(), written());
	}
}
