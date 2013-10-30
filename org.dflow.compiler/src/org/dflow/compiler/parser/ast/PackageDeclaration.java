package org.dflow.compiler.parser.ast;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.identifiers.Identifier;

public class PackageDeclaration extends Node {
	
	private final Identifier id;
	
	public PackageDeclaration(Identifier id) {
		this.id = id;
	}
	
	public String getName() {
		return id.toString();
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

}
