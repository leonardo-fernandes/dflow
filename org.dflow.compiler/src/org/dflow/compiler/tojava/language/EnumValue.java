package org.dflow.compiler.tojava.language;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class EnumValue implements Writeable {

	private final String value;
	
	public EnumValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	@Override
	public void write(Writer writer) {
		writer.write(getValue());
	}
}
