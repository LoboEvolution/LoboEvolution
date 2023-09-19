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

import org.loboevolution.html.node.Node;

/**
 * The interface Html all collection.
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
     * @param index the name or index
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node item(Object index);

    /**
     * Returns the item with ID or name name from the collection.
     * <p>
     * If there are multiple matching items, then an HTMLCollection object containing all those elements is returned.
     *
     * @param name the name
     * @return a {@link java.lang.Object} object.
     */
    Object namedItem(String name);

    /**
     * Returns all tags by name.
     *
     * @param tag the name of tag
     * @return a {@link org.loboevolution.html.dom.HTMLAllCollection} object.
     */
    HTMLAllCollection tags(String tag);

}
