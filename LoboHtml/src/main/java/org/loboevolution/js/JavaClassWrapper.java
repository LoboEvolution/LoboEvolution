/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.loboevolution.js;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.loboevolution.info.PropertyInfo;
import org.mozilla.javascript.Function;

/**
 * <p>JavaClassWrapper class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class JavaClassWrapper {
	private final Map<String, JavaFunctionObject> functions = new HashMap<String, JavaFunctionObject>();
	private PropertyInfo integerIndexer;
	private final Class javaClass;
	private PropertyInfo nameIndexer;
	private final Map<String, PropertyInfo> properties = new HashMap<String, PropertyInfo>();

	/**
	 * <p>Constructor for JavaClassWrapper.</p>
	 *
	 * @param class1 a {@link java.lang.Class} object.
	 */
	public JavaClassWrapper(Class class1) {
		this.javaClass = class1;
		scanMethods();
	}

	private void ensurePropertyKnown(String methodName, Method method) {
		String capPropertyName;
		String propertyName;
		boolean getter = false;
		boolean setter = false;
		if (methodName.startsWith("get")) {
			capPropertyName = methodName.substring(3);
			propertyName = propertyUncapitalize(capPropertyName);
			getter = true;
		} else if (methodName.startsWith("set")) {
			capPropertyName = methodName.substring(3);
			propertyName = propertyUncapitalize(capPropertyName);
			setter = method.getReturnType() == Void.TYPE;
		} else if (methodName.startsWith("is")) {
			capPropertyName = methodName.substring(2);
			propertyName = propertyUncapitalize(capPropertyName);
			getter = true;
		} else {
			throw new IllegalArgumentException("methodName=" + methodName);
		}
		PropertyInfo pinfo = this.properties.get(propertyName);
		if (pinfo == null) {
			final Class pt = getter ? method.getReturnType() : method.getParameterTypes()[0];
			pinfo = new PropertyInfo(propertyName, pt);
			this.properties.put(propertyName, pinfo);
		}
		if (getter) {
			pinfo.setGetter(method);
		}

		if (setter) {
			pinfo.setSetter(method);
		}
	}

	/**
	 * <p>getClassName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName() {
		final String className = this.javaClass.getName();
		final int lastDotIdx = className.lastIndexOf('.');
		return lastDotIdx == -1 ? className : className.substring(lastDotIdx + 1);
	}

	/**
	 * <p>getFunction.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getFunction(String name) {
		return this.functions.get(name);
	}

	/**
	 * <p>Getter for the field integerIndexer.</p>
	 *
	 * @return a {@link org.loboevolution.info.PropertyInfo} object.
	 */
	public PropertyInfo getIntegerIndexer() {
		return this.integerIndexer;
	}

	/**
	 * <p>Getter for the field nameIndexer.</p>
	 *
	 * @return a {@link org.loboevolution.info.PropertyInfo} object.
	 */
	public PropertyInfo getNameIndexer() {
		return this.nameIndexer;
	}

	/**
	 * <p>getProperty.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.info.PropertyInfo} object.
	 */
	public PropertyInfo getProperty(String name) {
		return this.properties.get(name);
	}

	private boolean isIntegerIndexer(String name, Method method) {
		return "item".equals(name) && method.getParameterTypes().length == 1
				|| "setItem".equals(name) && method.getParameterTypes().length == 2;
	}

	private boolean isNameIndexer(String name, Method method) {
		return "namedItem".equals(name) && method.getParameterTypes().length == 1
				|| "setNamedItem".equals(name) && method.getParameterTypes().length == 2;
	}

	private boolean isPropertyMethod(String name, Method method) {
		if (name.startsWith("get") || name.startsWith("is")) {
			return method.getParameterTypes().length == 0;
		} else if (name.startsWith("set") && !name.equals("setTimeout")) {
			return method.getParameterTypes().length == 1;
		} else {
			return false;
		}
	}

	/**
	 * <p>newInstance.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 * @throws java.lang.InstantiationException if any.
	 * @throws java.lang.IllegalAccessException if any.
	 */
	public Object newInstance() throws InstantiationException, IllegalAccessException {
		return this.javaClass.newInstance();
	}

	private String propertyUncapitalize(String text) {
		try {
			if (text.length() > 1 && Character.isUpperCase(text.charAt(1))) {
				// If second letter is capitalized, don't uncapitalize,
				// e.g. getURL.
				return text;
			}
			return Character.toLowerCase(text.charAt(0)) + text.substring(1);
		} catch (final IndexOutOfBoundsException iob) {
			return text;
		}
	}

	private void scanMethods() {
		final Method[] methods = this.javaClass.getMethods();
		final int len = methods.length;
		for (int i = 0; i < len; i++) {
			final Method method = methods[i];
			final String name = method.getName();
			if (isPropertyMethod(name, method)) {
				ensurePropertyKnown(name, method);
			} else {
				if (isNameIndexer(name, method)) {
					updateNameIndexer(name, method);
				} else if (isIntegerIndexer(name, method)) {
					updateIntegerIndexer(name, method);
				}
				JavaFunctionObject f = this.functions.get(name);
				if (f == null) {
					f = new JavaFunctionObject(name);
					this.functions.put(name, f);
				}
				f.addMethod(method);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.javaClass.getName();
	}

	private void updateIntegerIndexer(String methodName, Method method) {
		boolean getter = true;
		if (methodName.startsWith("set")) {
			getter = false;
		}
		PropertyInfo indexer = this.integerIndexer;
		if (indexer == null) {
			final Class pt = getter ? method.getReturnType() : method.getParameterTypes()[1];
			indexer = new PropertyInfo("$item", pt);
			this.integerIndexer = indexer;
		}
		if (getter) {
			indexer.setGetter(method);
		} else {
			indexer.setSetter(method);
		}
	}

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
}
