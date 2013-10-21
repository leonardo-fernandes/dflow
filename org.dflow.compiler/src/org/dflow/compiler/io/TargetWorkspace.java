package org.dflow.compiler.io;

import java.io.IOException;

import org.dflow.compiler.io.writing.Writer;

public abstract class TargetWorkspace {
	
	public static final String JAVA_EXTENSION = ".java";

	public abstract Writer openSource(String path) throws IOException;
	public abstract Writer openConfiguration(String path) throws IOException;
	public abstract Writer openWebContent(String path) throws IOException;
	
}
