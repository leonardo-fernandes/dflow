package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;

import org.dflow.compiler.model.types.ArrayType;
import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.semantic.Scope;


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
	
	@Override
	public Type resolveType(Scope currentScope) {
		return new ArrayType(type.resolveType(currentScope));
	}
	
}
