package org.dflow.compiler.semantic.scope;

import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.datamodel.Entity;
import org.dflow.compiler.parser.ast.enumerate.Enumerate;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.TypeResolver;

public class EntityDeclarationScope extends Scope {
	
	private final Entity entity;
	
	public EntityDeclarationScope(TypeResolver resolver, Entity entity) {
		super(resolver);
		this.entity = entity;
	}
	
	@Override
	public Type lookup(String name) {
		if (name.equals(entity.getType().getFullName()) || name.equals(entity.getType().getName())) {
			return entity.getType();
		}
		
		for (Entity e : entity.getNestedEntities()) {
			if (name.equals(e.getType().getFullName()) || name.equals(e.getType().getName())) {
				return e.getType();
			}
			if (name.equals(entity.getType().getName() + "." + e.getType().getName())) {
				return e.getType();
			}
		}
		
		for (Enumerate e : entity.getNestedEnums()) {
			if (name.equals(e.getType().getFullName()) || name.equals(e.getType().getName())) {
				return e.getType();
			}
			if (name.equals(e.getType().getName() + "." + e.getType().getName())) {
				return e.getType();
			}
		}
		
		return Type.UNKNOWN;
	}
	
}
