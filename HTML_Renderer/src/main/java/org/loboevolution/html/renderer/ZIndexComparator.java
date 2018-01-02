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

import java.util.Comparator;

/** It is assumed that objects don't change their
  * z-indexes or ordinals after entering the sorted set.
  * They may do so after the sorted set is no longer valid.**/

/**
 * The Class ZIndexComparator.
 */
public class ZIndexComparator implements Comparator<Object> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object object1, Object object2) {
		PositionedRenderable element1 = (PositionedRenderable) object1;
		PositionedRenderable element2 = (PositionedRenderable) object2;
		int zIndex1 = element1.getRenderable().getZIndex();
		int zIndex2 = element2.getRenderable().getZIndex();
		int diff = zIndex1 - zIndex2;
		if (diff != 0) {
			return diff;
		}
		return element1.getOrdinal() - element2.getOrdinal();
	}
}
