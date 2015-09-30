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
package org.lobobrowser.html.renderer;

/**
 * The Class SizeExceededException.
 */
class SizeExceededException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new size exceeded exception.
	 */
	public SizeExceededException() {
		super();
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public SizeExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param message
	 *            the message
	 */
	public SizeExceededException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public SizeExceededException(Throwable cause) {
		super(cause);
	}
}
