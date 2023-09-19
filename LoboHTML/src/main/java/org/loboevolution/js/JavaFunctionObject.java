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

import org.mozilla.javascript.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>JavaFunctionObject class.</p>
 */
public class JavaFunctionObject extends ScriptableObject implements Function {

	private static final long serialVersionUID = 1L;
	private final String className;
	private final String methodName;
	private final List<Method> methods = new ArrayList<>();

	/**
	 * <p>Constructor for JavaFunctionObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param className a {@link java.lang.String} object.
	 */
	public JavaFunctionObject(final String name, final String className) {
		this.methodName = name;
		this.className = className;
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
			throw new EvaluatorException("No method matching " + this.methodName + " with " + (args == null ? 0 : args.length) + " arguments in "
					+ className + " .");
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
			throw new IllegalStateException("Unable to call " + this.methodName + ".", iae);
		} catch (final InvocationTargetException ite) {
			throw new WrappedException(new InvocationTargetException(ite.getCause(),
					"Unable to call " + this.methodName + " on " + jcw.getJavaObject() + "."));
		} catch (final IllegalArgumentException iae) {
			final StringBuilder argTypes = new StringBuilder();
			for (int i = 0; i < actualArgs.length; i++) {
				if (i > 0) {
					argTypes.append(", ");
				}
				argTypes.append(actualArgs[i] == null ? "<null>" : actualArgs[i].getClass().getName());
			}
			throw new WrappedException(new IllegalArgumentException(
					"Unable to call " + this.methodName + ". Argument types: " + argTypes + ".", iae));
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
		for (final Method m : methods) {
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
	public Object getDefaultValue(Class hint) {
		if (hint == null || String.class.equals(hint)) {
			return "function " + this.methodName;
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
