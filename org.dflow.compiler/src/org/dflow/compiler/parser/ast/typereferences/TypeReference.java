package org.dflow.compiler.parser.ast.typereferences;

import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.semantic.Scope;

public abstract class TypeReference extends Node {
	
	public abstract Type resolveType(Scope currentScope);
	
}
