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

import org.loboevolution.html.dom.HTMLSlotElement;

/**
 * The textual content of Element or Attr. If an element has no markup within its content, it has a single child implementing Text that contains the element's text. However, if the element contains markup, it is parsed into information items and Text nodes that form its children.
 *
 *
 *
 */
public interface Text extends CharacterData {
   
    /**
     * <p>getAssignedSlot.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLSlotElement} object.
     */
    HTMLSlotElement getAssignedSlot();

    /**
     * Returns the combined data of all direct Text node siblings.
     *
     * @return a {@link java.lang.String} object.
     */
    String getWholeText();

    /**
     * Splits data at the given offset and returns the remainder as Text node.
     *
     * @param offset a int.
     * @return a {@link org.loboevolution.html.node.Text} object.
     */
    Text splitText(int offset);
    
    /**
     * <p>isElementContentWhitespace.</p>
     *
     * @return a boolean.
     */
    boolean isElementContentWhitespace();
    
    /**
     * <p>replaceWholeText.</p>
     *
     * @param content a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Text} object.
     */
    Text replaceWholeText(String content);

}
