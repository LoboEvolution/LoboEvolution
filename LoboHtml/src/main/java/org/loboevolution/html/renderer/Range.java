/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
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
