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
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>JavaClassWrapperFactory class.</p>
 */
public final class JavaClassWrapperFactory {

	private static JavaClassWrapperFactory instance;

	private final Map<Class, WeakReference<JavaClassWrapper>> classWrappers = new WeakHashMap();

	private JavaClassWrapperFactory() { }

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.js.JavaClassWrapperFactory} object.
	 */
	public static JavaClassWrapperFactory getInstance() {
		if (instance == null) {
			synchronized (JavaClassWrapperFactory.class) {
				if (instance == null) {
					instance = new JavaClassWrapperFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * <p>getClassWrapper.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return a {@link org.loboevolution.js.JavaClassWrapper} object.
	 */
	public JavaClassWrapper getClassWrapper(Class clazz) {
		synchronized (this) {
			// WeakHashMaps where the value refers to
			// the key will retain keys. Must make it
			// refer to the value weakly too.
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
}
