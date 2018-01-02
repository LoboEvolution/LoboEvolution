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
package org.lobobrowser.util.io;

/**
 * The Class BufferExceededException.
 */
public class BufferExceededException extends Exception {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3105309904365634760L;

	/**
	 * Instantiates a new buffer exceeded exception.
	 */
	public BufferExceededException() {
		super();
	}

	/**
	 * Instantiates a new buffer exceeded exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public BufferExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new buffer exceeded exception.
	 *
	 * @param message
	 *            the message
	 */
	public BufferExceededException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new buffer exceeded exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public BufferExceededException(Throwable cause) {
		super(cause);
	}
}
