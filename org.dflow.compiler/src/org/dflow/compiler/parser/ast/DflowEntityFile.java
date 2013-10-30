package org.dflow.compiler.parser.ast;

import java.io.File;
import java.util.Arrays;

import org.dflow.compiler.parser.ast.datamodel.Entity;

public class DflowEntityFile extends DflowFile {
	
	private final File file;
	private final PackageDeclaration $package;
	private final ImportDeclaration.Block imports;
	private final Entity entity;
	
	public DflowEntityFile(File file, PackageDeclaration $package, ImportDeclaration.Block imports, Entity entity) {
		this.file = file;
		this.$package = $package;
		this.imports = imports;
		this.entity = entity;
	}
	
	public File getFile() {
		return file;
	}
	
	public PackageDeclaration getPackage() {
		return $package;
	}
	
	public ImportDeclaration.Block getImports() {
		return imports;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { $package, imports, entity });
	}

}
