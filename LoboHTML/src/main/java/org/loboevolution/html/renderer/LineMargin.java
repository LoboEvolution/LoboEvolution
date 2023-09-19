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

class LineMargin {
	private final int clearX;
	private final int clearY;
	private final LineMargin next;

	/**
	 * <p>Constructor for LineMargin.</p>
	 *
	 * @param next a {@link org.loboevolution.html.renderer.LineMargin} object.
	 * @param cleary a int.
	 * @param totalXOffset a int.
	 */
	public LineMargin(LineMargin next, int cleary, int totalXOffset) {
		super();
		this.next = next;
		this.clearY = cleary;
		this.clearX = totalXOffset;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof LineMargin)) {
			return false;
		}
		final LineMargin olm = (LineMargin) other;
		return olm.clearX == this.clearX && olm.clearY == this.clearY
				&& java.util.Objects.equals(olm.next, this.next);
	}

	/**
	 * <p>Getter for the field clearY.</p>
	 *
	 * @return a int.
	 */
	public int getClearY() {
		return this.clearY;
	}

	/**
	 * <p>Getter for the field next.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.LineMargin} object.
	 */
	public LineMargin getNext() {
		return this.next;
	}

	/**
	 * <p>getOffset.</p>
	 *
	 * @return a int.
	 */
	public int getOffset() {
		return this.clearX;
	}

	/**
	 * <p>translated.</p>
	 *
	 * @param yoffset a int.
	 * @param xoffset a int.
	 * @return a {@link org.loboevolution.html.renderer.LineMargin} object.
	 */
	public LineMargin translated(int yoffset, int xoffset) {
		final int newClearY = this.clearY - yoffset;
		int newOffsetX = this.clearX - xoffset;
		if (newOffsetX < 0) {
			newOffsetX = 0;
		}
		final LineMargin oldNext = this.next;
		final LineMargin newNext = oldNext == null ? null : oldNext.translated(yoffset, xoffset);
		return new LineMargin(newNext, newClearY, newOffsetX);
	}
}
