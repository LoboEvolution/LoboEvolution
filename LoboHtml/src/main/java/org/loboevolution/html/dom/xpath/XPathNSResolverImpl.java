/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * $Id: XPathNSResolverImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathNSResolver;

import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;

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
 * The <code>XPathNSResolver</code> interface permit <code>prefix</code> strings
 * in the expression to be properly bound to <code>namespaceURI</code> strings.
 * <code>XPathEvaluator</code> can construct an implementation of
 * <code>XPathNSResolver</code> from a node, or the interface may be implemented
 * by any application.
 * </p>
 *
 * @see org.w3c.dom.xpath.XPathNSResolver
 * @xsl.usage internal
 */
public class XPathNSResolverImpl extends PrefixResolverDefault implements XPathNSResolver {

	/**
	 * Constructor for XPathNSResolverImpl.
	 * 
	 * @param xpathExpressionContext
	 */
	public XPathNSResolverImpl(Node xpathExpressionContext) {
		super(xpathExpressionContext);
	}

	/**
	 * @see org.w3c.dom.xpath.XPathNSResolver#lookupNamespaceURI(String)
	 */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return super.getNamespaceForPrefix(prefix);
	}

}
