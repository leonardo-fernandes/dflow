package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Enum implements Writeable, Annotated {
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PUBLIC;
	
	private final VisibilityModifier visibility;
	private final String $package;
	private final String name;
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	private final List<EnumValue> values = new LinkedList<>();
	
	public Enum(VisibilityModifier visibility, String $package, String name) {
		this.visibility = visibility;
		this.$package = $package;
		this.name = name;
	}
	
	public Enum(String $package, String name) {
		this(DEFAULT_VISIBILITY, $package, name);
	}
	
	public String getName() {
		return name;
	}

	public String getPackage() {
		return $package;
	}
	
	public String getFullName() {
		return getPackage() + "." + getName();
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
	
	@Override
	public Enum addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		return this;
	}
	
	public Enum addValue(EnumValue value) {
		this.values.add(value);
		return this;
	}
	
	@Override
	public void write(Writer writer) {
		for (Annotation a : annotations) {
			writer.write(a).println();
		}

		writer.write(visibility)
			.write("enum " + getName() + " {")
			.println();
		writer.indent();
		
		writer.write(values, ", ");
		
		writer.unindent();
		writer.write("}");
	}

}
