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
/*
 * Created on Jun 12, 2005
 */
package org.lobobrowser.store;

import java.io.File;
import java.util.Date;

/**
 * The Class CacheFileInfo.
 *
 * @author J. H. S.
 */
public class CacheFileInfo implements Comparable<Object> {

	/** The size. */
	private final long size;

	/** The path. */
	private final String path;

	/** The file. */
	private final File file;

	/** The initial last modified. */
	private final long initialLastModified;

	/**
	 * Instantiates a new cache file info.
	 *
	 * @param file
	 *            the file
	 */
	public CacheFileInfo(File file) {
		super();
		this.path = file.getAbsolutePath();
		this.size = file.length();
		this.file = file;
		this.initialLastModified = file.lastModified();
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(Object arg0) {
		// Tested 2/3/2008.
		// Yield on compare for potentially huge sort.
		Thread.yield();
		CacheFileInfo cfi1 = this;
		CacheFileInfo cfi2 = (CacheFileInfo) arg0;
		int fileCompare = cfi1.file.compareTo(cfi2.file);
		if (fileCompare == 0) {
			return 0;
		}
		long lm1 = cfi1.initialLastModified;
		long lm2 = cfi2.initialLastModified;
		if (lm1 < lm2) {
			return -1;
		} else if (lm1 > lm2) {
			return +1;
		} else {
			return fileCompare;
		}
	}

	/**
	 * Delete.
	 */
	public void delete() {
		new File(this.path).delete();
	}

	/**
	 * Gets the last modified.
	 *
	 * @return the last modified
	 */
	public long getLastModified() {
		return this.file.lastModified();
	}

	/**
	 * Gets the initial length.
	 *
	 * @return the initial length
	 */
	public long getInitialLength() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CacheFileInfo[path=" + this.path + ",lastModified=" + new Date(this.initialLastModified)
				+ "]";
	}
}
