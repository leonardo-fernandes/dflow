package org.dflow.compiler.model.types;

public interface TypeProvider<T extends Type> {
	
	boolean isNested();
	TypeProvider<?> getParent();
	
	String getPackage();
	String getName();
	String getFullName();

	T getType();

}
