package org.dflow.compiler.semantic;

import org.dflow.compiler.model.types.Type;

public abstract class Scope {
	
	protected final TypeResolver resolver;
	
	public Scope(TypeResolver resolver) {
		this.resolver = resolver;
	}

	public abstract Type lookup(String name);

}
