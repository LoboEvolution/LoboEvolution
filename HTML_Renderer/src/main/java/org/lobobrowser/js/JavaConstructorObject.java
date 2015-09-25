/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * The Class JavaConstructorObject.
 */
public class JavaConstructorObject extends ScriptableObject implements Function {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The class wrapper. */
	private final JavaClassWrapper classWrapper;

	/** The instantiator. */
	private final JavaInstantiator instantiator;

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new java constructor object.
	 *
	 * @param name
	 *            the name
	 * @param classWrapper
	 *            the class wrapper
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = new SimpleInstantiator(classWrapper);
	}

	/**
	 * Instantiates a new java constructor object.
	 *
	 * @param name
	 *            the name
	 * @param classWrapper
	 *            the class wrapper
	 * @param instantiator
	 *            the instantiator
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper, JavaInstantiator instantiator) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = instantiator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#getClassName()
	 */
	@Override
	public String getClassName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.Function#call(org.mozilla.javascript.Context,
	 * org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable,
	 * java.lang.Object[])
	 */
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.Function#construct(org.mozilla.javascript.Context,
	 * org.mozilla.javascript.Scriptable, java.lang.Object[])
	 */
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		try {
			Object javaObject = this.instantiator.newInstance();
			Scriptable newObject = new JavaObjectWrapper(this.classWrapper, javaObject);
			newObject.setParentScope(scope);
			return newObject;
		} catch (Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
	 */
	@Override
	public java.lang.Object getDefaultValue(Class hint) {
		if (String.class.equals(hint)) {
			return "function " + this.name;
		} else {
			return super.getDefaultValue(hint);
		}
	}

	/**
	 * The Class SimpleInstantiator.
	 */
	public static class SimpleInstantiator implements JavaInstantiator {

		/** The class wrapper. */
		private final JavaClassWrapper classWrapper;

		/**
		 * Instantiates a new simple instantiator.
		 *
		 * @param classWrapper
		 *            the class wrapper
		 */
		public SimpleInstantiator(final JavaClassWrapper classWrapper) {
			super();
			this.classWrapper = classWrapper;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.js.JavaInstantiator#newInstance()
		 */
		@Override
		public Object newInstance() throws InstantiationException, IllegalAccessException {
			return this.classWrapper.newInstance();
		}
	}
}
