package org.dflow.compiler.parser.ast;

import java.io.File;
import java.util.Collection;

import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.ScopeStack;
import org.dflow.compiler.semantic.scope.ImportPackageScope;


public abstract class DflowFile extends Node implements ScopeProvider {
	
	public abstract File getFile();
	public abstract PackageDeclaration getPackage();
	public abstract Collection<ImportDeclaration> getImports();
	
	@Override
	public Scope getScope(CompilationContext context) {
		ScopeStack stack = new ScopeStack(context.getTypeResolver());
		stack.push(new ImportPackageScope(context.getTypeResolver(), "java.lang"));
		stack.push(getPackage().getScope(context));
		for (ImportDeclaration $import : getImports()) {
			stack.push($import.getScope(context));
		}
		return stack;
	}

}
