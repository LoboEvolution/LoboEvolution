/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * The Interface XPathExpression.
 */
public interface XPathExpression {
	
	/**
	 * Evaluate.
	 *
	 * @param contextNode the context node
	 * @param type the type
	 * @param result the result
	 * @return the object
	 * @throws XPathException the x path exception
	 * @throws DOMException the DOM exception
	 */
	// XPathExpression
	public Object evaluate(Node contextNode, short type, Object result) throws XPathException, DOMException;
}
