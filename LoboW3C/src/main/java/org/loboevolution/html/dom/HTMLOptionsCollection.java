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

import org.loboevolution.html.node.Node;

/**
 * HTMLOptionsCollection is an interface representing a collection of HTML option elements (in document order) and offers methods and properties for traversing the list as well as optionally altering its items. This type is returned solely by the "options" property of select.
 *
 *
 *
 */
public interface HTMLOptionsCollection extends HTMLCollection {

    /**
     * Returns the number of elements in the collection.
     * <p>
     * When set to a smaller number, truncates the number of option elements in the corresponding container.
     * <p>
     * When set to a greater number, adds new blank option elements to that container.
     *
     * @return a int.
     */
    int getLength();

    
    /**
     * <p>setLength.</p>
     *
     * @param length a int.
     */
    void setLength(int length);

    /**
     * Returns the index of the first selected item, if any, or −1 if there is no selected item.
     * <p>
     * Can be set, to change the selection.
     *
     * @return a int.
     */
    int getSelectedIndex();

    
    /**
     * <p>setSelectedIndex.</p>
     *
     * @param selectedIndex a int.
     */
    void setSelectedIndex(int selectedIndex);

    /**
     * Inserts element before the node given by before.
     * <p>
     * The before argument can be a number, in which case element is inserted before the item with that number, or an element from the collection, in which case element is inserted before that element.
     * <p>
     * If before is omitted, null, or a number out of range, then element will be added at the end of the list.
     * <p>
     * This method will throw a "HierarchyRequestError" DOMException if element is an ancestor of the element into which it is to be inserted.
     *
     * @param element a {@link java.lang.Object} object.
     * @param before a {@link java.lang.Object} object.
     */
    void add(Object element, Object before);

    /**
     * <p>add.</p>
     *
     * @param element a {@link org.loboevolution.html.dom.HTMLOptionElement} object.
     */
    void add(HTMLOptionElement element);

    /**
     * Removes the item with index index from the collection.
     *
     * @param index a int.
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node remove(int index);

    /**
     * Remove.
     *
     * @param element the element
     * @return a boolean.
     */
    boolean remove(Object element);

}
