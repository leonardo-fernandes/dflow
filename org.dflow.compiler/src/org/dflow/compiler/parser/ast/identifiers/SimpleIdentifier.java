package org.dflow.compiler.parser.ast.identifiers;

public class SimpleIdentifier extends CompoundIdentifier {
	
	public SimpleIdentifier(String id) {
		super(id, null);
	}
	
	@Override
	public String getQualifier() {
		return null;
	}
	
	@Override
	public String getLastPart() {
		return getFirstPart();
	}
	
	@Override
	public String toString() {
		return getFirstPart();
	}
}
