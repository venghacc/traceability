package com.valeo.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class IntrospectClass {

	private Class<?> cl;

	public IntrospectClass(String filPath, String fullyQualifiedFileName)
			throws MalformedURLException, ClassNotFoundException {

		URLClassLoader urlClassLoader = URLClassLoader
				.newInstance(new URL[] { new URL(filPath) });

		this.cl = urlClassLoader.loadClass(fullyQualifiedFileName);
	}

	/**
	 * gets all constructors of a class
	 */
	public String[] getConstructors() {
		Constructor<?>[] constructors = this.cl.getDeclaredConstructors();
		String constructorsStr[] = new String[constructors.length];
		int i=0;
		

		for (Constructor<?> c : constructors) {
			
			StringBuffer temp = new StringBuffer();
			String name = c.getName();
			temp.append("   ");
			String modifiers = Modifier.toString(c.getModifiers());
			if (modifiers.length() > 0)
				temp.append(modifiers + " ");
			temp.append(name + "(");

			// print parameter types
			Class<?>[] paramTypes = c.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++) {
				if (j > 0)
					temp.append(", ");
				temp.append(paramTypes[j].getName());
			}
			temp.append(");\n");
			
			constructorsStr[i]= temp.toString();
			i++;
		}
		
		return constructorsStr;
	}

	/**
	 * gets all methods of a class
	 */
	public String[] getMethods() {
		Method[] methods = this.cl.getDeclaredMethods();
		String methodsStr[] = new String[methods.length];
		int i=0;	
		
		for (Method m : methods) {
			StringBuffer temp = new StringBuffer();
			Class<?> retType = m.getReturnType();
			String name = m.getName();

			temp.append("   ");
			// print modifiers, return type and method name
			String modifiers = Modifier.toString(m.getModifiers());
			if (modifiers.length() > 0)
				temp.append(modifiers + " ");
			temp.append(retType.getName() + " " + name + "(");

			// print parameter types
			Class<?>[] paramTypes = m.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++) {
				if (j > 0)
					temp.append(", ");
				temp.append(paramTypes[j].getName());
			}
			temp.append(")");

			Class<?> excetionTypes[] = m.getExceptionTypes();

			if (excetionTypes != null && excetionTypes.length > 0) {
				temp.append(" throws ");

				StringBuffer exceptionsStr = new StringBuffer();
				for (Class<?> e1 : excetionTypes) {

					exceptionsStr.append(e1.getTypeName() + ", ");

				}
				temp.append(exceptionsStr.substring(0,	exceptionsStr.lastIndexOf(",")));
			}
			temp.append(";\n");
			methodsStr[i] = temp.toString();
			i++;
		}
		
		return methodsStr;
		
	}

	/**
	 * gets all fields of a class
	 */
	public String[] getFields() {
		Field[] fields = this.cl.getDeclaredFields();
		String fieldStr[] = new String[fields.length];
		int i=0;
		
		for (Field f : fields) {
			StringBuffer temp = new StringBuffer();
			Class<?> type = f.getType();
			String name = f.getName();
			temp.append("   ");
			String modifiers = Modifier.toString(f.getModifiers());
			if (modifiers.length() > 0)
				temp.append(modifiers + " ");
			temp.append(type.getName()) .append(" ") .append(name) .append(";\n");
			fieldStr[i]=temp.toString();
			i++;
		}
		return fieldStr;
	}

	/**
	 * gets name of a class
	 */
	public String getClassName() {
		
		return this.cl.getName();
	}
	
	
	/**
	 * gets package of a class
	 */
	public String getPackageName() {
		
		return this.cl.getPackage().getName();
	}
		
	
	public static final int test1() throws Exception {
		return 0;
	}

}