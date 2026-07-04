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

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>JavaClassWrapperFactory class.</p>
 */
public final class JavaClassWrapperFactory {

    private final Map<Class<?>, WeakReference<JavaClassWrapper>> classWrappers = new WeakHashMap<>();

	private final Map<Class<?>, String> customClassNames = new WeakHashMap<>();


	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.js.JavaClassWrapperFactory} object.
	 */
	public static JavaClassWrapperFactory getInstance() {
        return InstanceHolder.instance;
	}

	/**
	 * <p>getClassWrapper.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return a {@link org.loboevolution.js.JavaClassWrapper} object.
	 */
	public JavaClassWrapper getClassWrapper(final Class<?> clazz) {
		synchronized (this) {
			final WeakReference<JavaClassWrapper> jcwr = this.classWrappers.get(clazz);
			JavaClassWrapper jcw = null;
			if (jcwr != null) {
				jcw = jcwr.get();
			}
			if (jcw == null) {
				jcw = new JavaClassWrapper(clazz);
				this.classWrappers.put(clazz, new WeakReference<>(jcw));
			}
			return jcw;
		}
	}

	/**
	 * <p>getCustomClassName.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return the custom class name for JavaScript, or null if not set.
	 */
	public String getCustomClassName(final Class<?> clazz) {
		return this.customClassNames.get(clazz);
	}

	/**
	 * <p>registerCustomClassName.</p>
	 * Registers a custom JavaScript class name for a Java class.
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param customClassName the custom JavaScript class name.
	 */
	public void registerCustomClassName(final Class<?> clazz, final String customClassName) {
		this.customClassNames.put(clazz, customClassName);
	}
}
