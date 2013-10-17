package org.dflow.compiler.model.entities;

import java.util.LinkedList;
import java.util.List;

public class Entity {
	
	private String name;
	private final List<Attribute> attributes = new LinkedList<>();
	
	public Entity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public Iterable<Attribute> getAttributes() {
		return attributes;
	}
}
