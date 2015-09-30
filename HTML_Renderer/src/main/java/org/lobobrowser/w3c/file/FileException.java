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

package org.lobobrowser.w3c.file;


/**
 * The Class FileException.
 */
public class FileException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file exception.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public FileException(short code, String message) {
		super(message);
		this.code = code;
	}

	/** The Constant NOT_FOUND_ERR. */
	public static final short NOT_FOUND_ERR = 1;
	
	/** The Constant SECURITY_ERR. */
	public static final short SECURITY_ERR = 2;
	
	/** The Constant ABORT_ERR. */
	public static final short ABORT_ERR = 3;
	
	/** The Constant NOT_READABLE_ERR. */
	public static final short NOT_READABLE_ERR = 4;
	
	/** The Constant ENCODING_ERR. */
	public static final short ENCODING_ERR = 5;
	
	/** The code. */
	public short code;
}
