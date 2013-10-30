package org.dflow.compiler.parser.ast;

import java.io.File;


public abstract class DflowFile extends Node {
	
	public abstract File getFile();
	public abstract PackageDeclaration getPackage();

}
