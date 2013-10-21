package org.dflow.compiler.io.templating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Template implements Writeable {
	
	private final BufferedReader template;
	private Map<String, String> stringReplacements = new HashMap<>();
	private Map<String, Writeable> writeableReplacements = new HashMap<String, Writeable>();
	
	public Template(Reader template) {
		this.template = new BufferedReader(template);
	}
	
	public void replace(String key, String replacement) {
		stringReplacements.put(key, replacement);
	}
	
	public void replace(String targetKey, Writeable replacement) {
		writeableReplacements.put(targetKey, replacement);
	}
	
	@Override
	public void write(Writer writer) {
		String line;
		while ((line = readLine()) != null) {
			for (Map.Entry<String, String> entry : stringReplacements.entrySet()) {
				String target = getReplacementTarget(entry.getKey());
				if (line.contains(target)) {
					line = line.replace(target, getReplacementValue(entry.getValue()));
				}
			}
			
			for (Map.Entry<String, Writeable> entry : writeableReplacements.entrySet()) {
				String target = getReplacementTarget(entry.getKey());
				if (line.contains(target)) {
					line = line.replace(target, getReplacementValue(entry.getValue()));
				}
			}
			
			writer.println(line);
		}
	}
	
	private String readLine() {
		try {
			return template.readLine();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getReplacementTarget(String replacement) {
		return "${" + replacement + "}";
	}
	
	private String getReplacementValue(String replacement) {
		return replacement;
	}
	
	private String getReplacementValue(Writeable value) {
		StringWriter sw = new StringWriter();
		Writer w = new Writer(new PrintWriter(sw));
		try {
			w.write(value);
			w.close();
			return sw.toString();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
