package org.dflow.compiler.io;

import java.io.IOException;

public abstract class TargetWorkspace {
	
	public static final String JAVA_EXTENSION = ".java";

	public abstract Writer openSource(String path) throws IOException;
	
}
