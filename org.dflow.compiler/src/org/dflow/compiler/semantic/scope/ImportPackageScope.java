package org.dflow.compiler.semantic.scope;

import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.TypeResolver;

public class ImportPackageScope extends Scope {
	
	private final String $package;
	
	public ImportPackageScope(TypeResolver resolver, String $package) {
		super(resolver);
		this.$package = $package;
	}
	
	@Override
	public Type lookup(String name) {
		return resolver.lookup($package + "." + name);
	}

}
