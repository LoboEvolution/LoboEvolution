/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.js;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import org.loboevolution.html.node.Document;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

/**
 * <p>JavaScript class.</p>
 *
 *
 *
 */
public class JavaScript {
	private static final JavaScript instance = new JavaScript();

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.js.JavaScript} object.
	 */
	public static JavaScript getInstance() {
		return instance;
	}

	private static String getStringValue(Object object) {
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
	public Object getJavaObject(Object javascriptObject, Class<?> type) {
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
			if (javascriptObject instanceof Double) {
				return ((Double) javascriptObject).intValue();
			} else if (javascriptObject instanceof Integer) {
				return javascriptObject;
			} else if (javascriptObject instanceof String) {
				return Integer.valueOf((String) javascriptObject);
			} else if (javascriptObject instanceof Short) {
				return (int) ((Short) javascriptObject).shortValue();
			} else if (javascriptObject instanceof Long) {
				return ((Long) javascriptObject).intValue();
			} else if (javascriptObject instanceof Float) {
				return ((Float) javascriptObject).intValue();
			} else {
				return javascriptObject;
			}
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
	public Object getJavascriptObject(Object raw, Scriptable scope) {
		if (raw instanceof String || raw instanceof Scriptable) {
			return raw;
		} else if (raw == null) {
			return null;
		} else if (raw.getClass().isPrimitive()) {
			return raw;
		} else if (raw instanceof ScriptableDelegate) {
			// Classes that implement ScriptableDelegate retain
			// the JavaScript object. Reciprocal linking cannot
			// be done with weak hash maps and without leaking.
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

	public void defineElementClass(Scriptable scope, final Document document, final String jsClassName, final String elementName, Class<?> javaClass) {
		JavaInstantiator ji = () -> {
			Document d = document;
			if (d == null) {
				throw new IllegalStateException("Document not set in current context.");
			}
			return d.createElement(elementName);
		};
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = new JavaConstructorObject(jsClassName, classWrapper, ji);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}

	public void defineJsObject(Scriptable scope, final String jsClassName, Class<?> javaClass, JavaInstantiator instantiator) {
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = new JavaConstructorObject(jsClassName, classWrapper, instantiator);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}
	
	private boolean isBoxClass(Class clazz) {
        return clazz == Integer.class || clazz == Boolean.class || clazz == Double.class || clazz == Float.class
                || clazz == Long.class || clazz == Byte.class || clazz == Short.class || clazz == Character.class;
    }
}
