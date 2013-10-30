package org.dflow.compiler.model.types;

public class ExternalType extends Type {
	
	private final Class<?> clazz;

	public ExternalType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public String getFullName() {
		return clazz.getCanonicalName();
	}
	
}
