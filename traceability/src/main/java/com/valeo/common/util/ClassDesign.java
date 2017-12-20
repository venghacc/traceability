package com.valeo.common.util;

public class ClassDesign {
	
	public String getClsName() {
		return clsName;
	}
	
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	
	public String getClsModifier() {
		return clsModifier;
	}
	
	public void setClsModifier(String clsModifier) {
		this.clsModifier = clsModifier;
	}
	
	public String[] getImplementations() {
		return implementations;
	}
	
	public void setImplementations(String[] implementations) {
		this.implementations = implementations;
	}
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String[] getMethods() {
		return methods;
	}
	
	public void setMethods(String[] methods) {
		this.methods = methods;
	}
	
	public String[] getFields() {
		return fields;
	}
	
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	public String[] getConstructors() {
		return constructors;
	}
	
	public void setConstructors(String[] constructors) {
		this.constructors = constructors;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String clsName;
	
	public String clsModifier;
	
	public String implementations[];
	
	public String parent;
	
	public String methods[];
	
	public String fields[];
	
	public String constructors[];
	
	public String packageName;

}
