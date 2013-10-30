package org.dflow.compiler.parser.ast.application;

import java.util.Arrays;

import org.dflow.compiler.parser.ast.Node;

public class Application extends Node {
	
	private final String name;
	private final Contents contents;
	
	public Application(String name, Contents contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public String getName() {
		return name;
	}
	
	public Contents getContents() {
		return contents;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { contents });
	}
	
	public static class Contents extends Node {
		
	}
}
