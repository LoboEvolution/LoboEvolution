/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom.smil;

/**
 * The TimeList interface provides the abstraction of an ordered
 * collection of times, without defining or constraining how this collection is
 * implemented.
 * <p>
 * The items in the TimeList are accessible via an integral index,
 * starting from 0.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface TimeList {
	/**
	 * Returns the index th item in the collection. If
	 * index is greater than or equal to the number of times in the
	 * list, this returns null .
	 *
	 * @param index
	 *            Index into the collection.
	 * @return The time at the index th position in the
	 *         TimeList , or null if that is not a
	 *         valid index.
	 */
    Time item(int index);

	/**
	 * The number of times in the list. The range of valid child time indices is
	 * 0 to length-1 inclusive.
	 *
	 * @return a int.
	 */
    int getLength();

}
