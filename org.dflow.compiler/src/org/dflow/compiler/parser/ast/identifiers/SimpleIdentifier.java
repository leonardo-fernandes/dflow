package org.dflow.compiler.parser.ast.identifiers;

public class SimpleIdentifier extends Identifier {
	
	private final String id;
	
	public SimpleIdentifier(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getId();
	}
}
