/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.main;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Global URL stream handler factory used by the browser.
 *
 * @see PlatformInit#initProtocols()
 */
public class PlatformStreamHandlerFactory implements URLStreamHandlerFactory {

	/** The Constant instance. */
	private static final PlatformStreamHandlerFactory instance = new PlatformStreamHandlerFactory();

	/** The factories. */
	private final Collection<URLStreamHandlerFactory> factories = new ArrayList<URLStreamHandlerFactory>();

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static PlatformStreamHandlerFactory getInstance() {
		return instance;
	}

	/**
	 * Adds the factory.
	 *
	 * @param factory
	 *            the factory
	 */
	public void addFactory(URLStreamHandlerFactory factory) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkSetFactory();
		}
		Collection<URLStreamHandlerFactory> factories = this.factories;
		synchronized (factories) {
			factories.add(factory);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.net.URLStreamHandlerFactory#createURLStreamHandler(java.lang.String)
	 */
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		Collection<URLStreamHandlerFactory> factories = this.factories;
		synchronized (factories) {
			for (URLStreamHandlerFactory f : factories) {
				URLStreamHandler handler = f.createURLStreamHandler(protocol);
				if (handler != null) {
					return handler;
				}
			}
		}
		return null;
	}
}
