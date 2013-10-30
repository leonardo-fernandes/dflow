package org.dflow.compiler.io;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public abstract class SourceWorkspace {
	
	public static final String DFLOW_EXTENSION = ".dflow";
	
	public abstract File[] list();
	public abstract File[] list(File directory);
	
	public abstract Reader open(File file) throws IOException;

	
	public static abstract class Visitor {
		
		private final SourceWorkspace source;
		private final File parent;
		
		public Visitor(SourceWorkspace source) {
			this(source, null);
		}
		
		public Visitor(SourceWorkspace source, File parent) {
			this.source = source;
			this.parent = parent;
		}
		
		public enum Action {
			CONTINUE,
			STOP
		}
		
		public final void visit() throws IOException {
			if (parent == null) {
				visitRoot();
			} else {
				visitChilds(parent);
			}
		}
		
		private void visitRoot() throws IOException {
			for (File f : source.list()) {
				if (!f.isDirectory()) {
					if (visit(f) == Action.STOP) {
						return;
					}
				}
			}

			for (File f : source.list()) {
				if (f.isDirectory()) {
					if (visitChilds(f) == Action.STOP) {
						return;
					}
				}
			}
		}

		private Action visitChilds(File file) throws IOException {
			for (File f : source.list(file)) {
				if (!f.isDirectory()) {
					if (visit(f) == Action.STOP) {
						return Action.STOP;
					}
				}
			}

			for (File f : source.list(file)) {
				if (f.isDirectory()) {
					if (visitChilds(f) == Action.STOP) {
						return Action.STOP;
					}
				}
			}
			
			return Action.CONTINUE;
		}

		protected abstract Action visit(File file) throws IOException;
	}

}