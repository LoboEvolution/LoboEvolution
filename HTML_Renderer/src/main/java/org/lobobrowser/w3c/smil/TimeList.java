/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.smil;

/**
 * The <code>TimeList</code> interface provides the abstraction of an ordered
 * collection of times, without defining or constraining how this collection is
 * implemented.
 * <p>
 * The items in the <code>TimeList</code> are accessible via an integral index,
 * starting from 0.
 */
public interface TimeList {
	/**
	 * Returns the <code>index</code> th item in the collection. If
	 * <code>index</code> is greater than or equal to the number of times in the
	 * list, this returns <code>null</code> .
	 * 
	 * @param index
	 *            Index into the collection.
	 * @return The time at the <code>index</code> th position in the
	 *         <code>TimeList</code> , or <code>null</code> if that is not a
	 *         valid index.
	 */
	public Time item(int index);

	/**
	 * The number of times in the list. The range of valid child time indices is
	 * 0 to <code>length-1</code> inclusive.
	 */
	public int getLength();

}
