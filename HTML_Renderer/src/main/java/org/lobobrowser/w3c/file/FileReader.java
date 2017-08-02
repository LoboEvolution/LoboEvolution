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

package org.lobobrowser.w3c.file;

import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventTarget;

/**
 * The Interface FileReader.
 */
public interface FileReader extends EventTarget {

	
	/** The Constant EMPTY. */
	public static final short EMPTY = 0;

	/** The Constant LOADING. */
	public static final short LOADING = 1;

	/** The Constant DONE. */
	public static final short DONE = 2;
	
	/**
	 * Read as array buffer.
	 *
	 * @param blob
	 *            the blob
	 */
	public void readAsArrayBuffer(Blob blob);

	/**
	 * Read as binary string.
	 *
	 * @param blob
	 *            the blob
	 */
	public void readAsBinaryString(Blob blob);

	/**
	 * Read as text.
	 *
	 * @param blob
	 *            the blob
	 */
	public void readAsText(Blob blob);

	/**
	 * Read as text.
	 *
	 * @param blob
	 *            the blob
	 * @param encoding
	 *            the encoding
	 */
	public void readAsText(Blob blob, String encoding);

	/**
	 * Read as data url.
	 *
	 * @param blob
	 *            the blob
	 */
	public void readAsDataURL(Blob blob);

	/**
	 * Abort.
	 */
	public void abort();

	/**
	 * Gets the ready state.
	 *
	 * @return the ready state
	 */
	public short getReadyState();

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Object getResult();

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public FileError getError();

	/**
	 * Gets the onloadstart.
	 *
	 * @return the onloadstart
	 */
	public Function getOnloadstart();

	/**
	 * Sets the onloadstart.
	 *
	 * @param onloadstart
	 *            the new onloadstart
	 */
	public void setOnloadstart(Function onloadstart);

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress();

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress
	 *            the new onprogress
	 */
	public void setOnprogress(Function onprogress);

	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload();

	/**
	 * Sets the onload.
	 *
	 * @param onload
	 *            the new onload
	 */
	public void setOnload(Function onload);

	/**
	 * Gets the onabort.
	 *
	 * @return the onabort
	 */
	public Function getOnabort();

	/**
	 * Sets the onabort.
	 *
	 * @param onabort
	 *            the new onabort
	 */
	public void setOnabort(Function onabort);

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror();

	/**
	 * Sets the onerror.
	 *
	 * @param onerror
	 *            the new onerror
	 */
	public void setOnerror(Function onerror);

	/**
	 * Gets the onloadend.
	 *
	 * @return the onloadend
	 */
	public Function getOnloadend();

	/**
	 * Sets the onloadend.
	 *
	 * @param onloadend
	 *            the new onloadend
	 */
	public void setOnloadend(Function onloadend);
}
