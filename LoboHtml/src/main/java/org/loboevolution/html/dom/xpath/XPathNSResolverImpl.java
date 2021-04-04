/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
/*
 * $Id: XPathNSResolverImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.apache.xml.utils.PrefixResolverDefault;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.xpath.XPathNSResolver;

/**
 *
 * The class provides an implementation XPathNSResolver according to the DOM L3
 * XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
 * Object Model (DOM) Level 3 XPath Specification</a>.
 * </p>
 *
 * <p>
 * The XPathNSResolver interface permit prefix strings
 * in the expression to be properly bound to namespaceURI strings.
 * XPathEvaluator can construct an implementation of
 * XPathNSResolver from a node, or the interface may be implemented
 * by any application.
 * </p>
 *
 * @see org.loboevolution.html.xpath.XPathNSResolver
 *
 *
 */
public class XPathNSResolverImpl extends PrefixResolverDefault implements XPathNSResolver {

	

	/**
	 * <p>Constructor for XPathNSResolverImpl.</p>
	 *
	 * @param xpathExpressionContext a {@link org.w3c.dom.Node} object.
	 */
	public XPathNSResolverImpl(org.w3c.dom.Node xpathExpressionContext) {
		super(xpathExpressionContext);
		// TODO Broken with new interfaces
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return super.getNamespaceForPrefix(prefix);
	}

}
