package org.dflow.compiler.model.entities;

public class Attribute {
	
	private String type;
	private String name;
	
	public Attribute(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
