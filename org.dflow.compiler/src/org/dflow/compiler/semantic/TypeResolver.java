package org.dflow.compiler.semantic;

import java.util.HashMap;
import java.util.Map;

import org.dflow.compiler.model.types.ExternalType;
import org.dflow.compiler.model.types.PrimitiveType;
import org.dflow.compiler.model.types.Type;

public class TypeResolver {

	private final Map<String, Type> externalTypes = new HashMap<String, Type>();
	private final Map<String, Type> userDefinedTypes = new HashMap<String, Type>();
	
	public void add(Type type) {
		userDefinedTypes.put(type.getFullName(), type);
	}
	
	public Type lookup(String fullName) {
		Type primitive = PrimitiveType.lookup(fullName);
		if (primitive != null) {
			return primitive;
		}

		Type udt = userDefinedTypes.get(fullName);
		if (udt != null) {
			return udt;
		}
		
		Type ext = externalTypes.get(fullName);
		if (ext != null) {
			return ext;
		}
		
		Type newExt = resolveExternalType(fullName);
		if (newExt != null) {
			externalTypes.put(fullName, newExt);
			return newExt;
		}

		return Type.UNKNOWN;
	}
	
	private Type resolveExternalType(String fullName) {
		try {
			return new ExternalType(Class.forName(fullName));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
