package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.Writeable;
import org.dflow.compiler.io.Writer;

public class Constructor implements Writeable, Annotated {
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PUBLIC;
	
	private Class declaringClass = Class.UNKNOWN;
	
	private final VisibilityModifier visibility;
	
	private final List<Argument> arguments = new LinkedList<>();
	private final ConstructorBody body = new ConstructorBody();

	private final List<Annotation> annotations = new LinkedList<>();
	
	public Constructor(VisibilityModifier visibility) {
		this.visibility = visibility;
	}
	
	public Constructor() {
		this(VisibilityModifier.PUBLIC);
	}
	
	public Class getDeclaringClass() {
		return declaringClass;
	}
	
	void setDeclaringClass(Class declaringClass) {
		this.declaringClass = declaringClass;
	}
	
	public ConstructorBody getBody() {
		return body;
	}

	public Constructor addArgument(Argument argument) {
		this.arguments.add(argument);
		return this;
	}
	
	@Override
	public Constructor addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Argument a : arguments) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(a.getType());
		}
		return declaringClass.getFullName() + ".ctor(" + sb.toString() + ")";
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(visibility)
			.write(declaringClass.getName() + "(")
			.write(arguments, ", ")
			.write(") {")
			.println();
		writer.indent();
		
		getBody().write(writer);
		
		writer.unindent();
		writer.println("}");
	}

}
