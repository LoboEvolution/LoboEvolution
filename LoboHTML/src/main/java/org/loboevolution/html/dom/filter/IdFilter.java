/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>IdFilter class.</p>
 */
public class IdFilter implements NodeFilter {
	private final String _id;

	/**
	 * <p>Constructor for IdFilter.</p>
	 *
	 * @param _id a {@link java.lang.String} object.
	 */
	public IdFilter(final String _id) {
		this._id = _id;
	}

	/** {@inheritDoc} */
	@Override
	public short acceptNode(final Node node) {
		if ((node instanceof Element) && this._id != null) {
			final NamedNodeMap attributes = node.getAttributes();
			if (attributes != null) {
				for (final Node attribute : Nodes.iterable(attributes)) {
					final Attr attr = (Attr) attribute;
					if (Strings.isNotBlank(attr.getNodeValue())) {
						if (this._id.equals(attr.getNodeValue()) && attr.isId()) {
							return NodeFilter.FILTER_ACCEPT;
						}
					}
				}
			}
		}

		return NodeFilter.FILTER_REJECT;
	}
}