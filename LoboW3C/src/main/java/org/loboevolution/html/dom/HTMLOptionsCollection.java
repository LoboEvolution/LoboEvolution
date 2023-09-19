/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;

/**
 * HTMLOptionsCollection is an interface representing a collection of HTML option elements (in document order)
 * and offers methods and properties for traversing the list as well as optionally altering its items. This type is returned solely by the "options" property of select.
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
    void add(Object element, Object before) throws DOMException;

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
