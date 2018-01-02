/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import org.lobobrowser.util.Objects;

/**
 * The Class ShiftedFloatingBounds.
 */
public class ShiftedFloatingBounds implements FloatingBounds {

	/** The prev bounds. */
	private final FloatingBounds prevBounds;

	/** The shift left. */
	private final int shiftLeft;

	/** The shift right. */
	private final int shiftRight;

	/** The shift y. */
	private final int shiftY;

	/**
	 * Constructs the ShiftedFloatingBounds. Floatinb bounds moved up the
	 * hierarchy of renderables will generally have positive shifts.
	 *
	 * @param prevBounds
	 *            The baseline floating bounds.
	 * @param shiftLeft
	 *            the shift left
	 * @param shiftRight
	 *            the shift right
	 * @param shiftY
	 *            How much the original bounds have shifted in the Y axis.
	 */
	public ShiftedFloatingBounds(final FloatingBounds prevBounds, final int shiftLeft, final int shiftRight,
			final int shiftY) {
		super();
		this.prevBounds = prevBounds;
		this.shiftLeft = shiftLeft;
		this.shiftRight = shiftRight;
		this.shiftY = shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getClearY(int)
	 */
	@Override
	public int getClearY(int y) {
		return this.prevBounds.getClearY(y - this.shiftY) + this.shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getFirstClearY(int)
	 */
	@Override
	public int getFirstClearY(int y) {
		return this.prevBounds.getFirstClearY(y - this.shiftY) + this.shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getLeft(int)
	 */
	@Override
	public int getLeft(int y) {
		return this.prevBounds.getLeft(y - this.shiftY) + this.shiftLeft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getLeftClearY(int)
	 */
	@Override
	public int getLeftClearY(int y) {
		return this.prevBounds.getLeftClearY(y - this.shiftY) + this.shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getRight(int)
	 */
	@Override
	public int getRight(int y) {
		return this.prevBounds.getRight(y - this.shiftY) + this.shiftRight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getRightClearY(int)
	 */
	@Override
	public int getRightClearY(int y) {
		return this.prevBounds.getRightClearY(y - this.shiftY) + this.shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.FloatingBounds#getMaxY()
	 */
	@Override
	public int getMaxY() {
		return this.prevBounds.getMaxY() + this.shiftY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// Important for layout caching.
		if (!(obj instanceof ShiftedFloatingBounds)) {
			return false;
		}
		ShiftedFloatingBounds other = (ShiftedFloatingBounds) obj;
		return this.shiftY == other.shiftY && this.shiftLeft == other.shiftLeft && this.shiftRight == other.shiftRight
				&& Objects.equals(this.prevBounds, other.prevBounds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.shiftY ^ this.shiftLeft ^ this.shiftRight;
	}
}
