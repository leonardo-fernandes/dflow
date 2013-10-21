package org.dflow.compiler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileSystemSourceWorkspace extends SourceWorkspace {
	
	private final File root;
	
	public FileSystemSourceWorkspace(File root) {
		this.root = root;
	}
	
	@Override
	public File[] list() {
		return root.listFiles();
	}
	
	@Override
	public File[] list(File directory) {
		return directory.listFiles();
	}
	
	@Override
	public Reader open(File file) throws FileNotFoundException {
		return new InputStreamReader(new FileInputStream(file));
	}

	@Override
	public String getRelativePath(File file) {
		return root.toURI().relativize(file.toURI()).getPath();
	}
	
	@Override
	public File getAbsoluteFile(String path) {
		return new File(root, path);
	}
}
