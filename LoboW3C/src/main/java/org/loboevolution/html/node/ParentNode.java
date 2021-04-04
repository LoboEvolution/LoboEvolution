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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Node;

/**
 * <p>ParentNode interface.</p>
 *
 *
 *
 */
public interface ParentNode extends Node {

    /**
     * <p>getChildElementCount.</p>
     *
     * @return a int.
     */
    int getChildElementCount();

    /**
     * Returns the child elements.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getChildren();

    /**
     * Returns the first child that is an element, and null otherwise.
     *
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getFirstElementChild();

    /**
     * Returns the last child that is an element, and null otherwise.
     *
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getLastElementChild();

    /**
     * Returns the first element that is a descendant of node that matches selectors.
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element querySelector(String selectors);

    /**
     * Returns all element descendants of node that match selectors.
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList querySelectorAll(String selectors);
}

