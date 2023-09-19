/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
