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
/*
 * Created on Jun 22, 2005
 */
package org.lobobrowser.primary.clientlets;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * The Class ArchiveInfo.
 */
public class ArchiveInfo {

	/** The Constant EMPTY_ARRAY. */
	public static final ArchiveInfo[] EMPTY_ARRAY = new ArchiveInfo[0];

	/** The file. */
	public final File file;

	/** The url. */
	public final URL url;
	
	/** The jar file. */
	private JarFile jarFile;

	/**
	 * Instantiates a new archive info.
	 *
	 * @param url
	 *            the url
	 * @param file
	 *            the file
	 */
	public ArchiveInfo(final URL url, final File file) {
		super();
		this.url = url;
		this.file = file;
	}

	/**
	 * Gets the jar file.
	 *
	 * @return the jar file
	 */
	public JarFile getJarFile() throws IOException {
		if (this.jarFile == null) {
			synchronized (this) {
				if (this.jarFile == null) {
					this.jarFile = new JarFile(this.file);
				}
			}
		}
		return this.jarFile;
	}
}
