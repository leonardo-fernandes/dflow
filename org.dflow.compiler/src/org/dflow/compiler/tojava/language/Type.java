package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.io.Writeable;

public class Type implements Writeable {
	
	public static final Type VOID = new Type("void");
	public static final Type INT = new Type("int");
	public static final Type STRING = new Type(java.lang.String.class);
	
	private final String namespace;
	private final String name;
	
	public Type(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}
	
	public Type(String primitiveType) {
		this(null, primitiveType);
	}
	
	public Type(java.lang.Class<?> clazz) {
		this(clazz.getPackage().getName(), clazz.getSimpleName());
	}
	
	public Type(Class clazz) {
		this(clazz.getNamespace(), clazz.getName());
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		if (getNamespace() == null) {
			return getName();
		} else {
			return getNamespace() + "." + getName();
		}
	}
	
	@Override
	public String toString() {
		return getFullName();
	}

	@Override
	public void write(Writer writer) {
		if (getNamespace() == null) {
			writer.write(getName() + " ");
		} else {
			// TODO: simplification of type references by importing types and packages
			writer.write(getNamespace() + "." + getName() + " ");
		}
	}
}
