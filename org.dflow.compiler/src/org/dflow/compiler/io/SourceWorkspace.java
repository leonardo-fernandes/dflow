package org.dflow.compiler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

public class SourceWorkspace {
	
	private final File root;
	
	public SourceWorkspace(File root) {
		this.root = root;
	}
	
	public File[] list() {
		return list(root);
	}
	
	public File[] list(File directory) {
		return directory.listFiles();
	}
	
	public Reader open(File file) {
		try {
			return new InputStreamReader(new FileInputStream(file));
		} catch (FileNotFoundException fnf) {
			throw new IllegalArgumentException(fnf);
		}
	}

}
