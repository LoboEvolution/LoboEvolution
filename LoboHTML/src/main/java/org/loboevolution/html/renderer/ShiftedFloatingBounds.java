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

import java.util.Objects;

class ShiftedFloatingBounds implements FloatingBounds {
	private final FloatingBounds prevBounds;
	private final int shiftLeft;
	private final int shiftRight;
	private final int shiftY;

	/**
	 * Constructs the ShiftedFloatingBounds. Floatinb bounds moved up the hierarchy
	 * of renderables will generally have positive shifts.
	 *
	 * @param prevBounds The baseline floating bounds.
	 * @param shiftY     How much the original bounds have shifted in the Y axis.
	 * @param shiftLeft a int.
	 * @param shiftRight a int.
	 */
	public ShiftedFloatingBounds(final FloatingBounds prevBounds, final int shiftLeft, final int shiftRight,
			final int shiftY) {
		super();
		this.prevBounds = prevBounds;
		this.shiftLeft = shiftLeft;
		this.shiftRight = shiftRight;
		this.shiftY = shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		// Important for layout caching.
		if (!(obj instanceof ShiftedFloatingBounds)) {
			return false;
		}
		final ShiftedFloatingBounds other = (ShiftedFloatingBounds) obj;
		return this.shiftY == other.shiftY && this.shiftLeft == other.shiftLeft && this.shiftRight == other.shiftRight
				&& Objects.equals(this.prevBounds, other.prevBounds);
	}

	/** {@inheritDoc} */
	@Override
	public int getClearY(int y) {
		return this.prevBounds.getClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getFirstClearY(int y) {
		return this.prevBounds.getFirstClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeft(int y) {
		return this.prevBounds.getLeft(y - this.shiftY) + this.shiftLeft;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeftClearY(int y) {
		return this.prevBounds.getLeftClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxY() {
		return this.prevBounds.getMaxY() + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getRight(int y) {
		return this.prevBounds.getRight(y - this.shiftY) + this.shiftRight;
	}

	/** {@inheritDoc} */
	@Override
	public int getRightClearY(int y) {
		return this.prevBounds.getRightClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return this.shiftY ^ this.shiftLeft ^ this.shiftRight;
	}
}
