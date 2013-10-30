package org.dflow.compiler.parser.ast;

import org.dflow.compiler.model.types.Type;

public interface TypeProvider<T extends Type> {
	
	Type getSafeType();
	T getType() throws ClassCastException;
	void setType(T type);

}
