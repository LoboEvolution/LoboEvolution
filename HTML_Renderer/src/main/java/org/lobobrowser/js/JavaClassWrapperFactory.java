/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.js;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A factory for creating JavaClassWrapper objects.
 */
public class JavaClassWrapperFactory {

	/** The instance. */
	private static JavaClassWrapperFactory instance;

	/** The class wrappers. */
	private final Map<Class, WeakReference<JavaClassWrapper>> classWrappers = new WeakHashMap();

	/**
	 * Instantiates a new java class wrapper factory.
	 */
	private JavaClassWrapperFactory() {
	}

	/**
	 * Gets the instance.
	 *
	 * @return the instance
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
	 * Gets the class wrapper.
	 *
	 * @param clazz
	 *            the clazz
	 * @return the class wrapper
	 */
	public JavaClassWrapper getClassWrapper(Class clazz) {
		synchronized (this) {
			// WeakHashMaps where the value refers to
			// the key will retain keys. Must make it
			// refer to the value weakly too.
			WeakReference<JavaClassWrapper> jcwr = this.classWrappers.get(clazz);
			JavaClassWrapper jcw = null;
			if (jcwr != null) {
				jcw = jcwr.get();
			}
			if (jcw == null) {
				jcw = new JavaClassWrapper(clazz);
				this.classWrappers.put(clazz, new WeakReference<JavaClassWrapper>(jcw));
			}
			return jcw;
		}
	}
}
