/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>TagNameFilter class.</p>
 */
@Data
@AllArgsConstructor
public class TagNameFilter implements NodeFilter {
    private final String name;

    private final boolean xml;

    /**
     * {@inheritDoc}
     */
    @Override
    public short acceptNode(Node node) {
        if (!(node instanceof Element)) {
            return NodeFilter.FILTER_REJECT;
        }
		String[] prefix = node.getNodeName().split(":");
        return (!isXml() && name.equalsIgnoreCase(node.getNodeName()) && node.getPrefix() == null) ||
                (isXml() && (name.equalsIgnoreCase(node.getNodeName()) || prefix.length > 1 && name.equalsIgnoreCase(prefix[1].trim()))) ?
				NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT;
    }
}
