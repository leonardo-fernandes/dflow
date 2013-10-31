package org.dflow.compiler.parser.ast.identifiers;

public class SimpleIdentifier extends CompoundIdentifier {
	
	public SimpleIdentifier(String id) {
		super(id, null);
	}
	
	@Override
	public String toString() {
		return getPart();
	}
}
