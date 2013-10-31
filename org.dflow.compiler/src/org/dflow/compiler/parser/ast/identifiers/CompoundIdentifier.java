package org.dflow.compiler.parser.ast.identifiers;

public class CompoundIdentifier extends Identifier {

	private final String part;
	private final CompoundIdentifier next;
	
	public CompoundIdentifier(String part, CompoundIdentifier next) {
		this.part = part;
		this.next = next;
	}
	
	public String getFirstPart() {
		return part;
	}
	
	public String getQualifier() {
		String nextQualifier = getNext().getQualifier();
		if (nextQualifier == null) {
			return getFirstPart();
		} else {
			return getFirstPart() + "." + nextQualifier;
		}
	}
	
	public String getLastPart() {
		return next.getLastPart();
	}
	
	public CompoundIdentifier getNext() {
		return next;
	}
	
	@Override
	public String toString() {
		return getFirstPart() + "." + getNext().toString();
	}
	
}
