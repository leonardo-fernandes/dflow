package org.dflow.compiler.model;

import java.io.File;

import org.dflow.compiler.model.datamodel.DataModel;

public class Application {
	
	private final String $package;
	private final String name;
	private final DataModel dataModel = new DataModel();

	public Application(String $package, String name) {
		this.$package = $package;
		this.name = name;
	}
	
	public String getPackage() {
		return $package;
	}
	
	public File getPackageDirectory() {
		return new File($package.replace('.', File.separatorChar));
	}

	public String getName() {
		return name;
	}

	public DataModel getDataModel() {
		return dataModel;
	}
	
}
