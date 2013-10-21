package org.dflow.compiler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.dflow.compiler.io.writing.Writer;

public class FileSystemTargetWorkspace extends TargetWorkspace {
	
	private static final String SOURCES_DIRECTORY = "src";
	private static final String WEB_CONTENT_DIRECTORY = "src";
	
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
	
	@Override
	public Writer openConfiguration(String path) throws IOException {
		File file = new File(root, path);
		file.getParentFile().mkdirs();
		return new Writer(new PrintWriter(new FileOutputStream(file)));
	}
	
	@Override
	public Writer openWebContent(String path) throws IOException {
		File file = new File(root, new File(WEB_CONTENT_DIRECTORY, path).getPath());
		file.getParentFile().mkdirs();
		return new Writer(new PrintWriter(new FileOutputStream(file)));
	}

}
