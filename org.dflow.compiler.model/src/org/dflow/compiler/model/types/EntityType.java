package org.dflow.compiler.model.types;

import org.dflow.compiler.model.datamodel.Entity;

public class EntityType extends Type {

	private final Entity entity;
	
	public EntityType(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public String getName() {
		return entity.getName();
	}
	
	@Override
	public String getFullName() {
		return entity.getFullName();
	}
	
}
