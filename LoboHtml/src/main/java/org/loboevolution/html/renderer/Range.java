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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

/**
 * @author J. H. S.
 */
class Range {
	public final int length;
	public final int offset;

	/**
	 * <p>Constructor for Range.</p>
	 *
	 * @param offset a int.
	 * @param length a int.
	 */
	public Range(final int offset, final int length) {
		super();
		this.offset = offset;
		this.length = length;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Range[offset=" + this.offset + ",length=" + this.length + "]";
	}
}
