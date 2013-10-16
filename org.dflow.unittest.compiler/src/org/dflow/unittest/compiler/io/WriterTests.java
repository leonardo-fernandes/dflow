package org.dflow.unittest.compiler.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.Writeable;
import org.dflow.compiler.io.Writer;
import org.junit.Test;

public class WriterTests extends AbstractWriterTest {
	
	@Test
	public void writeString() {
		writer.write("plain string");
		assertEquals("plain string", written());
	}
	
	@Test
	public void writeWriteable() {
		writer.write(new Writeable() {
			@Override
			public void write(Writer writer) {
				writer.write("writable string");
			}
		});
		assertEquals("writable string", written());
	}
	
	@Test
	public void writeWriteableList() {
		List<Writeable> list = new LinkedList<>();
		list.add(new Writeable() {
			@Override
			public void write(Writer writer) {
				writer.write("writeable1");
			}
		});
		list.add(new Writeable() {
			@Override
			public void write(Writer writer) {
				writer.write("writeable2");
			}
		});
		
		writer.write(list, ", ");
		assertEquals("writeable1, writeable2", written());
	}
	
	@Test
	public void println() {
		writer.println("line printed");
		writer.println();
		assertEquals("line printed\n\n", written());
	}

	@Test
	public void indentation() {
		writer.write("public void method() {");
		writer.indent();
			writer.println(" // this will appear in the same line");
			writer.println("if (true) {");
			writer.indent();
				writer.println("System.out.println(\"must execute\");");
			writer.unindent();
			writer.println("} else {");
			writer.indent();
				writer.println("// never reachable");
			writer.unindent();
			writer.println("}");
		writer.unindent();
		writer.write("}");
		assertEquals(expected(), written());
	}
	
	@Test(expected=IllegalStateException.class)
	public void illegalUnidentation() {
		writer.unindent();
	}
	
	@Test(expected=IOException.class)
	public void closeWithError() throws IOException {
		java.io.Writer sw = new java.io.Writer() {
			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {
				throw new IOException("could not write string");
			}
			@Override
			public void flush() {
			}
			@Override
			public void close() {
			}
		};
		Writer writer = new Writer(new PrintWriter(sw));
		writer.write("will crash");
		writer.close();
	}
	
	@Test(expected=IllegalStateException.class)
	public void closeWithIndentation() throws IOException {
		StringWriter sw = new StringWriter();
		Writer writer = new Writer(new PrintWriter(sw));
		writer.println("public class {");
		writer.indent();
		writer.println("}");
		writer.close();
	}
}
