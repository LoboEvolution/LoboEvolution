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

import org.mozilla.javascript.ES6Iterator;
import org.mozilla.javascript.Function;

/**
 * NodeList objects are collections of nodes, usually returned by properties
 * such as Node.childNodes and methods such as document.querySelectorAll().
 */
public interface NodeList {

    /**
     * Returns the number of nodes in the collection.
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getLength();

    /**
     * Returns the node with index index from the collection. The nodes are sorted
     * in tree order.
	 * @param index a {@link java.lang.Integer} object.
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node item(int index);

    /**
     * Returns an iterator, allowing code to go through all key/value pairs contained in the collection.
     * @return a {@link org.mozilla.javascript.ES6Iterator} object.
     */
    ES6Iterator entries();

    /**
     * Returns an iterator, allowing code to go through all the keys of the key/value pairs contained in the collection.
     * @return a {@link org.mozilla.javascript.ES6Iterator} object.
     */
    ES6Iterator keys();

    /**
     * Returns an iterator allowing code to go through all values (nodes) of the key/value pairs contained in the collection.
     * @return a {@link org.mozilla.javascript.ES6Iterator} object.
     */
    ES6Iterator values();

    /**
     * Executes a provided function once per NodeList element, passing the element as an argument to the function.
     * @param function a {@link org.mozilla.javascript.Function} object.
     */
    void forEach(final Function function);

    /**
     * <p>toArray.</p>
     * @return an array of {@link org.loboevolution.html.node.Node} objects.
     */
    Node[] toArray();
}
