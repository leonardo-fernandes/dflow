package org.dflow.compiler.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class TargetWorkspace {
	
	private final File root;
	
	public TargetWorkspace(File root) {
		this.root = root;
	}
	
	public void write(CompilationUnit unit) throws IOException {
		File file = new File(root, unit.getFile().getPath());
		
		FileOutputStream fos = new FileOutputStream(file);
		try {
			Writer iw = new Writer(new PrintWriter(fos));
			unit.write(iw);
			iw.close();
		} finally {
			fos.close();
		}
	}

}
