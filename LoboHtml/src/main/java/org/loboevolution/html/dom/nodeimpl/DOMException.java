/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.Code;


/**
 * <p>DOMException class.</p>
 *
 *
 *
 */
public class DOMException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Code code;
	
	private String name;
	
	/**
	 * <p>Constructor for DOMException.</p>
	 *
	 * @param code a {@link org.loboevolution.html.node.Code} object.
	 * @param name a {@link java.lang.String} object.
	 */
	public DOMException(Code code, String name) {
		this.code = code;
		this.name = name;
	}

	
	/**
	 * <p>Getter for the field <code>code</code>.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Code} object.
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return "[Object DOMException]";
	}
}
