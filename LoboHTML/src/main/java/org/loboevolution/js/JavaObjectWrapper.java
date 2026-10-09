/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import java.io.Serial;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>JavaObjectWrapper class.</p>
 */
@Slf4j
public class JavaObjectWrapper extends ScriptableObject {

	/** The Constant serialVersionUID. */
	@Serial
	private static final long serialVersionUID = 1L;

	/** The delegate. */
	private final Object delegate;

	/** The class wrapper. */
	private final JavaClassWrapper classWrapper;

	/** The custom class name for JavaScript. */
	private final String customClassName;


	/**
	 * Instantiates a new java object wrapper.
	 *
	 * @param classWrapper the class wrapper
	 * @param delegate     the delegate
	 */
	public JavaObjectWrapper(final JavaClassWrapper classWrapper, final Object delegate) {
		this(classWrapper, delegate, null);
	}

	/**
	 * Instantiates a new java object wrapper with a custom class name.
	 *
	 * @param classWrapper the class wrapper
	 * @param delegate     the delegate
	 * @param customClassName the custom JavaScript class name
	 */
	public JavaObjectWrapper(final JavaClassWrapper classWrapper, final Object delegate, final String customClassName) {
		if (delegate == null) {
			throw new IllegalArgumentException("Argument delegate cannot be null.");
		}
		this.classWrapper = classWrapper;
		this.delegate = delegate;
		this.customClassName = customClassName;
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
		return this.customClassName != null ? this.customClassName : this.classWrapper.getClassName();
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
				if (name.indexOf('-') != -1) {
					final String camelName = kebabToCamel(name);
					final PropertyInfo camelProp = this.classWrapper.getProperty(camelName);
					if (camelProp != null) {
						final Method getter = camelProp.getGetter();
						if (getter != null) {
							try {
								final Object javaObject = this.getJavaObject();
								if (javaObject != null) {
									final Object val = getter.invoke(javaObject, (Object[]) null);
									return JavaScript.getInstance().getJavascriptObject(val, start.getParentScope());
								}
							} catch (final Exception err) {
								log.error(err.getMessage(), err);
							}
						}
					}
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
			PropertyInfo pinfo = this.classWrapper.getProperty(name);
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
				if (name.indexOf('-') != -1) {
					final String camelName = kebabToCamel(name);
					pinfo = this.classWrapper.getProperty(camelName);
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
						return;
					}
				}
				final PropertyInfo ni = this.classWrapper.getNameIndexer();
				if (ni != null) {
					final Method setter = ni.getSetter();
					if (setter != null) {
						final Object actualValue = JavaScript.getInstance().getJavaObject(value, ni.getPropertyType());
						final Class<?> paramType = setter.getParameterTypes()[1];
						if (actualValue == null || paramType.isInstance(actualValue)) {
							try {
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
				} else {
					super.put(name, start, value);
				}
			}
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean has(final int index, final Scriptable start) {
		if (this.classWrapper.getIntegerIndexer() != null) {
			final PropertyInfo lenProp = this.classWrapper.getProperty("length");
			if (lenProp != null) {
				final Method lenGetter = lenProp.getGetter();
				if (lenGetter != null) {
					try {
						final Object lenVal = lenGetter.invoke(this.getJavaObject());
						if (lenVal instanceof Number && index >= 0 && index < ((Number) lenVal).intValue()) {
							return true;
						}
					} catch (final Exception ignored) {
						// ignore
					}
				}
			}
		}
		return super.has(index, start);
	}

	/** {@inheritDoc} */
	@Override
	public boolean has(final String name, final Scriptable start) {
		if (this.classWrapper.getProperty(name) != null || this.classWrapper.getFunction(name) != null) {
			return true;
		}
		if (name.indexOf('-') != -1 && this.classWrapper.getProperty(kebabToCamel(name)) != null) {
			return true;
		}
		if (this.classWrapper.getNameIndexer() != null) {
			final Method getter = this.classWrapper.getNameIndexer().getGetter();
			if (getter != null) {
				try {
					final Object val = getter.invoke(this.getJavaObject(), name);
					if (val != null) {
						return true;
					}
				} catch (final Exception ignored) {
				}
			}
		}
		return super.has(name, start);
	}

	/** {@inheritDoc} */
	@Override
	public Object[] getIds() {
		final List<Object> ids = new ArrayList<>();
		for (final Object id : super.getIds()) {
			ids.add(id);
		}
		final PropertyInfo intIndexer = this.classWrapper.getIntegerIndexer();
		if (intIndexer != null) {
			final PropertyInfo lenProp = this.classWrapper.getProperty("length");
			if (lenProp != null) {
				final Method lenGetter = lenProp.getGetter();
				if (lenGetter != null) {
					try {
						final Object lenVal = lenGetter.invoke(this.getJavaObject());
						if (lenVal instanceof Number) {
							final int len = ((Number) lenVal).intValue();
							for (int i = 0; i < len; i++) {
								ids.add(i);
							}
						}
					} catch (final Exception ignored) {
						// ignore
					}
				}
			}
			ids.add("length");
			final Method getter = intIndexer.getGetter();
			if (getter != null) {
				ids.add(getter.getName());
			}
		}
		final PropertyInfo nameIndexer = this.classWrapper.getNameIndexer();
		if (nameIndexer != null) {
			final Method getter = nameIndexer.getGetter();
			if (getter != null) {
				ids.add(getter.getName());
			}
		}
		return ids.toArray();
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

	private static String kebabToCamel(final String name) {
		final StringBuilder sb = new StringBuilder(name.length());
		boolean nextUpper = false;
		for (int i = 0; i < name.length(); i++) {
			final char c = name.charAt(i);
			if (c == '-') {
				nextUpper = true;
			} else {
				sb.append(nextUpper ? Character.toUpperCase(c) : c);
				nextUpper = false;
			}
		}
		return sb.toString();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final Object javaObject = this.getJavaObject();
		final String type = javaObject == null ? "<null>" : javaObject.getClass().getName();
		return "JavaObjectWrapper[object=" + this.getJavaObject() + ",type=" + type + "]";
	}
}