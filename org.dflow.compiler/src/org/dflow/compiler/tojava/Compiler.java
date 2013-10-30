package org.dflow.compiler.tojava;

import java.io.File;

import org.dflow.compiler.io.FileSystemSourceWorkspace;
import org.dflow.compiler.io.FileSystemTargetWorkspace;
import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.io.TargetWorkspace;
import org.dflow.compiler.semantic.CompilationContext;

public class Compiler {

	private final SourceWorkspace source;
	private final TargetWorkspace target;
	
	public Compiler(SourceWorkspace source, TargetWorkspace target) {
		this.source = source;
		this.target = target;
	}
	
	public void compile() throws Exception {
		CompilationContext context = new CompilationContext(source);

		ApplicationCompiler applicationCompiler = new ApplicationCompiler(source, target, context);
		context.setApplication(applicationCompiler.compile());

		DataModelCompiler datamodel = new DataModelCompiler(source, target, context);
		datamodel.compile();
	}
	
	public static void main(String[] args) throws Exception {
		SourceWorkspace source = new FileSystemSourceWorkspace(new File(args[0]));
		TargetWorkspace target = new FileSystemTargetWorkspace(new File(args[1]));
		
		new Compiler(source, target).compile();
	}
}
