/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.js.geom;

import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.js.geom.DOMRect;
import org.loboevolution.html.node.js.geom.DOMRectList;

public class DOMRectListImpl extends AbstractList<DOMRect>  implements DOMRectList {
    /**
     * <p>getLength.</p>
     *
     * @return a {@link Integer} object.
     */
    @Override
    public int getLength() {
        return this.size();
    }

    /**
     * <p>getLength.</p>
     *
     * @param index a {@link Integer} object.
     * @return a {@link DOMRect} object.
     */
    @Override
    public DOMRect item(int index) {
        return this.get(index);
    }

    @Override
    public String toString() {
        return "[object DOMRectList]";
    }
}
