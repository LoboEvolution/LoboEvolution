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
 * The Class FloatingViewportBounds.
 */
public class FloatingViewportBounds implements FloatingBounds {

	/** The prev bounds. */
	private final FloatingBounds prevBounds;

	/** The left float. */
	private final boolean leftFloat;

	/** The y. */
	private final int y;

	/** The offset from border. */
	private final int offsetFromBorder;

	/** The height. */
	private final int height;

	/**
	 * Instantiates a Float.valueOfing viewport bounds.
	 *
	 * @param prevBounds
	 *            the prev bounds
	 * @param leftFloat
	 *            the left float
	 * @param y
	 *            the y
	 * @param offsetFromBorder
	 *            Width of floating box, including padding insets.
	 * @param height
	 *            the height
	 */
	public FloatingViewportBounds(FloatingBounds prevBounds, boolean leftFloat, int y, int offsetFromBorder,
			int height) {
		this.prevBounds = prevBounds;
		this.leftFloat = leftFloat;
		this.y = y;
		this.offsetFromBorder = offsetFromBorder;
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getLeft(int)
	 */
	@Override
	public int getLeft(int y) {
		int left = 0;
		if (this.leftFloat && y >= this.y && y < this.y + height) {
			left = this.offsetFromBorder;
		}
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int newLeft = prev.getLeft(y);
			if (newLeft > left) {
				left = newLeft;
			}
		}
		return left;
	}

	/**
	 * The offset from the right edge, not counting padding.
	 *
	 * @param y
	 *            the y
	 * @return the right
	 */
	@Override
	public int getRight(int y) {
		int right = 0;
		if (!this.leftFloat && y >= this.y && y < this.y + this.height) {
			right = this.offsetFromBorder;
		}
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int newRight = prev.getRight(y);
			if (newRight > right) {
				right = newRight;
			}
		}
		return right;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getClearY(int)
	 */
	@Override
	public int getClearY(int y) {
		int cleary = Math.max(y, this.y + this.height);
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int pcy = prev.getClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getFirstClearY(int)
	 */
	@Override
	public int getFirstClearY(int y) {
		int clearY = y;
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int prevClearY = prev.getFirstClearY(y);
			if (prevClearY != y) {
				clearY = prevClearY;
			}
		}
		if (clearY == y && y >= this.y && y < this.y + this.height) {
			clearY = this.y + this.height;
		}
		return clearY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getLeftClearY(int)
	 */
	@Override
	public int getLeftClearY(int y) {
		int cleary;
		if (this.leftFloat) {
			cleary = Math.max(y, this.y + this.height);
		} else {
			cleary = y;
		}
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int pcy = prev.getLeftClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getRightClearY(int)
	 */
	@Override
	public int getRightClearY(int y) {
		int cleary;
		if (!this.leftFloat) {
			cleary = Math.max(y, this.y + this.height);
		} else {
			cleary = y;
		}
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int pcy = prev.getLeftClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getMaxY()
	 */
	@Override
	public int getMaxY() {
		int maxY = this.y + this.height;
		FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			int prevMaxY = prev.getMaxY();
			if (prevMaxY > maxY) {
				maxY = prevMaxY;
			}
		}
		return maxY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		// Important for layout caching.
		if (Objects.equals(other, this)) {
			return true;
		}
		if (!(other instanceof FloatingViewportBounds)) {
			return false;
		}
		FloatingViewportBounds olm = (FloatingViewportBounds) other;
		return olm.leftFloat == this.leftFloat && olm.y == this.y && olm.height == this.height
				&& olm.offsetFromBorder == this.offsetFromBorder
				&& org.loboevolution.util.Objects.equals(olm.prevBounds, this.prevBounds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.leftFloat ? 1 : 0) ^ this.y ^ this.height ^ this.offsetFromBorder;
	}
}
