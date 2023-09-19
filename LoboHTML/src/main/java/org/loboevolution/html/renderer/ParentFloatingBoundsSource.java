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

/**
 * <p>ParentFloatingBoundsSource class.</p>
 *
 *
 *
 */
public class ParentFloatingBoundsSource implements FloatingBoundsSource {
	private final int blockShiftRight;
	private final int expectedBlockWidth;
	private final FloatingBounds floatBounds;
	private final int newX;
	private final int newY;

	/**
	 * <p>Constructor for ParentFloatingBoundsSource.</p>
	 *
	 * @param blockShiftRight a int.
	 * @param expectedWidth a int.
	 * @param newX a int.
	 * @param newY a int.
	 * @param floatBounds a {@link org.loboevolution.html.renderer.FloatingBounds} object.
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

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		// Important for layout caching.
		if (!(obj instanceof ParentFloatingBoundsSource)) {
			return false;
		}
		final ParentFloatingBoundsSource other = (ParentFloatingBoundsSource) obj;
		return this.blockShiftRight == other.blockShiftRight && this.expectedBlockWidth == other.expectedBlockWidth
				&& this.newX == other.newX && this.newY == other.newY
				&& Objects.equals(this.floatBounds, other.floatBounds);

	}

	/** {@inheritDoc} */
	@Override
	public FloatingBounds getChildBlockFloatingBounds(int apparentBlockWidth) {
		final int actualRightShift = this.blockShiftRight + this.expectedBlockWidth - apparentBlockWidth;
		return new ShiftedFloatingBounds(this.floatBounds, -this.newX, -actualRightShift, -this.newY);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return this.newX ^ this.newY ^ this.blockShiftRight ^ this.expectedBlockWidth;
	}
}
