package org.dflow.compiler.semantic;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.model.Application;
import org.dflow.compiler.parser.ast.DflowFile;
import org.dflow.compiler.tojava.exceptions.ApplicationNotFoundException;
import org.dflow.compiler.tojava.exceptions.CompilationException;

public class CompilationContext {
	
	private static final String APPLICATION_FILE = "application" + SourceWorkspace.DFLOW_EXTENSION;

	private final File applicationFile;
	private Application application;
	private final List<DflowFile> parsedFiles = new LinkedList<>();
	
	private final TypeResolver resolver = new TypeResolver();
	
	public CompilationContext(SourceWorkspace source) throws IOException, CompilationException {
		DiscoverApplicationFileVisitor visitor = new DiscoverApplicationFileVisitor(source);
		visitor.visit();
		if (visitor.applicationFile == null) {
			throw new ApplicationNotFoundException("Could not find the required " + APPLICATION_FILE + " file");
		}
		
		applicationFile = visitor.applicationFile;
	}
	
	public File getApplicationFile() {
		return applicationFile;
	}
	
	public Application getApplication() {
		return application;
	}
	
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public File getSubpackage(String subpackageName) {
		return new File(applicationFile.getParentFile(), subpackageName);
	}

	public void addParsedFile(DflowFile file) {
		parsedFiles.add(file);
	}
	
	public Iterable<DflowFile> getParsedFiles() {
		return Collections.unmodifiableCollection(parsedFiles);
	}
	
	public TypeResolver getTypeResolver() {
		return resolver;
	}
	

	private static final class DiscoverApplicationFileVisitor extends SourceWorkspace.Visitor {

		private File applicationFile;
		
		public DiscoverApplicationFileVisitor(SourceWorkspace source) {
			super(source);
		}
		
		@Override
		protected Action visit(File file) {
			if (file.getName().equals(APPLICATION_FILE)) {
				this.applicationFile = file;
				return Action.STOP;
			} else {
				return Action.CONTINUE;
			}
		}
	}

}
