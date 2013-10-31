package org.dflow.compiler.parser.ast;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.dflow.compiler.parser.ast.application.Application;

public class DflowApplicationFile extends DflowFile {
	
	private final File file;
	private final Application root;
	
	private final PackageDeclaration $package;
	
	public DflowApplicationFile(File file, PackageDeclaration $package, Application root) {
		this.file = file;
		this.root = root;
		this.$package = $package;
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	public Application getRoot() {
		return root;
	}

	@Override
	public PackageDeclaration getPackage() {
		return $package;
	}
	
	@Override
	public Collection<ImportDeclaration> getImports() {
		return Arrays.asList(new ImportDeclaration[] {});
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { $package, root });
	}
	
	public Application getApplication() {
		return root;
	}

}
