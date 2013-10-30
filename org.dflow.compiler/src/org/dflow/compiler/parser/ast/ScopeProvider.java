package org.dflow.compiler.parser.ast;

import org.dflow.compiler.semantic.scope.Scope;

public interface ScopeProvider {
	
	Scope getScope();

}
