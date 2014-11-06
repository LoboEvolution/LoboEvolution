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
package org.lobobrowser.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaConstructorObject extends ScriptableObject implements Function {

	private static final long serialVersionUID = 1L;
	private final JavaClassWrapper classWrapper;
	private final JavaInstantiator instantiator;
	private final String name;

	public JavaConstructorObject(String name, JavaClassWrapper classWrapper) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = new SimpleInstantiator(classWrapper);
	}

	public JavaConstructorObject(String name, JavaClassWrapper classWrapper,
			JavaInstantiator instantiator) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = instantiator;
	}

	public String getClassName() {
		return this.name;
	}

	public Object call(Context cx, Scriptable scope, Scriptable thisObj,
			Object[] args) {
		throw new UnsupportedOperationException();
	}

	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		try {
			Object javaObject = this.instantiator.newInstance();
			Scriptable newObject = new JavaObjectWrapper(this.classWrapper,
					javaObject);
			newObject.setParentScope(scope);
			return newObject;
		} catch (Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	public java.lang.Object getDefaultValue(java.lang.Class hint) {
		if (String.class.equals(hint)) {
			return "function " + this.name;
		} else {
			return super.getDefaultValue(hint);
		}
	}

	public static class SimpleInstantiator implements JavaInstantiator {
		private final JavaClassWrapper classWrapper;

		public SimpleInstantiator(final JavaClassWrapper classWrapper) {
			super();
			this.classWrapper = classWrapper;
		}

		public Object newInstance() throws InstantiationException,
				IllegalAccessException {
			return this.classWrapper.newInstance();
		}
	}
}
