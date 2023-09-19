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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLSlotElement;

/**
 * The textual content of Element or Attr.
 * If an element has no markup within its content, it has a single child implementing Text that contains the element's text.
 * However, if the element contains markup, it is parsed into information items and Text nodes that form its children.
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
