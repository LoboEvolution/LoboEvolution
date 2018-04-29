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
package org.loboevolution.util;

/**
 * The Class BoxedObject.
 */
public class BoxedObject {
	/** The object. */
	private Object object;

	/**
	 * Instantiates a new boxed object.
	 */
	public BoxedObject() {
	}

	/**
	 * Instantiates a new boxed object.
	 *
	 * @param object
	 *            the object
	 */
	public BoxedObject(Object object) {
		super();
		this.object = object;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object
	 *            the new object
	 */
	public void setObject(Object object) {
		this.object = object;
	}
}
