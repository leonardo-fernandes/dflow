package org.dflow.compiler.semantic;

import java.util.ListIterator;
import java.util.Stack;

import org.dflow.compiler.model.types.Type;

public final class ScopeStack extends Scope {

	private final Stack<Scope> stack = new Stack<>();
	
	public ScopeStack(TypeResolver resolver) {
		super(resolver);
	}
	
	public void push(Scope scope) {
		stack.push(scope);
	}
	
	public void pop() {
		stack.pop();
	}
	
	public Type lookup(String name) {
		Type fullType = resolver.lookup(name);
		if (fullType != Type.UNKNOWN) {
			return fullType;
		}
		
		ListIterator<Scope> iter = stack.listIterator(stack.size());
		while (iter.hasPrevious()) {
			Scope s = iter.previous();
			
			Type scopeType = s.lookup(name);
			if (scopeType != Type.UNKNOWN) {
				return scopeType;
			}
		}
		
		return Type.UNKNOWN;
	}

}
