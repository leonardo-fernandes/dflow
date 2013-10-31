package org.dflow.compiler.parser.ast;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.identifiers.CompoundIdentifier;
import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.scope.ImportPackageScope;

public class PackageDeclaration extends Node {
	
	private final CompoundIdentifier id;
	
	public PackageDeclaration(CompoundIdentifier id) {
		this.id = id;
	}
	
	public String getName() {
		return id.toString();
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

	public Scope getScope(CompilationContext context) {
		return new ImportPackageScope(context.getTypeResolver(), getName());
	}
}
