package org.dflow.compiler.parser.ast.datamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.types.EntityType;
import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.TypeProvider;
import org.dflow.compiler.parser.ast.enumerate.Enumerate;

public class Entity extends Node implements TypeProvider<EntityType> {
	
	private final String name;
	private final Contents contents;
	
	private Type type = Type.UNKNOWN;
	
	public Entity(String name, Contents contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public String getName() {
		return name;
	}
	
	public Contents getContents() {
		return contents;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { contents });
	}
	
	@Override
	public Type getSafeType() {
		return type;
	}

	@Override
	public EntityType getType() {
		return (EntityType) type;
	}
	
	@Override
	public void setType(EntityType type) {
		this.type = type;
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
