package org.dflow.compiler.model.types;

public final class PrimitiveType extends Type {
	
	public static final PrimitiveType INT = new PrimitiveType("int");
	public static final PrimitiveType BOOLEAN = new PrimitiveType("boolean");
	public static final PrimitiveType BYTE = new PrimitiveType("byte");
	
	private final String name;
	
	private PrimitiveType(String name) {
		this.name = name;
	}
	
	public static PrimitiveType lookup(String name) {
		switch (name) {
		case "int":     return INT;
		case "boolean": return BOOLEAN;
		case "byte":    return BYTE;
		default: return null;
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getFullName() {
		return name;
	}

}
