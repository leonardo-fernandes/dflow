package org.dflow.compiler.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.parser.ast.identifiers.Identifier;

public class ImportDeclaration extends Node {

	private final Identifier id;
	private final boolean asterisk;
	
	public ImportDeclaration(Identifier id, boolean asterisk) {
		this.id = id;
		this.asterisk = asterisk;
	}
	
	public ImportDeclaration(Identifier id) {
		this(id, false);
	}
	
	public Identifier getId() {
		return id;
	}
	
	public boolean isAsterisk() {
		return asterisk;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { id });
	}

	public static class Block extends Node {
		
		private final List<ImportDeclaration> imports = new LinkedList<>();
		
		public Block add(ImportDeclaration $import) {
			imports.add($import);
			return this;
		}
		
		public Iterable<ImportDeclaration> getImports() {
			return Collections.unmodifiableCollection(imports);
		}
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(imports);
		}
	}
	
}
