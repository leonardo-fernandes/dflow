package org.dflow.compiler.tojava;

import java.io.File;
import java.io.Reader;

import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.io.TargetWorkspace;
import org.dflow.compiler.model.Application;
import org.dflow.compiler.parser.ApplicationParser;
import org.dflow.compiler.tojava.exceptions.ApplicationNotFoundException;

class ApplicationCompiler {
	
	private static final String APPLICATION_FILE = "Application" + SourceWorkspace.DFLOW_EXTENSION;
	
	private final SourceWorkspace source;
	private final TargetWorkspace target;
	
	public ApplicationCompiler(SourceWorkspace source, TargetWorkspace target) {
		this.source = source;
		this.target = target;
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
	
	public Application compile() throws Exception {
		DiscoverApplicationFileVisitor visitor = new DiscoverApplicationFileVisitor(source);
		visitor.visit();
		if (visitor.applicationFile == null) {
			throw new ApplicationNotFoundException("Could not find the required " + APPLICATION_FILE + " file");
		}

		String $package = source.getRelativePath(visitor.applicationFile.getParentFile()).replace(File.separatorChar, '.');
		Reader r = source.open(visitor.applicationFile);
		ApplicationParser parser = new ApplicationParser($package, r);
		parser.parse();
		return parser.getApplication();
	}

}