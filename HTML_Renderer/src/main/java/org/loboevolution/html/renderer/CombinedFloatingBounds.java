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
 * The Class CombinedFloatingBounds.
 */
public class CombinedFloatingBounds implements FloatingBounds {

	/** The float bounds1. */
	private final FloatingBounds floatBounds1;

	/** The float bounds2. */
	private final FloatingBounds floatBounds2;

	/**
	 * Instantiates a new combined floating bounds.
	 *
	 * @param floatBounds1
	 *            the float bounds1
	 * @param floatBounds2
	 *            the float bounds2
	 */
	public CombinedFloatingBounds(final FloatingBounds floatBounds1, final FloatingBounds floatBounds2) {
		super();
		this.floatBounds1 = floatBounds1;
		this.floatBounds2 = floatBounds2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getClearY(int)
	 */
	@Override
	public int getClearY(int y) {
		return Math.max(this.floatBounds1.getClearY(y), this.floatBounds2.getClearY(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getFirstClearY(int)
	 */
	@Override
	public int getFirstClearY(int y) {
		return Math.max(this.floatBounds1.getFirstClearY(y), this.floatBounds2.getFirstClearY(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getLeft(int)
	 */
	@Override
	public int getLeft(int y) {
		return Math.max(this.floatBounds1.getLeft(y), this.floatBounds2.getLeft(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getLeftClearY(int)
	 */
	@Override
	public int getLeftClearY(int y) {
		return Math.max(this.floatBounds1.getLeftClearY(y), this.floatBounds2.getLeftClearY(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getMaxY()
	 */
	@Override
	public int getMaxY() {
		return Math.max(this.floatBounds1.getMaxY(), this.floatBounds2.getMaxY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getRight(int)
	 */
	@Override
	public int getRight(int y) {
		return Math.max(this.floatBounds1.getRight(y), this.floatBounds2.getRight(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.FloatingBounds#getRightClearY(int)
	 */
	@Override
	public int getRightClearY(int y) {
		return Math.max(this.floatBounds1.getRightClearY(y), this.floatBounds2.getRightClearY(y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// Important for layout caching.
		if (!(obj instanceof CombinedFloatingBounds)) {
			return false;
		}
		CombinedFloatingBounds other = (CombinedFloatingBounds) obj;
		return Objects.equals(other.floatBounds1, this.floatBounds1)
				&& Objects.equals(other.floatBounds2, this.floatBounds2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		FloatingBounds fbounds1 = this.floatBounds1;
		FloatingBounds fbounds2 = this.floatBounds2;
		return (fbounds1 == null ? 0 : fbounds1.hashCode()) ^ (fbounds2 == null ? 0 : fbounds2.hashCode());
	}
}
