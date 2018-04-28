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
package org.loboevolution.html.info;

import java.io.Serializable;

import org.loboevolution.html.renderer.ExportableFloat;

/**
 * The Class FloatingInfo.
 */
public class FloatingInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6300332715805068001L;

	/** The shift x. */
	private int shiftX;

	/** The shift y. */
	private int shiftY;

	/** The floats. */
	private transient ExportableFloat[] floats;

	/**
	 * Instantiates a Float.valueOfing info.
	 *
	 * @param shiftX
	 *            the shift x
	 * @param shiftY
	 *            the shift y
	 * @param floats
	 *            the floats
	 */
	public FloatingInfo(int shiftX, int shiftY, ExportableFloat[] floats) {
		super();
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.floats = floats;
	}

	/**
	 * Gets the shift x.
	 *
	 * @return the shift x
	 */
	public int getShiftX() {
		return shiftX;
	}

	/**
	 * Sets the shift x.
	 *
	 * @param shiftX
	 *            the new shift x
	 */
	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	/**
	 * Gets the shift y.
	 *
	 * @return the shift y
	 */
	public int getShiftY() {
		return shiftY;
	}

	/**
	 * Sets the shift y.
	 *
	 * @param shiftY
	 *            the new shift y
	 */
	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}

	/**
	 * Gets the floats.
	 *
	 * @return the floats
	 */
	public ExportableFloat[] getFloats() {
		return floats;
	}

	/**
	 * Sets the floats.
	 *
	 * @param floats
	 *            the Float.valueOfs
	 */
	public void setFloats(ExportableFloat[] floats) {
		this.floats = floats;
	}
}
