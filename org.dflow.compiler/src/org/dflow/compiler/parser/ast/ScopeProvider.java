package org.dflow.compiler.parser.ast;

import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.semantic.Scope;

public interface ScopeProvider {
	
	Scope getScope(CompilationContext context);

}
