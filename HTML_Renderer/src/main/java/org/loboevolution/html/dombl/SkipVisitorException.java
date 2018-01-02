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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dombl;

/**
 * The Class SkipVisitorException.
 */
public class SkipVisitorException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new skip visitor exception.
	 */
	public SkipVisitorException() {
		super();
	}

	/**
	 * Instantiates a new skip visitor exception.
	 *
	 * @param message
	 *            the message
	 */
	public SkipVisitorException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new skip visitor exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public SkipVisitorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new skip visitor exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public SkipVisitorException(Throwable cause) {
		super(cause);
	}

}
