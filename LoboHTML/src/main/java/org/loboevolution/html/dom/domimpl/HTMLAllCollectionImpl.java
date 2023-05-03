/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
