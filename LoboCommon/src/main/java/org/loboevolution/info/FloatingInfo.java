/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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
package org.loboevolution.info;


public class FloatingInfo {
	
	private Object[] floats;
	
	private int shiftX, shiftY;

	/**
	 * @return the floats
	 */
	public Object[] getFloats() {
		return floats;
	}

	/**
	 * @return the shiftX
	 */
	public int getShiftX() {
		return shiftX;
	}

	/**
	 * @return the shiftY
	 */
	public int getShiftY() {
		return shiftY;
	}

	/**
	 * @param floats the floats to set
	 */
	public void setFloats(Object[] floats) {
		this.floats = floats;
	}

	/**
	 * @param shiftX the shiftX to set
	 */
	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	/**
	 * @param shiftY the shiftY to set
	 */
	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}
}
