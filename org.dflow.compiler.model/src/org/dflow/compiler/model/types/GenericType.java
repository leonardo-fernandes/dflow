package org.dflow.compiler.model.types;


public class GenericType extends Type {
	
	private final Type type;
	private final Iterable<Type> arguments;
	
	public GenericType(Type type, Iterable<Type> arguments) {
		this.type = type;
		this.arguments = arguments;
	}
	
	@Override
	public String getFullName() {
		return type.getFullName() + "<" + getArgumentNames() + ">";
	}

	private String getArgumentNames() {
		StringBuilder sb = new StringBuilder();
		for (Type arg : arguments) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(arg.getFullName());
		}
		return sb.toString();
	}

}
