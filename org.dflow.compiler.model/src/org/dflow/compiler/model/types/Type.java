package org.dflow.compiler.model.types;

public abstract class Type {
	
	public static final Type UNKNOWN = new Type() {
		@Override
		public String getFullName() {
			return "UNKNOWN";
		}
	};
	
	public abstract String getFullName();
	
	@Override
	public String toString() {
		return getFullName();
	}

}
