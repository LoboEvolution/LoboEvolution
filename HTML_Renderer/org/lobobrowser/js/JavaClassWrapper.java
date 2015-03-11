/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.js;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Function;


/**
 * The Class JavaClassWrapper.
 */
public class JavaClassWrapper {
	
	/** The java class. */
	private final Class javaClass;
	
	/** The functions. */
	private final Map<String, JavaFunctionObject> functions = new HashMap<String, JavaFunctionObject>();
	
	/** The properties. */
	private final Map<String, PropertyInfo> properties = new HashMap<String, PropertyInfo>();
	
	/** The name indexer. */
	private PropertyInfo nameIndexer;
	
	/** The integer indexer. */
	private PropertyInfo integerIndexer;

	/**
	 * Instantiates a new java class wrapper.
	 *
	 * @param class1 the class1
	 */
	public JavaClassWrapper(Class class1) {
		super();
		this.javaClass = class1;
		this.scanMethods();
	}

	/**
	 * New instance.
	 *
	 * @return the object
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public Object newInstance() throws InstantiationException,
			IllegalAccessException {
		return this.javaClass.newInstance();
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName() {
		String className = this.javaClass.getName();
		int lastDotIdx = className.lastIndexOf('.');
		return lastDotIdx == -1 ? className : className
				.substring(lastDotIdx + 1);
	}

	/**
	 * Gets the function.
	 *
	 * @param name the name
	 * @return the function
	 */
	public Function getFunction(String name) {
		return (Function) this.functions.get(name);
	}

	/**
	 * Gets the property.
	 *
	 * @param name the name
	 * @return the property
	 */
	public PropertyInfo getProperty(String name) {
		return (PropertyInfo) this.properties.get(name);
	}

	/**
	 * Scan methods.
	 */
	private void scanMethods() {
		Method[] methods = this.javaClass.getMethods();
		int len = methods.length;
		for (int i = 0; i < len; i++) {
			Method method = methods[i];
			String name = method.getName();
			if (isPropertyMethod(name, method)) {
				this.ensurePropertyKnown(name, method);
			} else {
				if (isNameIndexer(name, method)) {
					this.updateNameIndexer(name, method);
				} else if (this.isIntegerIndexer(name, method)) {
					this.updateIntegerIndexer(name, method);
				}
				JavaFunctionObject f = (JavaFunctionObject) this.functions
						.get(name);
				if (f == null) {
					f = new JavaFunctionObject(name);
					this.functions.put(name, f);
				}
				f.addMethod(method);
			}
		}
	}

	/**
	 * Checks if is name indexer.
	 *
	 * @param name the name
	 * @param method the method
	 * @return true, if is name indexer
	 */
	private boolean isNameIndexer(String name, Method method) {
		return ("namedItem".equals(name) && method.getParameterTypes().length == 1)
				|| ("setNamedItem".equals(name) && method.getParameterTypes().length == 2);
	}

	/**
	 * Checks if is integer indexer.
	 *
	 * @param name the name
	 * @param method the method
	 * @return true, if is integer indexer
	 */
	private boolean isIntegerIndexer(String name, Method method) {
		return ("item".equals(name) && method.getParameterTypes().length == 1)
				|| ("setItem".equals(name) && method.getParameterTypes().length == 2);
	}

	/**
	 * Update name indexer.
	 *
	 * @param methodName the method name
	 * @param method the method
	 */
	private void updateNameIndexer(String methodName, Method method) {
		boolean getter = true;
		if (methodName.startsWith("set")) {
			getter = false;
		}
		PropertyInfo indexer = this.nameIndexer;
		if (indexer == null) {
			indexer = new PropertyInfo("$item", Object.class);
			this.nameIndexer = indexer;
		}
		if (getter) {
			indexer.setGetter(method);
		} else {
			indexer.setSetter(method);
		}
	}

	/**
	 * Update integer indexer.
	 *
	 * @param methodName the method name
	 * @param method the method
	 */
	private void updateIntegerIndexer(String methodName, Method method) {
		boolean getter = true;
		if (methodName.startsWith("set")) {
			getter = false;
		}
		PropertyInfo indexer = this.integerIndexer;
		if (indexer == null) {
			Class pt = getter ? method.getReturnType() : method
					.getParameterTypes()[1];
			indexer = new PropertyInfo("$item", pt);
			this.integerIndexer = indexer;
		}
		if (getter) {
			indexer.setGetter(method);
		} else {
			indexer.setSetter(method);
		}
	}

	/**
	 * Gets the integer indexer.
	 *
	 * @return the integer indexer
	 */
	public PropertyInfo getIntegerIndexer() {
		return this.integerIndexer;
	}

	/**
	 * Gets the name indexer.
	 *
	 * @return the name indexer
	 */
	public PropertyInfo getNameIndexer() {
		return this.nameIndexer;
	}

	/**
	 * Checks if is property method.
	 *
	 * @param name the name
	 * @param method the method
	 * @return true, if is property method
	 */
	private boolean isPropertyMethod(String name, Method method) {
		if (name.startsWith("get") || name.startsWith("is")) {
			return method.getParameterTypes().length == 0;
		} else if (name.startsWith("set")) {
			return method.getParameterTypes().length == 1;
		} else {
			return false;
		}
	}

	/**
	 * Property uncapitalize.
	 *
	 * @param text the text
	 * @return the string
	 */
	private String propertyUncapitalize(String text) {
		try {
			if (text.length() > 1 && Character.isUpperCase(text.charAt(1))) {
				// If second letter is capitalized, don't uncapitalize,
				// e.g. getURL.
				return text;
			}
			return Character.toLowerCase(text.charAt(0)) + text.substring(1);
		} catch (IndexOutOfBoundsException iob) {
			return text;
		}
	}

	/**
	 * Ensure property known.
	 *
	 * @param methodName the method name
	 * @param method the method
	 */
	private void ensurePropertyKnown(String methodName, Method method) {
		String capPropertyName;
		String propertyName;
		boolean getter = false;
		if (methodName.startsWith("get")) {
			capPropertyName = methodName.substring(3);
			propertyName = propertyUncapitalize(capPropertyName);
			getter = true;
		} else if (methodName.startsWith("set")) {
			capPropertyName = methodName.substring(3);
			propertyName = propertyUncapitalize(capPropertyName);
		} else if (methodName.startsWith("is")) {
			capPropertyName = methodName.substring(2);
			propertyName = propertyUncapitalize(capPropertyName);
			getter = true;
		} else {
			throw new IllegalArgumentException("methodName=" + methodName);
		}
		PropertyInfo pinfo = (PropertyInfo) this.properties.get(propertyName);
		if (pinfo == null) {
			Class pt = getter ? method.getReturnType() : method
					.getParameterTypes()[0];
			pinfo = new PropertyInfo(propertyName, pt);
			this.properties.put(propertyName, pinfo);
		}
		if (getter) {
			pinfo.setGetter(method);
		} else {
			pinfo.setSetter(method);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.javaClass.getName();
	}
}
