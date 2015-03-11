/*
Copyright 1994-2006 The Lobo Project. Copyright 2014 Lobo Evolution. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list 
of conditions and the following disclaimer. Redistributions in binary form must 
reproduce the above copyright notice, this list of conditions and the following 
disclaimer in the documentation and/or other materials provided with the distribution.
 
THIS SOFTWARE IS PROVIDED BY THE LOBO PROJECT ``AS IS'' AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO 
EVENT SHALL THE FREEBSD PROJECT OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.
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
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath();

	/**
	 * Checks if is directory.
	 *
	 * @return true, if is directory
	 */
	public boolean isDirectory();

	/**
	 * Exists.
	 *
	 * @return true, if successful
	 */
	public boolean exists();

	/**
	 * Mkdir.
	 *
	 * @return true, if successful
	 */
	public boolean mkdir();

	/**
	 * Mkdirs.
	 *
	 * @return true, if successful
	 */
	public boolean mkdirs();

	/**
	 * List files.
	 *
	 * @return the managed file[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ManagedFile[] listFiles() throws IOException;

	/**
	 * List files.
	 *
	 * @param filter the filter
	 * @return the managed file[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ManagedFile[] listFiles(ManagedFileFilter filter) throws IOException;

	/**
	 * Atomically creates a new file.
	 *
	 * @return True if and only if the file did not already exist and was
	 *         successfully created.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean createNewFile() throws IOException;

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ManagedFile getParent() throws IOException;

	/**
	 * Creates an output stream for the managed file. If the managed file
	 * already exists, it is overwritten.
	 * <p>
	 * The number of bytes that can be written to the stream may be restricted
	 * by a quota.
	 *
	 * @return the output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see QuotaExceededException
	 */
	public OutputStream openOutputStream() throws IOException;

	/**
	 * Creates an input stream for reading from the managed file.
	 *
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public InputStream openInputStream() throws IOException;

	/**
	 * Delete.
	 *
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean delete() throws IOException;
}
