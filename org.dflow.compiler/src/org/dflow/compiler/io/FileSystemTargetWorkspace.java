package org.dflow.compiler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileSystemTargetWorkspace extends TargetWorkspace {
	
	private static final String SOURCES_DIRECTORY = "src";
	
	private final File root;
	
	public FileSystemTargetWorkspace(File root) {
		this.root = root;
	}
	
	@Override
	public Writer openSource(String path) throws FileNotFoundException {
		File file = new File(root, new File(SOURCES_DIRECTORY, path).getPath());
		file.getParentFile().mkdirs();
		return new Writer(new PrintWriter(new FileOutputStream(file)));
	}

}
