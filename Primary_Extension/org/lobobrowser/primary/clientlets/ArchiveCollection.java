/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 23, 2005
 */
package org.lobobrowser.primary.clientlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * The Class ArchiveCollection.
 *
 * @author J. H. S.
 */
public class ArchiveCollection {
	
	/** The archive infos. */
	private final Collection<Object[]> archiveInfos;

	/**
	 * Instantiates a new archive collection.
	 */
	public ArchiveCollection() {
		this.archiveInfos = new LinkedList<Object[]>();
	}

	/**
	 * Instantiates a new archive collection.
	 *
	 * @param archiveInfos the archive infos
	 */
	public ArchiveCollection(Collection<Object[]> archiveInfos) {
		this.archiveInfos = archiveInfos;
	}

	// public void addArchiveInfo(ArchiveInfo ainfo) {
	// synchronized(this) {
	// this.archiveInfos.add(ainfo);
	// }
	// }

	/** The class loader. */
	private ArchiveClassLoader classLoader;

	/**
	 * Gets the class loader.
	 *
	 * @return the class loader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ClassLoader getClassLoader() throws IOException {
		synchronized (this) {
			if (this.archiveInfos.size() == 0) {
				return this.getClass().getClassLoader();
			}
			if (this.classLoader == null) {
				this.classLoader = new ArchiveClassLoader(this.archiveInfos);
			}
			return this.classLoader;
		}
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public Iterator<Object[]> iterator() {
		return this.archiveInfos.iterator();
	}
}
