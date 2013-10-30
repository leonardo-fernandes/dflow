package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.parser.ast.Node;

public class GenericTypeReference extends TypeReference {
	
	private final TypeReference type;
	private final Arguments arguments;
	
	public GenericTypeReference(TypeReference type, Arguments arguments) {
		this.type = type;
		this.arguments = arguments;
	}
	
	public TypeReference getType() {
		return type;
	}
	
	public Arguments getArguments() {
		return arguments;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { type, arguments });
	}
	
	public static class Arguments extends Node {
		
		private final List<TypeReference> arguments = new LinkedList<>();
		
		public Arguments add(TypeReference argument) {
			arguments.add(argument);
			return this;
		}
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(arguments);
		}
		
	}

}
