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

import java.util.Objects;

class CombinedFloatingBounds implements FloatingBounds {
	private final FloatingBounds floatBounds1;
	private final FloatingBounds floatBounds2;

	/**
	 * <p>Constructor for CombinedFloatingBounds.</p>
	 *
	 * @param floatBounds1 a {@link org.loboevolution.html.renderer.FloatingBounds} object.
	 * @param floatBounds2 a {@link org.loboevolution.html.renderer.FloatingBounds} object.
	 */
	public CombinedFloatingBounds(final FloatingBounds floatBounds1, final FloatingBounds floatBounds2) {
		super();
		this.floatBounds1 = floatBounds1;
		this.floatBounds2 = floatBounds2;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		// Important for layout caching.
		if (!(obj instanceof CombinedFloatingBounds)) {
			return false;
		}
		final CombinedFloatingBounds other = (CombinedFloatingBounds) obj;
		return Objects.equals(other.floatBounds1, this.floatBounds1)
				&& Objects.equals(other.floatBounds2, this.floatBounds2);
	}

	/** {@inheritDoc} */
	@Override
	public int getClearY(int y) {
		return Math.max(this.floatBounds1.getClearY(y), this.floatBounds2.getClearY(y));
	}

	/** {@inheritDoc} */
	@Override
	public int getFirstClearY(int y) {
		return Math.max(this.floatBounds1.getFirstClearY(y), this.floatBounds2.getFirstClearY(y));
	}

	/** {@inheritDoc} */
	@Override
	public int getLeft(int y) {
		return Math.max(this.floatBounds1.getLeft(y), this.floatBounds2.getLeft(y));
	}

	/** {@inheritDoc} */
	@Override
	public int getLeftClearY(int y) {
		return Math.max(this.floatBounds1.getLeftClearY(y), this.floatBounds2.getLeftClearY(y));
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxY() {
		return Math.max(this.floatBounds1.getMaxY(), this.floatBounds2.getMaxY());
	}

	/** {@inheritDoc} */
	@Override
	public int getRight(int y) {
		return Math.max(this.floatBounds1.getRight(y), this.floatBounds2.getRight(y));
	}

	/** {@inheritDoc} */
	@Override
	public int getRightClearY(int y) {
		return Math.max(this.floatBounds1.getRightClearY(y), this.floatBounds2.getRightClearY(y));
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final FloatingBounds fbounds1 = this.floatBounds1;
		final FloatingBounds fbounds2 = this.floatBounds2;
		return (fbounds1 == null ? 0 : fbounds1.hashCode()) ^ (fbounds2 == null ? 0 : fbounds2.hashCode());
	}
}
