package org.dflow.compiler.model.datamodel;

import org.dflow.compiler.model.types.Type;

public class Attribute {
	
	private Type type;
	private String name;
	
	public Attribute(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
