package org.dflow.compiler.parser.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.parser.ast.identifiers.CompoundIdentifier;
import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.scope.ImportPackageScope;
import org.dflow.compiler.semantic.scope.ImportTypeScope;

public class ImportDeclaration extends Node {

	private final CompoundIdentifier id;
	private final boolean asterisk;
	
	public ImportDeclaration(CompoundIdentifier id, boolean asterisk) {
		this.id = id;
		this.asterisk = asterisk;
	}
	
	public ImportDeclaration(CompoundIdentifier id) {
		this(id, false);
	}
	
	public CompoundIdentifier getId() {
		return id;
	}
	
	public boolean isAsterisk() {
		return asterisk;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

	public Scope getScope(CompilationContext context) {
		if (isAsterisk()) {
			return new ImportPackageScope(context.getTypeResolver(), getId().toString());
		} else {
			return new ImportTypeScope(context.getTypeResolver(), getId().getQualifier(), getId().getLastPart());
		}
	}
	
	public static class Block extends Node {
		
		private final List<ImportDeclaration> imports = new LinkedList<>();
		
		public Block add(ImportDeclaration $import) {
			imports.add($import);
			return this;
		}
		
		public Collection<ImportDeclaration> getImports() {
			return Collections.unmodifiableCollection(imports);
		}
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(imports);
		}
	}
	
}
