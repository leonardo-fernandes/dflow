package org.dflow.compiler.tojava.language;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.dflow.compiler.io.CompilationUnit;
import org.dflow.compiler.io.Writer;

public class Class implements CompilationUnit, Annotated {
	
	public static final String JAVA_FILE_EXTENSION = ".java";
	
	public static final Class UNKNOWN = new Class(VisibilityModifier.PRIVATE, "UNKNOWN", "UNKNOWN");
	
	public static final VisibilityModifier DEFAULT_VISIBILITY = VisibilityModifier.PUBLIC;
	
	private final VisibilityModifier visibility;
	private final String namespace;
	private final String name;
	
	private final List<Annotation> annotations = new LinkedList<>();
	
	private final List<Field> fields = new LinkedList<>();
	private final List<Constructor> constructors = new LinkedList<>();
	private final List<Method> methods = new LinkedList<>();
	
	public Class(VisibilityModifier visibility, String namespace, String name) {
		this.visibility = visibility;
		this.namespace = namespace;
		this.name = name;
	}
	
	public Class(String namespace, String name) {
		this(DEFAULT_VISIBILITY, namespace, name);
	}
	
	public String getName() {
		return name;
	}

	public String getNamespace() {
		return namespace;
	}
	
	public String getFullName() {
		return getNamespace() + "." + getName();
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
	
	@Override
	public Annotated addAnnotation(Annotation annotation) {
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
	public File getFile() {
		return new File(getNamespace(), getName() + JAVA_FILE_EXTENSION);
	}
	
	@Override
	public void write(Writer writer) {
		writer.println("package " + getNamespace() + ";");
		writer.println();

		for (Annotation a : annotations) {
			writer.write(a).println();
		}

		writer.write(visibility)
			.write("class " + getName() + " {")
			.println();
		writer.indent();
		
		writer.println();
		
		for (Field f : fields) {
			f.write(writer);
		}
		
		writer.println();
		
		for (Constructor c : constructors) {
			c.write(writer);
			writer.println();
		}
		
		for (Method m : methods) {
			m.write(writer);
			writer.println();
		}
		
		writer.unindent();
		writer.println("}");
	}
}