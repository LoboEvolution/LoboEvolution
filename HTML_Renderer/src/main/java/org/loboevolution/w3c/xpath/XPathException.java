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

package org.loboevolution.w3c.xpath;

/**
 * The Class XPathException.
 */
public class XPathException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant INVALID_EXPRESSION_ERR. */
	public static final short INVALID_EXPRESSION_ERR = 51;

	/** The Constant TYPE_ERR. */
	public static final short TYPE_ERR = 52;

	/** The code. */
	public short code;

	/**
	 * Instantiates a new x path exception.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public XPathException(short code, String message) {
		super(message);
		this.code = code;
	}
}
