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
package org.loboevolution.html.dom.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

/**
 * <p>TagNameFilter class.</p>
 */
@Data
@AllArgsConstructor
public class TagNsNameFilter implements NodeFilter {
    
    private final String localName;
    private final String namespaceURI;
    /**
     * {@inheritDoc}
     */
    @Override
    public short acceptNode(Node node) {

        if (!(node instanceof Element)) {
            return NodeFilter.FILTER_REJECT;
        }

        String lc = localName.contains(":") ? localName.split(":")[1] : localName;
        boolean tag = node.getLocalName().equalsIgnoreCase(lc.toUpperCase().trim());

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