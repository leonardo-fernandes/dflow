package org.dflow.compiler.model.types;

public abstract class Type {
	
	public static final Type UNKNOWN = new Type() {
		@Override
		public String getName() {
			return "UNKNOWN";
		}
		
		@Override
		public String getFullName() {
			return getName();
		}
	};
	
	public abstract String getName();
	public abstract String getFullName();
	
	@Override
	public String toString() {
		return getFullName();
	}

}
