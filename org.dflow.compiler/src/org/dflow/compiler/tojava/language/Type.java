package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.Writer;
import org.dflow.compiler.io.Writeable;

public class Type implements Writeable {
	
	public static final Type VOID = new Type("void");
	public static final Type INT = new Type("int");
	public static final Type STRING = new Type(java.lang.String.class);
	
	private final String namespace;
	private final String name;
	
	private boolean ommitPackage = false;
	
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
	
	public void ommitPackage() {
		this.ommitPackage = true;
	}
	
	private boolean canOmmitPackage() {
		// TODO: simplification of type references by importing types and packages
		return ommitPackage ||
			getNamespace() == null ||
			getNamespace().equals("java.lang");
	}
	
	public String getFullName() {
		if (getNamespace() == null) {
			return getName();
		} else {
			return getNamespace() + "." + getName();
		}
	}
	
	public String getSimplifiedName() {
		if (canOmmitPackage()) {
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
		writer.write(getSimplifiedName() + " ");
	}
}
