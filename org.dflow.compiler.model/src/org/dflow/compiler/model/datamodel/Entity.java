package org.dflow.compiler.model.datamodel;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Entity {
	
	private final String $package;
	private final String name;
	private final List<Attribute> attributes = new LinkedList<>();
	
	public Entity(String $package, String name) {
		this.$package = $package;
		this.name = name;
	}
	
	public String getPackage() {
		return $package;
	}
	
	public File getPackageDirectory() {
		return new File($package.replace('.', File.separatorChar));
	}

	public String getName() {
		return name;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public Iterable<Attribute> getAttributes() {
		return Collections.unmodifiableCollection(attributes);
	}
}
