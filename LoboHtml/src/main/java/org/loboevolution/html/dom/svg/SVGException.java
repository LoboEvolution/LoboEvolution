/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.html.dom.svg;

/**
 * <p>Abstract SVGException class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class SVGException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	final short code;

	// ExceptionCode
	static final short SVG_WRONG_TYPE_ERR = 0;

	static final short SVG_INVALID_VALUE_ERR = 1;

	static final short SVG_MATRIX_NOT_INVERTABLE = 2;

	SVGException(short code, String message) {
		super(message);
		this.code = code;
	}
}
