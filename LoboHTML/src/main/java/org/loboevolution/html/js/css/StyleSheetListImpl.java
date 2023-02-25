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

package org.loboevolution.html.js.css;

import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.html.node.css.StyleSheetList;

public class StyleSheetListImpl extends AbstractList<CSSStyleSheet> implements StyleSheetList {

    /** {@inheritDoc} */
    @Override
    public CSSStyleSheet item(int index) {
        return this.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return this.size();
    }

    @Override
    public String toString() {
        return "[object StyleSheetList]";
    }
}
