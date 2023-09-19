/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <p>JavaConstructorObject class.</p>
 *
 *
 *
 */
public class JavaConstructorObject extends ScriptableObject implements Function {

	public static class SimpleInstantiator implements JavaInstantiator {
		private final JavaClassWrapper classWrapper;

		public SimpleInstantiator(final JavaClassWrapper classWrapper) {
			super();
			this.classWrapper = classWrapper;
		}

		@Override
		public Object newInstance() throws InstantiationException, IllegalAccessException {
			return this.classWrapper.newInstance();
		}
	}

	private static final long serialVersionUID = 1L;
	private final JavaClassWrapper classWrapper;
	private final JavaInstantiator instantiator;

	private final String name;

	/**
	 * <p>Constructor for JavaConstructorObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param classWrapper a {@link org.loboevolution.js.JavaClassWrapper} object.
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = new SimpleInstantiator(classWrapper);
	}

	/**
	 * <p>Constructor for JavaConstructorObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param classWrapper a {@link org.loboevolution.js.JavaClassWrapper} object.
	 * @param instantiator a {@link org.loboevolution.js.JavaInstantiator} object.
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper, JavaInstantiator instantiator) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = instantiator;
	}

	/** {@inheritDoc} */
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		try {
			final Object javaObject = this.instantiator.newInstance();
			final Scriptable newObject = new JavaObjectWrapper(this.classWrapper, javaObject);
			newObject.setParentScope(scope);
			return newObject;
		} catch (final Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public java.lang.Object getDefaultValue(java.lang.Class hint) {
		if (String.class.equals(hint)) {
			return "function " + this.name;
		} else {
			return super.getDefaultValue(hint);
		}
	}
}
