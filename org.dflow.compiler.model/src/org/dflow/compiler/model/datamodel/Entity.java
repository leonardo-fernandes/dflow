package org.dflow.compiler.model.datamodel;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.enums.Enumerate;
import org.dflow.compiler.model.types.EntityType;
import org.dflow.compiler.model.types.TypeProvider;

public class Entity implements TypeProvider<EntityType> {
	
	private final String $package;
	private final Entity parent;
	private final String name;

	private final List<Attribute> attributes = new LinkedList<>();
	private final List<Enumerate> nestedEnums = new LinkedList<>();
	private final List<Entity> nestedEntities = new LinkedList<>();
	
	private final EntityType type = new EntityType(this);
	
	public Entity(String $package, Entity parent, String name) {
		this.$package = $package;
		this.parent = parent;
		this.name = name;
	}
	
	public Entity(String $package, String name) {
		this($package, null, name);
	}
	
	@Override
	public String getPackage() {
		return $package;
	}
	
	public File getPackageDirectory() {
		return new File($package.replace('.', File.separatorChar));
	}
	
	@Override
	public boolean isNested() {
		return parent != null;
	}
	
	@Override
	public Entity getParent() {
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
	public EntityType getType() {
		return type;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public Iterable<Attribute> getAttributes() {
		return Collections.unmodifiableCollection(attributes);
	}
	
	public void addNestedEnum(Enumerate $enum) {
		nestedEnums.add($enum);
	}
	
	public Iterable<Enumerate> getNestedEnums() {
		return Collections.unmodifiableCollection(nestedEnums);
	}
	
	public void addNestedEntity(Entity nestedEntity) {
		nestedEntities.add(nestedEntity);
	}
	
	public Iterable<Entity> getNestedEntities() {
		return Collections.unmodifiableCollection(nestedEntities);
	}
}
