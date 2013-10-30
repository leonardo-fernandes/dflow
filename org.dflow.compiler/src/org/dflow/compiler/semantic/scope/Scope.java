package org.dflow.compiler.semantic.scope;

import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.semantic.TypeResolver;

public abstract class Scope {
	
	protected TypeResolver resolver;
	
	public Scope(TypeResolver resolver) {
		this.resolver = resolver;
	}

	public abstract Type lookup(String name);

}
