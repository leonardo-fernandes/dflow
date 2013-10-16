package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.Writeable;
import org.dflow.compiler.io.Writer;
import org.dflow.compiler.tojava.language.expression.ArgumentReference;
import org.dflow.compiler.tojava.language.expression.Assignment;
import org.dflow.compiler.tojava.language.expression.FieldReference;
import org.dflow.compiler.tojava.language.statement.ReturnStatement;

public class Method implements Writeable, Annotated {
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PUBLIC;
	public static final ScopeModifier DEFAULT_SCOPE = ScopeModifier.INSTANCE;
	
	private Class declaringClass = Class.UNKNOWN;
	
	private final ScopeModifier scope;
	private final VisibilityModifier visibility;
	private final Type returnType;
	private final String name;

	private final List<Argument> arguments = new LinkedList<>();
	private final MethodBody body = new MethodBody();
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	public Method(ScopeModifier scope, VisibilityModifier visibility, Type returnType, String name) {
		this.scope = scope;
		this.visibility = visibility;
		this.returnType = returnType;
		this.name = name;
	}
	
	public Method(VisibilityModifier visibility, Type returnType, String name) {
		this(DEFAULT_SCOPE, visibility, returnType, name);
	}
	
	public Method(Type returnType, String name) {
		this(DEFAULT_VISIBILITY, returnType, name);
	}
	
	public Class getDeclaringClass() {
		return declaringClass;
	}
	
	void setDeclaringClass(Class declaringClass) {
		this.declaringClass = declaringClass;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public String getName() {
		return name;
	}
	
	public MethodBody getBody() {
		return body;
	}
	
	public Method addArgument(Argument argument) {
		this.arguments.add(argument);
		return this;
	}
	
	@Override
	public Method addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		return this;
	}
	
	public static Method getter(Field field) {
		Method get = new Method(field.getType(), getterName(field));
		get.getBody().addStatement(
			new ReturnStatement(new FieldReference(field))
		);
		return get;
	}
	
	public static Method setter(Field field) {
		Method set = new Method(Type.VOID, setterName(field));
		Argument arg = new Argument(field.getType(), field.getName());
		set.addArgument(arg);
		set.getBody().addStatement(
			new Assignment(new FieldReference(field), new ArgumentReference(arg)).toStatement()
		);
		return set;
	}
	
	private static String getterName(Field field) {
		return "get" + field.getName().substring(0, 1).toUpperCase() +
				field.getName().substring(1);
	}
	
	private static String setterName(Field field) {
		return "set" + field.getName().substring(0, 1).toUpperCase() +
				field.getName().substring(1);
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
		return declaringClass.getFullName() + "." + getName() + "(" + sb.toString() + ")";
	}
	
	@Override
	public void write(Writer writer) {
		for (Annotation a : annotations) {
			writer.write(a).println();
		}
		
		writer.write(visibility)
			.write(scope)
			.write(getReturnType())
			.write(getName() + "(")
			.write(arguments, ", ")
			.write(") {")
			.println();
		writer.indent();
		
		getBody().write(writer);
		
		writer.unindent();
		writer.println("}");
		
	}

}
