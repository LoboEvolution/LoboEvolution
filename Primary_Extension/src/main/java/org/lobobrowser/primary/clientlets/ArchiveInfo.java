/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
/*
 * Created on Jun 22, 2005
 */
package org.lobobrowser.primary.clientlets;

import java.io.IOException;
import java.net.URL;

/**
 * The Class ArchiveInfo.
 */
public class ArchiveInfo {

	/** The Constant EMPTY_ARRAY. */
	public static final ArchiveInfo[] EMPTY_ARRAY = new ArchiveInfo[0];

	/** The file. */
	public final java.io.File file;

	/** The url. */
	public final URL url;

	/**
	 * Instantiates a new archive info.
	 *
	 * @param url
	 *            the url
	 * @param file
	 *            the file
	 */
	public ArchiveInfo(final URL url, final java.io.File file) {
		super();
		this.url = url;
		this.file = file;
	}

	/** The jar file. */
	private java.util.jar.JarFile jarFile;

	/**
	 * Gets the jar file.
	 *
	 * @return the jar file
	 */
	public java.util.jar.JarFile getJarFile() throws IOException {
		if (this.jarFile == null) {
			synchronized (this) {
				if (this.jarFile == null) {
					this.jarFile = new java.util.jar.JarFile(this.file);
				}
			}
		}
		return this.jarFile;
	}

	// private transient WarriorClassLoader classLoader = null;
	//
	// public WarriorClassLoader getClassLoader() throws IOException {
	// synchronized(this) {
	// if(this.classLoader == null) {
	// this.classLoader = new ArchiveClassLoader(this.url, this.file);
	// }
	// return this.classLoader;
	// }
	// }
}
