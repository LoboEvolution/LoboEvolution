/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.info;

import java.io.Serializable;

import org.lobobrowser.html.renderer.ExportableFloat;

/**
 * The Class FloatingInfo.
 */
public class FloatingInfo implements Serializable {

	private static final long serialVersionUID = -6300332715805068001L;

	/** The shift x. */
	private int shiftX;

	/** The shift y. */
	private int shiftY;

	/** The floats. */
	private ExportableFloat[] floats;

	/**
	 * Instantiates a new floating info.
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
	 * @return the shiftX
	 */
	public int getShiftX() {
		return shiftX;
	}

	/**
	 * @param shiftX
	 *            the shiftX to set
	 */
	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	/**
	 * @return the shiftY
	 */
	public int getShiftY() {
		return shiftY;
	}

	/**
	 * @param shiftY
	 *            the shiftY to set
	 */
	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}

	/**
	 * @return the floats
	 */
	public ExportableFloat[] getFloats() {
		return floats;
	}

	/**
	 * @param floats
	 *            the floats to set
	 */
	public void setFloats(ExportableFloat[] floats) {
		this.floats = floats;
	}
}
