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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;

/**
 * <p>JavaFunctionObject class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class JavaFunctionObject extends ScriptableObject implements Function {

	private static final long serialVersionUID = 1L;
	private final String className;
	private final List<Method> methods = new ArrayList<Method>();

	/**
	 * <p>Constructor for JavaFunctionObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public JavaFunctionObject(String name) {
		super();
		this.className = name;
	}

	/**
	 * <p>addMethod.</p>
	 *
	 * @param m a {@link java.lang.reflect.Method} object.
	 */
	public void addMethod(Method m) {
		this.methods.add(m);
	}

	/** {@inheritDoc} */
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		final JavaObjectWrapper jcw = (JavaObjectWrapper) thisObj;
		final Method method = getBestMethod(args);
		if (method == null) {
			throw new EvaluatorException("No method matching " + this.className + " with "
					+ (args == null ? 0 : args.length) + " arguments.");
		}
		final Class[] actualArgTypes = method.getParameterTypes();
		final int numParams = actualArgTypes.length;
		final Object[] actualArgs = args == null ? new Object[0] : new Object[numParams];

		final JavaScript manager = JavaScript.getInstance();
		for (int i = 0; i < numParams; i++) {
			final Object arg = args[i];
			final Object actualArg = manager.getJavaObject(arg, actualArgTypes[i]);
			actualArgs[i] = actualArg;
		}
		try {
			final Object raw = method.invoke(jcw.getJavaObject(), actualArgs);
			return manager.getJavascriptObject(raw, scope);
		} catch (final IllegalAccessException iae) {
			throw new IllegalStateException("Unable to call " + this.className + ".", iae);
		} catch (final InvocationTargetException ite) {
			throw new WrappedException(new InvocationTargetException(ite.getCause(),
					"Unable to call " + this.className + " on " + jcw.getJavaObject() + "."));
		} catch (final IllegalArgumentException iae) {
			final StringBuilder argTypes = new StringBuilder();
			for (int i = 0; i < actualArgs.length; i++) {
				if (i > 0) {
					argTypes.append(", ");
				}
				argTypes.append(actualArgs[i] == null ? "<null>" : actualArgs[i].getClass().getName());
			}
			throw new WrappedException(new IllegalArgumentException(
					"Unable to call " + this.className + ". Argument types: " + argTypes + ".", iae));
		}
	}

	/** {@inheritDoc} */
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		throw new UnsupportedOperationException();
	}

	private Method getBestMethod(Object[] args) {
		final List<Method> methods = this.methods;
		final int size = methods.size();
		int matchingNumParams = 0;
		Method matchingMethod = null;
		for (int i = 0; i < size; i++) {
			final Method m = methods.get(i);
			final Class[] parameterTypes = m.getParameterTypes();
			if (args == null) {
				if (parameterTypes == null || parameterTypes.length == 0) {
					return m;
				}
			} else if (parameterTypes != null && args.length >= parameterTypes.length) {
				if (areAssignableTo(args, parameterTypes)) {
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

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		return this.className;
	}

	/** {@inheritDoc} */
	@Override
	public java.lang.Object getDefaultValue(java.lang.Class hint) {
		if (hint == null || String.class.equals(hint)) {
			return "function " + this.className;
		} else {
			return super.getDefaultValue(hint);
		}
	}
	
	private boolean areAssignableTo(Object[] objects, Class[] types) {
        final int length = objects.length;
        if (length != types.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!isAssignableOrBox(objects[i], types[i])) {
                return false;
            }
        }
        return true;
    }
	
	private boolean isAssignableOrBox(Object value, Class clazz) {
        if (clazz.isInstance(value)) {
            return true;
        }
        if (clazz.isPrimitive()) {
            if (clazz == double.class && value instanceof Double || clazz == int.class && value instanceof Integer
                    || clazz == long.class && value instanceof Long
                    || clazz == boolean.class && value instanceof Boolean
                    || clazz == byte.class && value instanceof Byte || clazz == char.class && value instanceof Character
                    || clazz == short.class && value instanceof Short
                    || clazz == float.class && value instanceof Float) {
                return true;
            }
        }

        if (clazz.isAssignableFrom(String.class)) {
            return value == null || !value.getClass().isPrimitive();
        }
        return false;
    }
}
