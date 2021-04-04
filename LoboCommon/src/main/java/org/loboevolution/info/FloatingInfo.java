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
package org.loboevolution.info;


/**
 * <p>FloatingInfo class.</p>
 *
 *
 *
 */
public class FloatingInfo {
	
	private Object[] floats;
	
	private int shiftX, shiftY;

	/**
	 * <p>Getter for the field floats.</p>
	 *
	 * @return the floats
	 */
	public Object[] getFloats() {
		return floats;
	}

	/**
	 * <p>Getter for the field shiftX.</p>
	 *
	 * @return the shiftX
	 */
	public int getShiftX() {
		return shiftX;
	}

	/**
	 * <p>Getter for the field shiftY.</p>
	 *
	 * @return the shiftY
	 */
	public int getShiftY() {
		return shiftY;
	}

	/**
	 * <p>Setter for the field floats.</p>
	 *
	 * @param floats the floats to set
	 */
	public void setFloats(Object[] floats) {
		this.floats = floats;
	}

	/**
	 * <p>Setter for the field shiftX.</p>
	 *
	 * @param shiftX the shiftX to set
	 */
	public void setShiftX(int shiftX) {
		this.shiftX = shiftX;
	}

	/**
	 * <p>Setter for the field shiftY.</p>
	 *
	 * @param shiftY the shiftY to set
	 */
	public void setShiftY(int shiftY) {
		this.shiftY = shiftY;
	}
}
