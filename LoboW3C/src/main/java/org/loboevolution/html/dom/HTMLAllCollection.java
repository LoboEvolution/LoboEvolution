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

package org.loboevolution.html.dom;



/**
 * The interface Html all collection.
 *
 *
 *
 */
public interface HTMLAllCollection  {

    /**
     * Returns the number of elements in the collection.
     *
     * @return a int.
     */
    int getLength();

    /**
     * Returns the item with index index from the collection (determined by tree order).
     *
     * @param nameOrIndex the name or index
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection item(String nameOrIndex);

    /**
     * Item unknown.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection item();

    /**
     * Returns the item with ID or name name from the collection.
     * <p>
     * If there are multiple matching items, then an HTMLCollection object containing all those elements is returned.
     *
     * @param name the name
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection namedItem(String name);

}
