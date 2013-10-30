package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.identifiers.Identifier;


public class SimpleTypeReference extends TypeReference {
	
	private final Identifier id;
	
	public SimpleTypeReference(Identifier id) {
		this.id = id;
	}
	
	public Identifier getId() {
		return id;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

}
