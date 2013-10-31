package org.dflow.compiler.parser.ast.datamodel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.types.EntityType;
import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.ScopeProvider;
import org.dflow.compiler.parser.ast.TypeProvider;
import org.dflow.compiler.parser.ast.enumerate.Enumerate;
import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.semantic.Scope;
import org.dflow.compiler.semantic.scope.EntityDeclarationScope;

public class Entity extends Node implements TypeProvider<EntityType>, ScopeProvider {
	
	private final String name;
	private final Contents contents;
	
	private org.dflow.compiler.model.datamodel.Entity model;
	
	public Entity(String name, Contents contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<EntityAttribute> getAttributes() {
		return Collections.unmodifiableCollection(contents.attributes.attributes);
	}
	
	public Collection<Entity> getNestedEntities() {
		return Collections.unmodifiableCollection(contents.entities.entities);
	}
	
	public Collection<Enumerate> getNestedEnums() {
		return Collections.unmodifiableCollection(contents.enums.enums);
	}
	
	public org.dflow.compiler.model.datamodel.Entity getModel() {
		return model;
	}
	
	public void setModel(org.dflow.compiler.model.datamodel.Entity entity) {
		this.model = entity;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { contents });
	}
	
	@Override
	public Scope getScope(CompilationContext context) {
		return new EntityDeclarationScope(context.getTypeResolver(), this);
	}

	@Override
	public Type getSafeType() {
		if (model == null) {
			return Type.UNKNOWN;
		} else {
			return getType();
		}
	}

	@Override
	public EntityType getType() {
		return model.getType();
	}
	
	public static class Contents extends Node {
		
		private final Attributes attributes = new Attributes();
		private final NestedEnums enums = new NestedEnums();
		private final NestedEntities entities = new NestedEntities();
		
		public Contents addAttribute(EntityAttribute attribute) {
			attributes.attributes.add(attribute);
			return this;
		}
		
		public Contents addEnumerate(Enumerate $enum) {
			enums.enums.add($enum);
			return this;
		}
		
		public Contents addNestedEntity(Entity entity) {
			entities.entities.add(entity);
			return this;
		}

		@Override
		protected Iterable<Node> getChildren() {
			return Arrays.asList(new Node[] { attributes, enums, entities });
		}
	}

	public static class Attributes extends Node {
		private final List<EntityAttribute> attributes = new LinkedList<>();
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(attributes);
		}
	}
	
	public static class NestedEnums extends Node {
		private final List<Enumerate> enums = new LinkedList<>();
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(enums);
		}
	}
	
	public static class NestedEntities extends Node {
		private final List<Entity> entities = new LinkedList<>();
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(entities);
		}
	}
}
