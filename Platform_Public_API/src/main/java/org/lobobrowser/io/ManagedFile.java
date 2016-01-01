/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a file location in a managed store.
 *
 * @author J. H. S.
 */
public interface ManagedFile {

    /** Gets the path.
	 *
	 * @return the path
	 */
    String getPath();

    /** Checks if is directory.
	 *
	 * @return true, if is directory
	 */
    boolean isDirectory();

    /**
     * Exists.
     *
     * @return true, if successful
     */
    boolean exists();

    /**
     * Mkdir.
     *
     * @return true, if successful
     */
    boolean mkdir();

    /**
     * Mkdirs.
     *
     * @return true, if successful
     */
    boolean mkdirs();

    /**
     * List files.
     *
     * @return the managed file[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    ManagedFile[] listFiles() throws IOException;

    /**
     * List files.
     *
     * @param filter
     *            the filter
     * @return the managed file[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    ManagedFile[] listFiles(ManagedFileFilter filter) throws IOException;

    /**
     * Atomically creates a new file.
     *
     * @return True if and only if the file did not already exist and was
     *         successfully created.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    boolean createNewFile() throws IOException;

    /** Gets the parent.
	 *
	 * @return the parent
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    ManagedFile getParent() throws IOException;

    /**
     * Creates an output stream for the managed file. If the managed file
     * already exists, it is overwritten.
     * <p>
     * The number of bytes that can be written to the stream may be restricted
     * by a quota.
     *
     * @return the output stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see QuotaExceededException
     */
    OutputStream openOutputStream() throws IOException;

    /**
     * Creates an input stream for reading from the managed file.
     *
     * @return the input stream
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    InputStream openInputStream() throws IOException;

    /**
     * Delete.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    boolean delete() throws IOException;
}
