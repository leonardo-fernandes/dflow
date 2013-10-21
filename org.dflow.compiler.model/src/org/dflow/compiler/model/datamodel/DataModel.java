package org.dflow.compiler.model.datamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DataModel {

	private final List<Entity> entities = new LinkedList<>();
	
	public DataModel() {
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(entities);
	}
	
}
