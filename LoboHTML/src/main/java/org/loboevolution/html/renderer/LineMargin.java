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
