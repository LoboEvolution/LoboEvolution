/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.js;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.info.PropertyInfo;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * The Class JavaObjectWrapper.
 */
public class JavaObjectWrapper extends ScriptableObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(JavaObjectWrapper.class.getName());

	/** The Constant loggableInfo. */
	private static final boolean loggableInfo = logger.isLoggable(Level.INFO);

	/** The delegate. */
	private final Object delegate;

	/** The class wrapper. */
	private final JavaClassWrapper classWrapper;

	/**
	 * Instantiates a new java object wrapper.
	 *
	 * @param classWrapper
	 *            the class wrapper
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	public JavaObjectWrapper(JavaClassWrapper classWrapper) throws InstantiationException, IllegalAccessException {
		this.classWrapper = classWrapper;
		// Retaining a strong reference, but note
		// that the object wrapper map uses weak keys
		// and weak values.
		Object delegate = this.classWrapper.newInstance();
		this.delegate = delegate;
	}

	/**
	 * Instantiates a new java object wrapper.
	 *
	 * @param classWrapper
	 *            the class wrapper
	 * @param delegate
	 *            the delegate
	 */
	public JavaObjectWrapper(JavaClassWrapper classWrapper, Object delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("Argument delegate cannot be null.");
		}
		this.classWrapper = classWrapper;
		// Retaining a strong reference, but note
		// that the object wrapper map uses weak keys
		// and weak values.
		this.delegate = delegate;
	}

	/**
	 * Gets the java object.
	 *
	 * @return the java object
	 */
	public Object getJavaObject() {
		// Cannot retain delegate with a strong reference.
		return this.delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#getClassName()
	 */
	@Override
	public String getClassName() {
		return this.classWrapper.getClassName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#get(int,
	 * org.mozilla.javascript.Scriptable)
	 */
	@Override
	public Object get(int index, Scriptable start) {
		PropertyInfo pinfo = this.classWrapper.getIntegerIndexer();
		if (pinfo == null) {
			return super.get(index, start);
		} else {
			try {
				Method getter = pinfo.getGetter();
				if (getter == null) {
					throw new EvaluatorException("Indexer is write-only");
				}
				// Cannot retain delegate with a strong reference.
				Object javaObject = this.getJavaObject();
				if (javaObject == null) {
					throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
				}
				Object raw = getter.invoke(javaObject, new Object[] { new Integer(index) });
				if (raw != null) {
					return JavaScript.getInstance().getJavascriptObject(raw, this.getParentScope());
				}
			} catch (Exception err) {
				err.getCause();
			}
		}
		return Scriptable.NOT_FOUND;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#get(java.lang.String,
	 * org.mozilla.javascript.Scriptable)
	 */
	@Override
	public Object get(String name, Scriptable start) {
		PropertyInfo pinfo = this.classWrapper.getProperty(name);
		if (pinfo != null) {
			Method getter = pinfo.getGetter();
			if (getter == null) {
				throw new EvaluatorException("Property '" + name + "' is not readable");
			}
			try {
				// Cannot retain delegate with a strong reference.
				Object javaObject = this.getJavaObject();
				if (javaObject == null) {
					throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
				}
				Object val = getter.invoke(javaObject, (Object[]) null);
				return JavaScript.getInstance().getJavascriptObject(val, start.getParentScope());
			} catch (Exception err) {
				err.getCause();
				return new Object();
			}
		} else {
			Function f = this.classWrapper.getFunction(name);
			if (f != null) {
				return f;
			} else {
				// Should check properties set in context
				// first. Consider element IDs should not
				// override Window variables set by user.
				Object result = super.get(name, start);
				if (result != Scriptable.NOT_FOUND) {
					return result;
				}
				PropertyInfo ni = this.classWrapper.getNameIndexer();
				if (ni != null) {
					Method getter = ni.getGetter();
					if (getter != null) {
						// Cannot retain delegate with a strong reference.
						Object javaObject = this.getJavaObject();
						if (javaObject == null) {
							throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
						}
						try {
							Object val = getter.invoke(javaObject, new Object[] { name });
							if (val == null) {
								// There might not be an indexer setter.
								return super.get(name, start);
							} else {
								return JavaScript.getInstance().getJavascriptObject(val, start.getParentScope());
							}
						} catch (Exception err) {
							err.getCause();
						}
					}
				}
				return Scriptable.NOT_FOUND;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#put(int,
	 * org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	@Override
	public void put(int index, Scriptable start, Object value) {
		PropertyInfo pinfo = this.classWrapper.getIntegerIndexer();
		if (pinfo == null) {
			super.put(index, start, value);
		} else {
			try {
				Method setter = pinfo.getSetter();
				if (setter == null) {
					throw new EvaluatorException("Indexer is read-only");
				}
				Object actualValue;
				actualValue = JavaScript.getInstance().getJavaObject(value, pinfo.getPropertyType());
				setter.invoke(this.getJavaObject(), new Object[] { new Integer(index), actualValue });
			} catch (Exception err) {
				err.getCause();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.ScriptableObject#put(java.lang.String,
	 * org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	@Override
	public void put(String name, Scriptable start, Object value) {
		if (value instanceof org.mozilla.javascript.Undefined) {
			super.put(name, start, value);
		} else {
			PropertyInfo pinfo = this.classWrapper.getProperty(name);
			if (pinfo != null) {
				Method setter = pinfo.getSetter();
				if (setter == null) {
					throw new EvaluatorException(
							"Property '" + name + "' is not settable in " + this.classWrapper.getClassName() + ".");
				}
				try {
					Object actualValue;
					actualValue = JavaScript.getInstance().getJavaObject(value, pinfo.getPropertyType());
					setter.invoke(this.getJavaObject(), new Object[] { actualValue });
				} catch (IllegalArgumentException iae) {
					iae.getCause();
				} catch (Exception err) {
					err.getCause();
				}
			} else {
				PropertyInfo ni = this.classWrapper.getNameIndexer();
				if (ni != null) {
					Method setter = ni.getSetter();
					if (setter != null) {
						try {
							Object actualValue;
							actualValue = JavaScript.getInstance().getJavaObject(value, ni.getPropertyType());
							setter.invoke(this.getJavaObject(), new Object[] { name, actualValue });
						} catch (Exception err) {
							err.getCause();
						}
					} else {
						super.put(name, start, value);
					}
				} else {
					super.put(name, start, value);
				}
			}
		}
	}

	/**
	 * Gets the constructor.
	 *
	 * @param className
	 *            the class name
	 * @param classWrapper
	 *            the class wrapper
	 * @param scope
	 *            the scope
	 * @return the constructor
	 */
	public static Function getConstructor(String className, JavaClassWrapper classWrapper, Scriptable scope) {
		return new JavaConstructorObject(className, classWrapper);
	}

	/**
	 * Gets the constructor.
	 *
	 * @param className
	 *            the class name
	 * @param classWrapper
	 *            the class wrapper
	 * @param scope
	 *            the scope
	 * @param instantiator
	 *            the instantiator
	 * @return the constructor
	 */
	public static Function getConstructor(String className, JavaClassWrapper classWrapper, Scriptable scope,
			JavaInstantiator instantiator) {
		return new JavaConstructorObject(className, classWrapper, instantiator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
	 */
	@Override
	public java.lang.Object getDefaultValue(Class hint) {
		if (loggableInfo) {
			logger.info("getDefaultValue(): hint=" + hint + ",this=" + this.getJavaObject());
		}
		if ((hint == null) || String.class.equals(hint)) {
			Object javaObject = this.getJavaObject();
			if (javaObject == null) {
				throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
			}
			return javaObject.toString();
		} else if (Number.class.isAssignableFrom(hint)) {
			Object javaObject = this.getJavaObject();
			if (javaObject instanceof Number) {
				return javaObject;
			} else if (javaObject instanceof String) {
				return Double.valueOf((String) javaObject);
			} else {
				return super.getDefaultValue(hint);
			}
		} else {
			return super.getDefaultValue(hint);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Object javaObject = this.getJavaObject();
		String type = javaObject == null ? "<null>" : javaObject.getClass().getName();
		return "JavaObjectWrapper[object=" + this.getJavaObject() + ",type=" + type + "]";
	}
}
