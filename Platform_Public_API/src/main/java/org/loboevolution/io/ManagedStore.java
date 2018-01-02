/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.io;

import java.io.IOException;

/**
 * Represents client-side storage with quota restrictions. A clientlet engine
 * will typically provide an instance of this interface per host. A manged store
 * manages instances of {@link org.loboevolution.io.ManagedFile}. The path to a
 * managed file is a Unix-style path, using forward slashes, with '/'
 * representing the root directory of the managed store. So each managed store
 * is similar to a small file system accessible only by a particular domain.
 *
 * @see org.loboevolution.io.ManagedFile
 * @see org.loboevolution.clientlet.ClientletContext#getManagedStore()
 */
public interface ManagedStore {

	/**
	 * Gets a ManagedFile instance for the given managed path. Directories in
	 * the path are separated by "/".
	 *
	 * @param path
	 *            the path
	 * @return the managed file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	ManagedFile getManagedFile(String path) throws IOException;

	/**
	 * Gets a ManagedFile relative to a given parent. Directories in the
	 * relative path are separated by "/".
	 *
	 * @param parent
	 *            the parent
	 * @param relativePath
	 *            the relative path
	 * @return the managed file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	ManagedFile getManagedFile(ManagedFile parent, String relativePath) throws IOException;

	/**
	 * Gets the root managed directory.
	 *
	 * @return the root managed directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	ManagedFile getRootManagedDirectory() throws IOException;

	/**
	 * Gets the quota.
	 *
	 * @return the quota
	 */
	long getQuota();

	/**
	 * Gets the size.
	 *
	 * @return the size
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	long getSize() throws IOException;

	/**
	 * Saves a serializable object at the given managed file path.
	 *
	 * @param path
	 *            the path
	 * @param object
	 *            the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void saveObject(String path, java.io.Serializable object) throws IOException;

	/**
	 * Retrieves a serializable object. If the file identified by
	 * <code>path</code> does not exist, this method returns <code>null</code>.
	 *
	 * @param path
	 *            Managed path to the object file.
	 * @param classLoader
	 *            A class loader that can load the expected object type.
	 * @return An object unserialized from managed file data.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	Object retrieveObject(String path, ClassLoader classLoader) throws IOException, ClassNotFoundException;

}
