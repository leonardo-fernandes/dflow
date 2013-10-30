package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.Node;


public class ArrayTypeReference extends TypeReference {
	
	private final TypeReference type;
	
	public ArrayTypeReference(TypeReference type) {
		this.type = type;
	}
	
	public TypeReference getType() {
		return type;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { type });
	}
	
}
