package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class SourceFile implements Writeable {
	
	private final String $package;
	private final Writeable content;
	
	public SourceFile(String $package, Writeable content) {
		this.$package = $package;
		this.content = content;
	}
	
	public String getPackage() {
		return $package;
	}
	
	public void write(Writer writer) {
		writer.println("package " + getPackage() + ";");
		writer.println();
		writer.write(content);
	}

}
