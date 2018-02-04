/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.js;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.util.Objects;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * The Class JavaFunctionObject.
 */
public class JavaFunctionObject extends ScriptableObject implements Function {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(JavaFunctionObject.class.getName());

	/** The Constant loggableInfo. */
	private static final boolean loggableInfo = logger.isEnabled(Level.INFO);

	/** The class name. */
	private final String className;

	/** The methods. */
	private final ArrayList<Method> methods = new ArrayList<Method>();

	/**
	 * Instantiates a new java function object.
	 *
	 * @param name
	 *            the name
	 */
	public JavaFunctionObject(String name) {
		super();
		this.className = name;
	}

	/**
	 * Adds the method.
	 *
	 * @param m
	 *            the m
	 */
	public void addMethod(Method m) {
		this.methods.add(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#getClassName()
	 */
	@Override
	public String getClassName() {
		return this.className;
	}

	/**
	 * Gets the type name.
	 *
	 * @param object
	 *            the object
	 * @return the type name
	 */
	private String getTypeName(Object object) {
		return object == null ? "[null]" : object.getClass().getName();
	}

	/**
	 * Gets the best method.
	 *
	 * @param args
	 *            the args
	 * @return the best method
	 */
	private Method getBestMethod(Object[] args) {
		ArrayList<Method> methods = this.methods;
		int size = methods.size();
		int matchingNumParams = 0;
		Method matchingMethod = null;
		for (int i = 0; i < size; i++) {
			Method m = methods.get(i);
			Class[] parameterTypes = m.getParameterTypes();
			if (args == null) {
				if (parameterTypes == null || parameterTypes.length == 0) {
					return m;
				}
			} else if (parameterTypes != null && args.length >= parameterTypes.length) {
				if (Objects.areAssignableTo(args, parameterTypes)) {
					return m;
				}
				if (matchingMethod == null || parameterTypes.length > matchingNumParams) {
					matchingNumParams = parameterTypes.length;
					matchingMethod = m;
				}
			}
		}
		if (size == 0) {
			throw new IllegalStateException("zero methods");
		}
		return matchingMethod;
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
		JavaObjectWrapper jcw = (JavaObjectWrapper) thisObj;
		Method method = this.getBestMethod(args);
		if (method == null) {
			throw new EvaluatorException("No method matching " + this.className + " with "
					+ (args == null ? 0 : args.length) + " arguments.");
		}
		Class[] actualArgTypes = method.getParameterTypes();
		int numParams = actualArgTypes.length;
		Object[] actualArgs = args == null ? new Object[0] : new Object[numParams];
		
		JavaScript manager = JavaScript.getInstance();
		for (int i = 0; i < numParams; i++) {
			Object arg = args[i];
			Object actualArg = manager.getJavaObject(arg, actualArgTypes[i]);
			actualArgs[i] = actualArg;
		}
		try {
			Object raw = method.invoke(jcw.getJavaObject(), actualArgs);
			return manager.getJavascriptObject(raw, scope);
		} catch (IllegalAccessException iae) {
			logger.error("Unable to call " + this.className + ".");
		} catch (InvocationTargetException ite) {
			logger.error("Unable to call " + this.className + " on " + jcw.getJavaObject() + ".");
		} catch (IllegalArgumentException iae) {
			StringBuilder argTypes = new StringBuilder();
			for (int i = 0; i < actualArgs.length; i++) {
				if (i > 0) {
					argTypes.append(", ");
				}
				argTypes.append(actualArgs[i] == null ? "<null>" : actualArgs[i].getClass().getName());
			}
			logger.error("Unable to call " + this.className + ". Argument types: " + argTypes + ".");
		}
		return manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
	 */
	@Override
	public Object getDefaultValue(Class hint) {
		if (loggableInfo) {
			logger.info("getDefaultValue(): hint=" + hint + ",this=" + this);
		}
		if (hint == null || String.class.equals(hint)) {
			return "function " + this.className;
		} else {
			return super.getDefaultValue(hint);
		}
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
		throw new UnsupportedOperationException();
	}
}
