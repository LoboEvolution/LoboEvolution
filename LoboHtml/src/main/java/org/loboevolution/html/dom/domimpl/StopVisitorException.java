/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

class StopVisitorException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Object tag;

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 */
	public StopVisitorException() {
		super();
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param tag a {@link java.lang.Object} object.
	 */
	public StopVisitorException(Object tag) {
		this.tag = tag;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public StopVisitorException(String message) {
		super(message);
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public StopVisitorException(String message, Throwable cause) {
		super(message, cause);
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public StopVisitorException(Throwable cause) {
		super(cause);
		this.tag = null;
	}

	/**
	 * <p>Getter for the field tag.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public final Object getTag() {
		return this.tag;
	}
}
