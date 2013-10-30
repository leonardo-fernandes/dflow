package org.dflow.compiler.model.datamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.enums.Enumerate;

public class DataModel {

	private final List<Entity> entities = new LinkedList<>();
	private final List<Enumerate> enums = new LinkedList<>();
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(entities);
	}
	
	public void addEnum(Enumerate $enum) {
		enums.add($enum);
	}
	
	public Collection<Enumerate> getEnums() {
		return Collections.unmodifiableCollection(enums);
	}
	
}
