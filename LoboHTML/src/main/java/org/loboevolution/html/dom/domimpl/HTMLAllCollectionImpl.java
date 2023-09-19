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

package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLAllCollection;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The HTMLAllCollectionImpl class.
 */
public class HTMLAllCollectionImpl extends AbstractList<Node> implements HTMLAllCollection {

    private final NodeImpl rootNode;

    /** {@inheritDoc} */
    public HTMLAllCollectionImpl(NodeImpl rootNode, List<Node> nodeList) {
        super(nodeList);
        this.rootNode = rootNode;
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return this.size();
    }

    /** {@inheritDoc} */
    @Override
    public Node item(Object index) {
        try {
            double idx = Double.parseDouble(index.toString());
            if (idx >= getLength() || idx == -1) return null;
            return this.get((int) idx);
        } catch (NumberFormatException e) {
            return this.get(0);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object namedItem(String name) {
        final List<Node> list = new ArrayList<>();
        for (Node node : this) {
            Element elm = (Element) node;
            if (name.equals(elm.getId()) || name.equals(elm.getAttribute("name"))) {
                list.add(elm);
            }
        }

        if (list.size() > 1) {
            return new HTMLAllCollectionImpl(null, list);
        } else if (list.size() == 1) {
            Optional<Node> node = list.stream().findFirst();
            return node.orElse(null);
        } else {
            return null;
        }
    }

    /**
     * Returns all tags by name.
     *
     * @param tag the name of tag
     * @return a {@link HTMLAllCollection} object.
     */
    @Override
    public HTMLAllCollection tags(String tag) {
        final Document doc = this.rootNode.getOwnerDocument();
        if (doc == null) {
            return null;
        }

        final HTMLAllCollection list =  (HTMLAllCollection) doc.getElementsByTagName(tag);
        if(list.getLength() == 0){
            throw new DOMException(DOMException.NOT_FOUND_ERR, "is not a valid tag name.");
        }

        return list;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[object HTMLAllCollection]";
    }
}
