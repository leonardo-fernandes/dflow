package org.dflow.compiler.parser.ast.typereferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.types.GenericType;
import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.semantic.Scope;

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
	
	@Override
	public Type resolveType(Scope currentScope) {
		return new GenericType(type.resolveType(currentScope), resolveArguments(currentScope));
	}
	
	private Iterable<Type> resolveArguments(Scope currentScope) {
		List<Type> argTypes = new LinkedList<>();
		for (TypeReference argType : arguments.arguments) {
			argTypes.add(argType.resolveType(currentScope));
		}
		return argTypes;
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
