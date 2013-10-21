package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Type implements Writeable {
	
	public static final Type VOID = new Type("void");
	public static final Type INT = new Type("int");
	public static final Type STRING = new Type(java.lang.String.class);
	
	private final String $package;
	private final String name;
	
	private boolean ommitPackage = false;
	
	public Type(String $package, String name) {
		this.$package = $package;
		this.name = name;
	}
	
	public Type(String primitiveType) {
		this(null, primitiveType);
	}
	
	public Type(java.lang.Class<?> clazz) {
		this(clazz.getPackage().getName(), clazz.getSimpleName());
	}
	
	public Type(Class clazz) {
		this(clazz.getPackage(), clazz.getName());
	}
	
	public String getPackage() {
		return $package;
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
			getPackage() == null ||
			getPackage().equals("java.lang");
	}
	
	public String getFullName() {
		if (getPackage() == null) {
			return getName();
		} else {
			return getPackage() + "." + getName();
		}
	}
	
	public String getSimplifiedName() {
		if (canOmmitPackage()) {
			return getName();
		} else {
			return getPackage() + "." + getName();
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
