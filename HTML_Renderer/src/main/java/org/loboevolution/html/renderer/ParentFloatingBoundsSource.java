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
package org.loboevolution.html.renderer;

import org.loboevolution.util.Objects;

/**
 * The Class ParentFloatingBoundsSource.
 */
public class ParentFloatingBoundsSource implements FloatingBoundsSource {

	/** The block shift right. */
	private final int blockShiftRight;

	/** The expected block width. */
	private final int expectedBlockWidth;

	/** The new x. */
	private final int newX;

	/** The new y. */
	private final int newY;

	/** The float bounds. */
	private final FloatingBounds floatBounds;

	/**
	 * Instantiates a new parent floating bounds source.
	 *
	 * @param blockShiftRight
	 *            the block shift right
	 * @param expectedWidth
	 *            the expected width
	 * @param newX
	 *            the new x
	 * @param newY
	 *            the new y
	 * @param floatBounds
	 *            the float bounds
	 */
	public ParentFloatingBoundsSource(int blockShiftRight, int expectedWidth, int newX, int newY,
			FloatingBounds floatBounds) {
		super();
		this.blockShiftRight = blockShiftRight;
		this.expectedBlockWidth = expectedWidth;
		this.newX = newX;
		this.newY = newY;
		this.floatBounds = floatBounds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBoundsSource#
	 * getChildBlockFloatingBounds (int)
	 */
	@Override
	public FloatingBounds getChildBlockFloatingBounds(int apparentBlockWidth) {
		int actualRightShift = this.blockShiftRight + this.expectedBlockWidth - apparentBlockWidth;
		return new ShiftedFloatingBounds(this.floatBounds, -this.newX, -actualRightShift, -this.newY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// Important for layout caching.
		if (!(obj instanceof ParentFloatingBoundsSource)) {
			return false;
		}
		ParentFloatingBoundsSource other = (ParentFloatingBoundsSource) obj;
		return this.blockShiftRight == other.blockShiftRight && this.expectedBlockWidth == other.expectedBlockWidth
				&& this.newX == other.newX && this.newY == other.newY
				&& Objects.equals(this.floatBounds, other.floatBounds);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.newX ^ this.newY ^ this.blockShiftRight ^ this.expectedBlockWidth;
	}
}
