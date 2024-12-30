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
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Document;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * <p>JavaScript class.</p>
 */
public class JavaScript {

	@Getter
	private static final JavaScript instance = new JavaScript();

	private static String getStringValue(final Object object) {
		if (object instanceof Undefined) {
			return "undefined";
		} else if (object instanceof Scriptable) {
			return (String) ((Scriptable) object).getDefaultValue(String.class);
		} else {
			return String.valueOf(object);
		}
	}

	// objectMap must be a map that uses weak keys
	// and refers to values using weak references.
	// Keys are java objects other than ScriptableDelegate instances.
	private final WeakHashMap<Object, WeakReference<JavaObjectWrapper>> javaObjectToWrapper = new WeakHashMap<>();

	/**
	 * <p>getJavaObject.</p>
	 *
	 * @param javascriptObject a {@link java.lang.Object} object.
	 * @param type a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getJavaObject(final Object javascriptObject, final Class<?> type) {
		if (javascriptObject instanceof JavaObjectWrapper) {
			final Object rawJavaObject = ((JavaObjectWrapper) javascriptObject).getJavaObject();
			if (String.class == type) {
				return String.valueOf(rawJavaObject);
			} else {
				return rawJavaObject;
			}
		} else if (javascriptObject == null) {
			return null;
		} else if (type == String.class) {
			if (javascriptObject instanceof String) {
				return javascriptObject;
			} else if (javascriptObject instanceof Double) {
				final String text = String.valueOf(javascriptObject);
				if (text.endsWith(".0")) {
					return text.substring(0, text.length() - 2);
				} else {
					return text;
				}
			} else {
				return getStringValue(javascriptObject);
			}
		} else if (type == int.class || type == Integer.class) {
            return switch (javascriptObject) {
                case Double v -> v.intValue();
                case String s -> Strings.isNumeric(s) ? Float.valueOf(s) : null;
                case Short i -> (int) i;
                case Long l -> l.intValue();
                case Float v -> v.intValue();
                default -> javascriptObject;
            };
		} else {
			return javascriptObject;
		}
	}

	/**
	 * Returns an object that may be used by the Javascript engine.
	 *
	 * @param raw a {@link java.lang.Object} object.
	 * @param scope a {@link org.mozilla.javascript.Scriptable} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getJavascriptObject(final Object raw, final Scriptable scope) {
		if (raw == null) {
			return null;
		} else if (raw instanceof String || raw instanceof Scriptable) {
			return raw;
		} else if (raw.getClass().isPrimitive()) {
			return raw;
		} else if (raw instanceof ScriptableDelegate) {
			synchronized (this) {
				Scriptable javascriptObject = ((ScriptableDelegate) raw).getScriptable();
				if (javascriptObject == null) {
					final JavaObjectWrapper jow = new JavaObjectWrapper(
							JavaClassWrapperFactory.getInstance().getClassWrapper(raw.getClass()), raw);
					javascriptObject = jow;
					jow.setParentScope(scope);
					((ScriptableDelegate) raw).setScriptable(jow);
				}
				javascriptObject.setParentScope(scope);
				return javascriptObject;
			}
		} else if (isBoxClass(raw.getClass())) {
			return raw;
		} else {
			synchronized (this.javaObjectToWrapper) {
				// WeakHashMaps will retain keys if the value refers to the key.
				// That's why we need to refer to the value weakly too.
				final WeakReference<?> valueRef = this.javaObjectToWrapper.get(raw);
				JavaObjectWrapper jow = null;
				if (valueRef != null) {
					jow = (JavaObjectWrapper) valueRef.get();
				}
				if (jow == null) {
					final Class<? extends Object> javaClass = raw.getClass();
					final JavaClassWrapper wrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
					jow = new JavaObjectWrapper(wrapper, raw);
					this.javaObjectToWrapper.put(raw, new WeakReference<>(jow));
				}
				jow.setParentScope(scope);
				return jow;
			}
		}
	}

	public void defineElementClass(final Scriptable scope, final Document document, final String jsClassName, final String elementName, final Class<?> javaClass) {
		final JavaInstantiator ji = (final Object[] args) -> {
			final Document d = document;
			if (d == null) {
				throw new IllegalStateException("Document not set in current context.");
			}
			return d.createElement(elementName);
		};
		final JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		final Function constructorFunction = new JavaConstructorObject(jsClassName, classWrapper, ji);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}

	public void defineJsObject(final Scriptable scope, final String jsClassName, final Class<?> javaClass, final JavaInstantiator instantiator) {
		final JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		final Function constructorFunction = new JavaConstructorObject(jsClassName, classWrapper, instantiator);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}
	
	private boolean isBoxClass(final Class clazz) {
        return clazz == Integer.class || clazz == Boolean.class || clazz == Double.class || clazz == Float.class
                || clazz == Long.class || clazz == Byte.class || clazz == Short.class || clazz == Character.class;
    }
}
