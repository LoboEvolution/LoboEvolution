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
 * Created on Jun 12, 2005
 */
package org.lobobrowser.store;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The Class CacheStoreInfo.
 *
 * @author J. H. S.
 */
public class CacheStoreInfo {

	/** The length. */
	private long length;

	/** The file infos. */
	private final Collection<CacheFileInfo> fileInfos = new ArrayList<CacheFileInfo>();

	/**
	 * Gets the file infos.
	 *
	 * @return the file infos
	 */
	public CacheFileInfo[] getFileInfos() {
		return this.fileInfos.toArray(new CacheFileInfo[0]);
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public long getLength() {
		return this.length;
	}

	/**
	 * Adds the cache file.
	 *
	 * @param file
	 *            the file
	 */
	public void addCacheFile(File file) {
		CacheFileInfo cfi = new CacheFileInfo(file);
		this.length += cfi.getInitialLength();
		this.fileInfos.add(cfi);
	}
}
