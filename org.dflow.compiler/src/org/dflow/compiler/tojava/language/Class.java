package org.dflow.compiler.tojava.language;

import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.writing.Writeable;
import org.dflow.compiler.io.writing.Writer;

public class Class implements Writeable, Annotated {
	
	public static final Class UNKNOWN = new Class(VisibilityModifier.PRIVATE, "UNKNOWN", "UNKNOWN");
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PUBLIC;
	
	private final VisibilityModifier visibility;
	private final String $package;
	private final String name;
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	private final List<Field> fields = new LinkedList<>();
	private final List<Constructor> constructors = new LinkedList<>();
	private final List<Method> methods = new LinkedList<>();
	
	private final List<Class> nestedClasses = new LinkedList<>();
	private final List<Enum> nestedEnums = new LinkedList<>();
	
	public Class(VisibilityModifier visibility, String $package, String name) {
		this.visibility = visibility;
		this.$package = $package;
		this.name = name;
	}
	
	public Class(String $package, String name) {
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
	public Class addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		return this;
	}
	
	public Class addField(Field field) {
		this.fields.add(field);
		field.setDeclaringClass(this);
		return this;
	}
	
	public Class addConstructor(Constructor constructor) {
		this.constructors.add(constructor);
		constructor.setDeclaringClass(this);
		return this;
	}
	
	public Class addMethod(Method method) {
		this.methods.add(method);
		method.setDeclaringClass(this);
		return this;
	}
	
	@Override
	public void write(Writer writer) {
		for (Annotation a : annotations) {
			writer.write(a).println();
		}

		writer.write(visibility)
			.write("class " + getName() + " {")
			.println();
		writer.indent();
		writer.println();
		
		for (Field f : fields) {
			writer.write(f).println();
		}
		writer.println();

		for (Constructor c : constructors) {
			writer.write(c).println();
			writer.println();
		}

		for (Method m : methods) {
			writer.write(m).println();
			writer.println();
		}
		writer.println();
		
		for (Class c : nestedClasses) {
			c.write(writer);
			writer.println();
		}
		
		for (Enum e : nestedEnums) {
			e.write(writer);
			writer.println();
		}
		
		writer.unindent();
		writer.write("}");
	}

	public void addNested(Class nestedClass) {
		nestedClasses.add(nestedClass);
	}
	
	public void addNested(Enum nestedEnum) {
		nestedEnums.add(nestedEnum);
	}
}
