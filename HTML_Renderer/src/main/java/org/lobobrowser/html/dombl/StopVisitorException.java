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
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.dombl;

/**
 * The Class StopVisitorException.
 */
public class StopVisitorException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The tag. */
	private final Object tag;

	/**
	 * Instantiates a new stop visitor exception.
	 */
	public StopVisitorException() {
		super();
		this.tag = null;
	}

	/**
	 * Instantiates a new stop visitor exception.
	 *
	 * @param message
	 *            the message
	 */
	public StopVisitorException(String message) {
		super(message);
		this.tag = null;
	}

	/**
	 * Instantiates a new stop visitor exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public StopVisitorException(String message, Throwable cause) {
		super(message, cause);
		this.tag = null;
	}

	/**
	 * Instantiates a new stop visitor exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public StopVisitorException(Throwable cause) {
		super(cause);
		this.tag = null;
	}

	/**
	 * Instantiates a new stop visitor exception.
	 *
	 * @param tag
	 *            the tag
	 */
	public StopVisitorException(Object tag) {
		this.tag = tag;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public final Object getTag() {
		return this.tag;
	}
}
