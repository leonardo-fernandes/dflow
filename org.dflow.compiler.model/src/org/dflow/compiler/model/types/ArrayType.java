package org.dflow.compiler.model.types;

public class ArrayType extends Type {
	
	private final Type type;
	
	public ArrayType(Type type) {
		this.type = type;
	}
	
	@Override
	public String getName() {
		return type.getName() + "[]";
	}
	
	@Override
	public String getFullName() {
		return type.getFullName() + "[]";
	}

}
