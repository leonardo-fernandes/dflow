package org.dflow.compiler.tojava;

import java.io.File;
import java.io.IOException;

import org.dflow.compiler.io.SourceWorkspace;
import org.dflow.compiler.io.TargetWorkspace;
import org.dflow.compiler.io.writing.Writer;
import org.dflow.compiler.model.Application;
import org.dflow.compiler.model.datamodel.Attribute;
import org.dflow.compiler.model.datamodel.DataModel;
import org.dflow.compiler.model.datamodel.Entity;
import org.dflow.compiler.parser.EntityParser;
import org.dflow.compiler.parser.exceptions.ParseException;
import org.dflow.compiler.tojava.exceptions.CompilationException;
import org.dflow.compiler.tojava.language.Annotation;
import org.dflow.compiler.tojava.language.Class;
import org.dflow.compiler.tojava.language.Field;
import org.dflow.compiler.tojava.language.Method;
import org.dflow.compiler.tojava.language.Type;

class DataModelCompiler {
	
	private static final String DATAMODEL_PACKAGE = "datamodel";
	
	private final Application application;
	private final SourceWorkspace source;
	private final TargetWorkspace target;
	
	public DataModelCompiler(Application application, SourceWorkspace source, TargetWorkspace target) {
		this.application = application;
		this.source = source;
		this.target = target;
	}

	public DataModel compile() throws IOException, ParseException, CompilationException {
		File dataModel = new File(application.getPackageDirectory(), DATAMODEL_PACKAGE);

		SourceWorkspace.Visitor visitor = new SourceWorkspace.Visitor(source, dataModel) {
			@Override
			protected Action visit(File file) throws IOException, ParseException {
				EntityParser parser = new EntityParser(source.open(file));
				application.getDataModel().addEntity(parser.parse());
				return Action.CONTINUE;
			}
		};
		
		visitor.visit();
		
		for (Entity e : application.getDataModel().getEntities()) {
			compile(e);
		}
		
		return application.getDataModel();
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

	private Class toJava(Entity e) {
		Class c = new Class(e.getPackage(), e.getName());
		c.addAnnotation(new Annotation(new Type(javax.persistence.Entity.class)));
		
		Field id = new Field(Type.INT, "id");
		id.addAnnotation(new Annotation(new Type(javax.persistence.Id.class)));
		c.addField(id);
		c.addMethod(Method.getter(id)).addMethod(Method.setter(id));
		
		Field version = new Field(Type.INT, "version");
		version.addAnnotation(new Annotation(new Type(javax.persistence.Version.class)));
		c.addField(version);
		c.addMethod(Method.getter(version)).addMethod(Method.setter(version));
		
		for (Attribute a : e.getAttributes()) {
			Field f = new Field(new Type(a.getType()), a.getName());
			c.addField(f);
			c.addMethod(Method.getter(f)).addMethod(Method.setter(f));
		}

		return c;
	}
}
