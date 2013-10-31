package org.dflow.compiler.parser.ast.identifiers;

public class CompoundIdentifier extends Identifier {

	private final String part;
	private final Identifier next;
	
	public CompoundIdentifier(String part, Identifier next) {
		this.part = part;
		this.next = next;
	}
	
	public String getPart() {
		return part;
	}
	
	public Identifier getNext() {
		return next;
	}
	
	@Override
	public String toString() {
		return getPart() + "." + getNext().toString();
	}
	
}
