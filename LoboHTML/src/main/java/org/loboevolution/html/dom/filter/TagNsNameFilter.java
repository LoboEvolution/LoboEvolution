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
package org.loboevolution.html.dom.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

/**
 * <p>TagNameFilter class.</p>
 */
@Data
@AllArgsConstructor
public class TagNsNameFilter implements NodeFilter {
    private final String name;
    private final String namespaceURI;

    /**
     * {@inheritDoc}
     */
    @Override
    public short acceptNode(Node node) {
        if (!(node instanceof Element)) {
            return NodeFilter.FILTER_REJECT;
        }

        String localName = name.contains(":") ? name.split(":")[1] : name;
        boolean tag = node.getLocalName().equalsIgnoreCase(localName.toUpperCase().trim());

        if (tag && namespaceURI == null && node.getNamespaceURI() == null) {
            return NodeFilter.FILTER_ACCEPT;
        }

        if (tag && namespaceURI != null && namespaceURI.equals(node.getNamespaceURI())) {
            return NodeFilter.FILTER_ACCEPT;
        }

        if (tag && "*".equals(namespaceURI)) {
            return NodeFilter.FILTER_ACCEPT;
        }

        return NodeFilter.FILTER_REJECT;
    }
}
