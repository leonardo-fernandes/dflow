package org.dflow.compiler.semantic.scope;

import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.semantic.TypeResolver;

public class ImportTypeScope extends Scope {
	
	private final String $package;
	private final String type;
	
	public ImportTypeScope(TypeResolver resolver, String $package, String type) {
		super(resolver);
		this.$package = $package;
		this.type = type;
	}
	
	@Override
	public Type lookup(String name) {
		if (name.equals(type)) {
			return resolver.lookup($package + "." + type);
		} else {
			return null;
		}
	}

}
