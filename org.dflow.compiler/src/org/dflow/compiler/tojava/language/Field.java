package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Field implements Writeable, Annotated {
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PRIVATE;
	public static final ScopeModifier DEFAULT_SCOPE = ScopeModifier.INSTANCE;
	
	private Class declaringClass = Class.UNKNOWN;
	
	private final ScopeModifier scope;
	private final VisibilityModifier visibility;
	private final Type type;
	private final String name;
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	public Field(Type type, String name) {
		this(DEFAULT_VISIBILITY, type, name);
	}
	
	public Field(VisibilityModifier visibility, Type type, String name) {
		this(DEFAULT_SCOPE, visibility, type, name);
	}
	
	public Field(ScopeModifier scope, VisibilityModifier visibility, Type type, String name) {
		this.scope = scope;
		this.visibility = visibility;
		this.type = type;
		this.name = name;
	}
	
	public Class getDeclaringClass() {
		return declaringClass;
	}

	void setDeclaringClass(Class declaringClass) {
		this.declaringClass = declaringClass;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Field addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		return this;
	}
	
	@Override
	public String toString() {
		return declaringClass.getFullName() + "." + getName();
	}

	@Override
	public void write(Writer writer) {
		for (Annotation a : annotations) {
			writer.write(a).println();
		}
		
		writer.write(visibility)
			.write(scope)
			.write(getType())
			.write(getName() + ";");
	}

}
