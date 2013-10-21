package org.dflow.unittest.compiler.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import org.dflow.compiler.io.writing.Writer;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractWritingTest {
	
	private StringWriter sw;
	protected Writer writer;
	
	@Before
	public void setup() {
		sw = new StringWriter();
		writer = new Writer(new PrintWriter(sw));
	}
	
	@After
	public void teardown() throws IOException {
		writer.close();
		writer = null;
		sw = null;
	}

	/**
	 * Retrieves the expected text for a given test.
	 * The expected is looked up in a resource,
	 * with the name <i>TestClassName</i>.<i>testName</i>.
	 * @return The contents of the expected resource file.
	 * @throws IllegalStateException If the resource is not
	 * found or cannot be read.
	 */
	protected String expected() {
		String resourceName = getExpectedResourceName();
		InputStream resourceStream = this.getClass().getResourceAsStream(resourceName);
		if (resourceStream == null) {
			throw new IllegalStateException("Could not locate resource " + resourceName);
		}
		
		Reader resourceReader = new InputStreamReader(resourceStream);
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[1024];
		
		try {
			int n;
			while ((n = resourceReader.read(buffer)) != -1) {
				sb.append(buffer, 0, n);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		return sb.toString();
	}
	
	/**
	 * Retrieves the text written by {@link AbstractWritingTest#writer}.
	 * @return The text that was written, since the beginning of the test.
	 */
	protected String written() {
		return sw.toString();
	}

	private String getExpectedResourceName() {
		for (StackTraceElement el : new Throwable().getStackTrace()) {
			if (el.getClassName().equals(this.getClass().getCanonicalName())) {
				return this.getClass().getSimpleName() + "." + el.getMethodName();
			}
		}
		
		throw new IllegalStateException("Could not determine the resource name");
	}
}
