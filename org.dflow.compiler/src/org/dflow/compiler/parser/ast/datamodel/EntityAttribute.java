package org.dflow.compiler.parser.ast.datamodel;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.typereferences.TypeReference;

public class EntityAttribute extends Node {
	
	private final TypeReference type;
	private final String name;
	
	public EntityAttribute(TypeReference type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public TypeReference getType() {
		return type;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { type });
	}

}
