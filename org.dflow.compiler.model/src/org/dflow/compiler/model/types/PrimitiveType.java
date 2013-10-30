package org.dflow.compiler.model.types;

public final class PrimitiveType extends Type {
	
	public static final PrimitiveType INT = new PrimitiveType("int");
	public static final PrimitiveType BOOLEAN = new PrimitiveType("boolean");
	
	private final String name;
	
	private PrimitiveType(String name) {
		this.name = name;
	}
	
	public static PrimitiveType lookup(String name) {
		switch (name) {
		case "int": return INT;
		case "boolean": return BOOLEAN;
		default: return null;
		}
	}
	
	@Override
	public String getFullName() {
		return name;
	}

}
