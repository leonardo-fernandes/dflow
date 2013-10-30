package org.dflow.compiler.tojava;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.io.TargetWorkspace;
import org.dflow.compiler.io.writing.Writer;
import org.dflow.compiler.model.datamodel.Attribute;
import org.dflow.compiler.model.datamodel.DataModel;
import org.dflow.compiler.model.datamodel.Entity;
import org.dflow.compiler.model.enums.Enumerate;
import org.dflow.compiler.model.types.TypeProvider;
import org.dflow.compiler.parser.DataModelParser;
import org.dflow.compiler.parser.DflowLexer;
import org.dflow.compiler.parser.ast.DflowFile;
import org.dflow.compiler.parser.ast.Node;
import org.dflow.compiler.semantic.CompilationContext;
import org.dflow.compiler.tojava.language.Annotation;
import org.dflow.compiler.tojava.language.Class;
import org.dflow.compiler.tojava.language.EnumValue;
import org.dflow.compiler.tojava.language.Field;
import org.dflow.compiler.tojava.language.Method;
import org.dflow.compiler.tojava.language.SourceFile;
import org.dflow.compiler.tojava.language.Type;


class DataModelCompiler {
	
	private static final String DATAMODEL_PACKAGE = "datamodel";
	
	private final SourceWorkspace source;
	private final TargetWorkspace target;
	private final CompilationContext context;
	
	public DataModelCompiler(SourceWorkspace source, TargetWorkspace target, CompilationContext context) {
		this.source = source;
		this.target = target;
		this.context = context;
	}

	public DataModel compile() throws IOException {
		File dataModel = context.getSubpackage(DATAMODEL_PACKAGE);

		SourceWorkspace.Visitor parser = new ParseDataModelVisitor(source, dataModel);
		parser.visit();
		
		new RegisterTypesVisitor(context.getParsedFiles()).visit();
		
		for (Entity e : context.getApplication().getDataModel().getEntities()) {
			compile(e);
		}
		
		return context.getApplication().getDataModel();
	}

	private void compile(Entity e) throws IOException {
		String java = new File(e.getPackageDirectory(), e.getName() + TargetWorkspace.JAVA_EXTENSION).getPath();
		Writer w = target.openSource(java);
		try {
			w.write(toJava(e));
		} finally {
			w.close();
		}
	}

	private SourceFile toJava(Entity e) {
		Class c = toJavaClass(e);
		return new SourceFile(c.getPackage(), c);
	}
	
	private Class toJavaClass(Entity e) {
		Class c = new Class(e.getPackage(), e.getName());
		c.addAnnotation(new Annotation(new Type(javax.persistence.Entity.class)));
		
		Field id = new Field(Type.INT, "id");
		id.addAnnotation(new Annotation(new Type(javax.persistence.Id.class)));
		c.addField(id);
		c.addMethod(Method.getter(id)).addMethod(Method.setter(id));
		
		for (Attribute a : e.getAttributes()) {
			Field f = new Field(new Type(a.getType().getFullName()), a.getName());
			c.addField(f);
			c.addMethod(Method.getter(f)).addMethod(Method.setter(f));
		}
		
		Field version = new Field(Type.INT, "version");
		version.addAnnotation(new Annotation(new Type(javax.persistence.Version.class)));
		c.addField(version);
		c.addMethod(Method.getter(version)).addMethod(Method.setter(version));
		
		for (Entity nested : e.getNestedEntities()) {
			Class nestedClass = toJavaClass(nested);
			c.addNested(nestedClass);
		}
		
		for (Enumerate nested : e.getNestedEnums()) {
			org.dflow.compiler.tojava.language.Enum nestedEnum = new org.dflow.compiler.tojava.language.Enum(nested.getPackage(), nested.getName());
			for (String v : nested.getValues()) {
				nestedEnum.addValue(new EnumValue(v));
			}
			c.addNested(nestedEnum);
		}

		return c;
	}
	
	private class ParseDataModelVisitor extends SourceWorkspace.Visitor {

		public ParseDataModelVisitor(SourceWorkspace source, File dataModelPackage) {
			super(source, dataModelPackage);
		}
		
		@Override
		protected Action visit(File file) throws IOException {
			DataModelParser parser = new DataModelParser(new DflowLexer(source.open(file), file), file);
			parser.parse();
			context.addParsedFile(parser.getParsedFile());
			return Action.CONTINUE;
		}
	}
	
	private class RegisterTypesVisitor extends Node.Visitor {
		
		private String $package;
		private Stack<TypeProvider<?>> nesting;
		
		public RegisterTypesVisitor(Iterable<DflowFile> files) {
			super(files);
		}
		
		@Override
		protected Action enter(Node n) {
			if (n instanceof DflowFile) {
				$package = ((DflowFile) n).getPackage().getName();
				nesting = new Stack<>();
			} else if (n instanceof org.dflow.compiler.parser.ast.TypeProvider<?>) {
				if (n instanceof org.dflow.compiler.parser.ast.datamodel.Entity) {
					nesting.push(registerEntity((org.dflow.compiler.parser.ast.datamodel.Entity) n));
				} else if (n instanceof org.dflow.compiler.parser.ast.enumerate.Enumerate) {
					nesting.push(registerEnumerate((org.dflow.compiler.parser.ast.enumerate.Enumerate) n));
				} else {
					throw new IllegalArgumentException();
				}
			}
			return Action.CONTINUE;
		}

		@Override
		protected void leave(Node n) {
			if (n instanceof DflowFile) {
				$package = null;
				nesting = null;
			} else if (n instanceof org.dflow.compiler.parser.ast.TypeProvider<?>) {
				nesting.pop();
			}
		}
		
		private Entity registerEntity(org.dflow.compiler.parser.ast.datamodel.Entity node) {
			Entity entity;
			if (nesting.isEmpty()) {
				entity = new Entity($package, node.getName());
				context.getApplication().getDataModel().addEntity(entity);
			} else {
				Entity parent = (Entity) nesting.peek();
				entity = new Entity($package, parent, node.getName());
				parent.addNestedEntity(entity);
			}
			
			node.setType(entity.getType());
			context.getTypeResolver().add(entity.getType());
			return entity;
		}
		
		private Enumerate registerEnumerate(org.dflow.compiler.parser.ast.enumerate.Enumerate node) {
			Enumerate $enum;
			if (nesting.isEmpty()) {
				$enum = new Enumerate($package, node.getName());
				context.getApplication().getDataModel().addEnum($enum);
			} else {
				Entity parent = (Entity) nesting.peek();
				$enum = new Enumerate($package, parent, node.getName());
				parent.addNestedEnum($enum);
			}
			
			node.setType($enum.getType());
			context.getTypeResolver().add($enum.getType());
			return $enum;
		}
		
	}
}
