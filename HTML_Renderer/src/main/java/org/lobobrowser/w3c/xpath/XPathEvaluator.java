/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


/**
 * The Interface XPathEvaluator.
 */
public interface XPathEvaluator {
	
	/**
	 * Creates the expression.
	 *
	 * @param expression the expression
	 * @param resolver the resolver
	 * @return the x path expression
	 * @throws XPathException the x path exception
	 * @throws DOMException the DOM exception
	 */
	// XPathEvaluator
	public XPathExpression createExpression(String expression, XPathNSResolver resolver)
			throws XPathException, DOMException;

	/**
	 * Creates the ns resolver.
	 *
	 * @param nodeResolver the node resolver
	 * @return the x path ns resolver
	 */
	public XPathNSResolver createNSResolver(Node nodeResolver);

	/**
	 * Evaluate.
	 *
	 * @param expression the expression
	 * @param contextNode the context node
	 * @param resolver the resolver
	 * @param type the type
	 * @param result the result
	 * @return the object
	 * @throws XPathException the x path exception
	 * @throws DOMException the DOM exception
	 */
	public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result)
			throws XPathException, DOMException;
}
