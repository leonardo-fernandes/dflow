package org.dflow.compiler.parser.ast.enumerate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.model.types.EnumerateType;
import org.dflow.compiler.model.types.Type;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.parser.ast.TypeProvider;

public class Enumerate extends Node implements TypeProvider<EnumerateType> {

	private final String name;
	private final Contents contents;
	
	private org.dflow.compiler.model.enums.Enumerate model;
	
	public Enumerate(String name, Contents contents) {
		this.name = name;
		this.contents = contents;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<Value> getValues() {
		return Collections.unmodifiableCollection(contents.values.values);
	}
	
	public org.dflow.compiler.model.enums.Enumerate getModel() {
		return model;
	}
	
	public void setModel(org.dflow.compiler.model.enums.Enumerate model) {
		this.model = model;
	}
	
	@Override
	protected Iterable<Node> getChildren() {
		return Arrays.asList(new Node[] { contents });
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
	public EnumerateType getType() {
		return model.getType();
	}
	
	public static class Contents extends Node {
		
		private final Values values;
		
		public Contents(Values values) {
			this.values = values;
		}
		
		@Override
		protected Iterable<Node> getChildren() {
			return Arrays.asList(new Node[] { values });
		}
	}
	
	public static class Values extends Node {
		
		private final List<Value> values = new LinkedList<>();
		
		public Values add(Value value) {
			values.add(value);
			return this;
		}
		
		@Override
		protected Iterable<Node> getChildren() {
			return Collections.<Node>unmodifiableCollection(values);
		}
	}
	
	public static class Value extends Node {
		
		private final String name;
		
		public Value(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
