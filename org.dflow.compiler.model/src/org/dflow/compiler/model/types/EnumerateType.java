package org.dflow.compiler.model.types;

import org.dflow.compiler.model.enums.Enumerate;

public class EnumerateType extends Type {
	
	private final Enumerate $enum;
	
	public EnumerateType(Enumerate $enum) {
		this.$enum = $enum;
	}
	
	@Override
	public String getName() {
		return $enum.getName();
	}

	@Override
	public String getFullName() {
		return $enum.getFullName();
	}

}
