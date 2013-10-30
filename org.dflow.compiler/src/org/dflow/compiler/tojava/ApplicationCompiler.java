package org.dflow.compiler.tojava;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.io.TargetWorkspace;
import org.dflow.compiler.io.templating.Template;
import org.dflow.compiler.io.writing.Writer;
import org.dflow.compiler.model.Application;
import org.dflow.compiler.parser.ApplicationParser;
import org.dflow.compiler.parser.DflowLexer;
import org.dflow.compiler.parser.ast.DflowApplicationFile;
import org.dflow.compiler.semantic.CompilationContext;

class ApplicationCompiler {
	
	private final SourceWorkspace source;
	private final TargetWorkspace target;
	private final CompilationContext context;
	
	public ApplicationCompiler(SourceWorkspace source, TargetWorkspace target, CompilationContext context) {
		this.source = source;
		this.target = target;
		this.context = context;
	}
	
	public Application compile() throws IOException {
		File f = context.getApplicationFile();
		Reader r = source.open(f);
		ApplicationParser parser = new ApplicationParser(new DflowLexer(r, f), f);
		parser.parse();
		context.addParsedFile(parser.getParsedFile());
		
		Application application = createApplication(parser.getParsedFile());
		compileEclipseProject(application);
		return application;
	}

	private Application createApplication(DflowApplicationFile f) {
		return new Application(f.getPackage().getName(), f.getApplication().getName());
	}

	private void compileEclipseProject(Application application) throws IOException {
		Template eclipseClasspath = new Template(new InputStreamReader(this.getClass().getResourceAsStream("templates/eclipse.classpath")));
		Writer classpathWriter = target.openConfiguration(".classpath");
		try {
			classpathWriter.write(eclipseClasspath);
		} finally {
			classpathWriter.close();
		}
		
		Template eclipseProject = new Template(new InputStreamReader(this.getClass().getResourceAsStream("templates/eclipse.project")));
		eclipseProject.replace("application.name", application.getName());
		Writer projectWriter = target.openConfiguration(".project");
		try {
			projectWriter.write(eclipseProject);
		} finally {
			projectWriter.close();
		}
	}

}
