package org.dflow.compiler.model.enums;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.types.EnumerateType;
import org.dflow.compiler.model.types.TypeProvider;

public class Enumerate implements TypeProvider<EnumerateType> {
	
	private final String $package;
	private final TypeProvider<?> parent;
	private final String name;
	
	private final List<String> values = new LinkedList<>();
	
	private final EnumerateType type = new EnumerateType(this);
	
	public Enumerate(String $package, TypeProvider<?> parent, String name) {
		this.$package = $package;
		this.parent = parent;
		this.name = name;
	}
	
	public Enumerate(String $package, String name) {
		this($package, null, name);
	}
	
	@Override
	public String getPackage() {
		return $package;
	}
	
	@Override
	public boolean isNested() {
		return parent != null;
	}
	
	@Override
	public TypeProvider<?> getParent() {
		return parent;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getFullName() {
		if (isNested()) {
			return getParent().getFullName() + "." + getName();
		} else {
			return getPackage() + "." + getName();
		}
	}
	
	@Override
	public EnumerateType getType() {
		return type;
	}
	
	public void addValue(String value) {
		values.add(value);
	}
	
	public Iterable<String> getValues() {
		return Collections.unmodifiableCollection(values);
	}
}
