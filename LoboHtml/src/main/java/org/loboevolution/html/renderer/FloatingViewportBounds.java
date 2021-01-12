/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

class FloatingViewportBounds implements FloatingBounds {
	private final int height;
	private final boolean leftFloat;
	private final int offsetFromBorder;
	private final FloatingBounds prevBounds;
	private final int y;

	/**
	 * <p>Constructor for FloatingViewportBounds.</p>
	 *
	 * @param prevBounds a {@link org.loboevolution.html.renderer.FloatingBounds} object.
	 * @param leftFloat a boolean.
	 * @param y a int.
	 * @param offsetFromBorder Width of floating box, including padding insets.
	 * @param height a int.
	 */
	public FloatingViewportBounds(FloatingBounds prevBounds, boolean leftFloat, int y, int offsetFromBorder,
			int height) {
		this.prevBounds = prevBounds;
		this.leftFloat = leftFloat;
		this.y = y;
		this.offsetFromBorder = offsetFromBorder;
		this.height = height;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object other) {
		// Important for layout caching.
		if (other == this) {
			return true;
		}
		if (!(other instanceof FloatingViewportBounds)) {
			return false;
		}
		final FloatingViewportBounds olm = (FloatingViewportBounds) other;
		return olm.leftFloat == this.leftFloat && olm.y == this.y && olm.height == this.height
				&& olm.offsetFromBorder == this.offsetFromBorder
				&& java.util.Objects.equals(olm.prevBounds, this.prevBounds);
	}

	/** {@inheritDoc} */
	@Override
	public int getClearY(int y) {
		int cleary = Math.max(y, this.y + this.height);
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int pcy = prev.getClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/** {@inheritDoc} */
	@Override
	public int getFirstClearY(int y) {
		int clearY = y;
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int prevClearY = prev.getFirstClearY(y);
			if (prevClearY != y) {
				clearY = prevClearY;
			}
		}
		if (clearY == y && y >= this.y && y < this.y + this.height) {
			clearY = this.y + this.height;
		}
		return clearY;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeft(int y) {
		int left = 0;
		if (this.leftFloat && y >= this.y && y < this.y + this.height) {
			left = this.offsetFromBorder;
		}
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int newLeft = prev.getLeft(y);
			if (newLeft > left) {
				left = newLeft;
			}
		}
		return left;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeftClearY(int y) {
		int cleary;
		if (this.leftFloat) {
			cleary = Math.max(y, this.y + this.height);
		} else {
			cleary = y;
		}
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int pcy = prev.getLeftClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxY() {
		int maxY = this.y + this.height;
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int prevMaxY = prev.getMaxY();
			if (prevMaxY > maxY) {
				maxY = prevMaxY;
			}
		}
		return maxY;
	}

	/**
	 * {@inheritDoc}
	 *
	 * The offset from the right edge, not counting padding.
	 */
	@Override
	public int getRight(int y) {
		int right = 0;
		if (!this.leftFloat && y >= this.y && y < this.y + this.height) {
			right = this.offsetFromBorder;
		}
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int newRight = prev.getRight(y);
			if (newRight > right) {
				right = newRight;
			}
		}
		return right;
	}

	/** {@inheritDoc} */
	@Override
	public int getRightClearY(int y) {
		int cleary;
		if (!this.leftFloat) {
			cleary = Math.max(y, this.y + this.height);
		} else {
			cleary = y;
		}
		final FloatingBounds prev = this.prevBounds;
		if (prev != null) {
			final int pcy = prev.getLeftClearY(y);
			if (pcy > cleary) {
				cleary = pcy;
			}
		}
		return cleary;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return (this.leftFloat ? 1 : 0) ^ this.y ^ this.height ^ this.offsetFromBorder;
	}
}
