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

package org.loboevolution.html.dom.smil;

import org.loboevolution.html.node.NodeList;

/**
 * This is a placeholder - subject to change. This represents generic timelines.
 */
public interface ElementTimeContainer extends ElementTime {
    /**
     * A NodeList that contains all timed childrens of this node. If there are
     * no timed children, the Nodelist is empty. An iterator is
     * more appropriate here than a node list but it requires Traversal module
     * support.
     *
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getTimeChildren();

    /**
     * Returns a list of child elements active at the specified invocation.
     *
     * @param instant
     *            The desired position on the local timeline in milliseconds.
     * @return List of timed child-elements active at instant.
     */
    NodeList getActiveChildrenAt(float instant);

}
