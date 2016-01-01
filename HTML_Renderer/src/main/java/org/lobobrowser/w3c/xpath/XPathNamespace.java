/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.xpath;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * The Interface XPathNamespace.
 */
public interface XPathNamespace extends Node {
	
	/** The Constant XPATH_NAMESPACE_NODE. */
	// XPathNamespace
	public static final short XPATH_NAMESPACE_NODE = 13;

	/**
	 * Gets the owner element.
	 *
	 * @return the owner element
	 */
	public Element getOwnerElement();
}
