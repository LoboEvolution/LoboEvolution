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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.info.PropertyInfo;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.lang.reflect.Method;

/**
 * <p>JavaObjectWrapper class.</p>
 */
@Slf4j
public class JavaObjectWrapper extends ScriptableObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The delegate. */
	private final Object delegate;

	/** The class wrapper. */
	private final JavaClassWrapper classWrapper;


	/**
	 * Instantiates a new java object wrapper.
	 *
	 * @param classWrapper the class wrapper
	 * @param delegate     the delegate
	 */
	public JavaObjectWrapper(final JavaClassWrapper classWrapper, final Object delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("Argument delegate cannot be null.");
		}
		this.classWrapper = classWrapper;
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


	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		return this.classWrapper.getClassName();
	}

	/** {@inheritDoc} */
	@Override
	public Object get(final int index, final Scriptable start) {
		final PropertyInfo pinfo = this.classWrapper.getIntegerIndexer();
		if (pinfo == null) {
			return super.get(index, start);
		} else {
			try {
				final Method getter = pinfo.getGetter();
				if (getter == null) {
					throw new EvaluatorException("Indexer is write-only");
				}
				// Cannot retain delegate with a strong reference.
				final Object javaObject = this.getJavaObject();
				if (javaObject == null) {
					throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
				}
				final Object raw = getter.invoke(javaObject, index);
				if (raw != null) {
					return JavaScript.getInstance().getJavascriptObject(raw, this.getParentScope());
				}
			} catch (final Exception err) {
				log.error(err.getMessage(), err);
			}
		}
		return Scriptable.NOT_FOUND;
	}

	/** {@inheritDoc} */
	@Override
	public Object get(final String name, final Scriptable start) {
		final PropertyInfo pinfo = this.classWrapper.getProperty(name);
		if (pinfo != null) {
			final Method getter = pinfo.getGetter();
			if (getter == null) {
				throw new EvaluatorException("Property '" + name + "' is not readable");
			}
			try {
				// Cannot retain delegate with a strong reference.
				final Object javaObject = this.getJavaObject();
				if (javaObject == null) {
					throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
				}
				final Object val = getter.invoke(javaObject, (Object[]) null);
				return JavaScript.getInstance().getJavascriptObject(val, start.getParentScope());
			} catch (final Exception err) {
				log.error(err.getMessage(), err);
				return new Object();
			}
		} else {
			final Function f = this.classWrapper.getFunction(name);
			if (f != null) {
				return f;
			} else {
				// Should check properties set in context
				// first. Consider element IDs should not
				// override Window variables set by user.
				final Object result = super.get(name, start);
				if (result != Scriptable.NOT_FOUND) {
					return result;
				}
				final PropertyInfo ni = this.classWrapper.getNameIndexer();
				if (ni != null) {
					final Method getter = ni.getGetter();
					if (getter != null) {
						// Cannot retain delegate with a strong reference.
						final Object javaObject = this.getJavaObject();
						if (javaObject == null) {
							throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
						}
						try {
							final Object val = getter.invoke(javaObject, name);
							if (val == null) {
								// There might not be an indexer setter.
								return super.get(name, start);
							} else {
								return JavaScript.getInstance().getJavascriptObject(val, start.getParentScope());
							}
						} catch (final Exception err) {
							log.error(err.getMessage(), err);
						}
					}
				}
				return Scriptable.NOT_FOUND;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(final int index, final Scriptable start, final Object value) {
		final PropertyInfo pinfo = this.classWrapper.getIntegerIndexer();
		if (pinfo == null) {
			super.put(index, start, value);
		} else {
			try {
				final Method setter = pinfo.getSetter();
				if (setter == null) {
					throw new EvaluatorException("Indexer is read-only");
				}
				final Object actualValue;
				actualValue = JavaScript.getInstance().getJavaObject(value, pinfo.getPropertyType());
				setter.invoke(this.getJavaObject(), index, actualValue);
			} catch (final Exception err) {
				log.error(err.getMessage(), err);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(final String name, final Scriptable start, final Object value) {
		if (value instanceof org.mozilla.javascript.Undefined) {
			super.put(name, start, value);
		} else {
			final PropertyInfo pinfo = this.classWrapper.getProperty(name);
			if (pinfo != null) {
				final Method setter = pinfo.getSetter();
				if (setter != null) {
					try {
						final Object actualValue;
						actualValue = JavaScript.getInstance().getJavaObject(value, pinfo.getPropertyType());
						setter.invoke(this.getJavaObject(), actualValue);
					} catch (final Exception err) {
						log.error(err.getMessage(), err);
					}
				}
			} else {
				final PropertyInfo ni = this.classWrapper.getNameIndexer();
				if (ni != null) {
					final Method setter = ni.getSetter();
					if (setter != null) {
						try {
							final Object actualValue;
							actualValue = JavaScript.getInstance().getJavaObject(value, ni.getPropertyType());
							setter.invoke(this.getJavaObject(), name, actualValue);
						} catch (final Exception err) {
							log.error(err.getMessage(), err);
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


	/** {@inheritDoc} */
	@Override
	public Object getDefaultValue(final Class hint) {
		if (hint == null || String.class.equals(hint)) {
			final Object javaObject = this.getJavaObject();
			if (javaObject == null) {
				throw new IllegalStateException("Java object (class=" + this.classWrapper + ") is null.");
			}
			return javaObject.toString();
		} else if (Number.class.isAssignableFrom(hint)) {
			final Object javaObject = this.getJavaObject();
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final Object javaObject = this.getJavaObject();
		final String type = javaObject == null ? "<null>" : javaObject.getClass().getName();
		return "JavaObjectWrapper[object=" + this.getJavaObject() + ",type=" + type + "]";
	}
}
