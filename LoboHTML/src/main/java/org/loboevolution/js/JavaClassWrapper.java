/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.js;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.info.PropertyInfo;
import org.mozilla.javascript.Function;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>JavaClassWrapper class.</p>
 */
public class JavaClassWrapper {
	private final Map<String, JavaFunctionObject> functions = new HashMap<>();
	@Getter
	@Setter
	private PropertyInfo integerIndexer;
	private final Class javaClass;
	@Getter
	@Setter
	private PropertyInfo nameIndexer;
	private final Map<String, PropertyInfo> properties = new HashMap<>();

	/**
	 * <p>Constructor for JavaClassWrapper.</p>
	 *
	 * @param class1 a {@link java.lang.Class} object.
	 */
	public JavaClassWrapper(final Class class1) {
		this.javaClass = class1;
		scanMethods();
	}

	private void ensurePropertyKnown(final String methodName, final Method method) {
		final String capPropertyName;
		final String propertyName;
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
	public Function getFunction(final String name) {
		return this.functions.get(name);
	}

	/**
	 * <p>getProperty.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.info.PropertyInfo} object.
	 */
	public PropertyInfo getProperty(final String name) {
		return this.properties.get(name);
	}

	private boolean isIntegerIndexer(final String name, final Method method) {
		return "item".equals(name) && method.getParameterTypes().length == 1
				|| "setItem".equals(name) && method.getParameterTypes().length == 2;
	}

	private boolean isNameIndexer(final String name, final Method method) {
		return "namedItem".equals(name) && method.getParameterTypes().length == 1
				|| "setNamedItem".equals(name) && method.getParameterTypes().length == 2;
	}

	private boolean isPropertyMethod(final String name, final Method method) {
		if ((name.startsWith("get") || name.startsWith("is")) &&
				!name.equals("getBoundingClientRect") &&
				!name.equals("getClientRects") &&
				!name.equals("getComputedStyle") &&
				!name.equals("getPropertyCSSValue") &&
				!name.equals("getSelection")) {
			return method.getParameterTypes().length == 0;
		} else if (name.startsWith("set") &&
				!name.equals("setTimeout") &&
				!name.equals("setNamedItem") &&
				!name.equals("setProperty") &&
				!name.equals("setAttributeNode") &&
				!name.equals("setStartAfter") &&
				!name.equals("setStart")) {
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

	private String propertyUncapitalize(final String text) {
		try {
			if ("NodeFilter".equals(text) || "Node".equals(text) ||
					(text.length() > 1 && Character.isUpperCase(text.charAt(1)))) {
				return text;
			}
			return Character.toLowerCase(text.charAt(0)) + text.substring(1);
		} catch (final IndexOutOfBoundsException iob) {
			return text;
		}
	}

	private void scanMethods() {
		final List<Method> methods = Arrays.asList(this.javaClass.getMethods());
		methods.forEach(method -> {
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
					f = new JavaFunctionObject(name, javaClass.getName());
					this.functions.put(name, f);
				}
				f.addMethod(method);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.javaClass.getName();
	}

	private void updateIntegerIndexer(final String methodName, final Method method) {
		boolean getter = !methodName.startsWith("set");
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

	private void updateNameIndexer(final String methodName, final Method method) {
		boolean getter = !methodName.startsWith("set");
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
