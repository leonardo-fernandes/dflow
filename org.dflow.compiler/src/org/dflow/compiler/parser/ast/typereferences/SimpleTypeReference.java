package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.identifiers.CompoundIdentifier;
import org.dflow.compiler.parser.ast.identifiers.Identifier;


public class SimpleTypeReference extends TypeReference {
	
	private final CompoundIdentifier id;
	
	public SimpleTypeReference(CompoundIdentifier id) {
		this.id = id;
	}
	
	public CompoundIdentifier getId() {
		return id;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

}
