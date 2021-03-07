package org.loboevolution.html.node;

import org.loboevolution.html.node.Node;

/**
 * A DOM element's attribute as an object. In most DOM methods, you will
 * probably directly retrieve the attribute as a string (e.g.,
 * Element.getAttribute(), but certain functions (e.g.,
 * Element.getAttributeNode()) or means of iterating give Attr types.
 */
public interface Attr extends Node {

	String getLocalName();

	String getName();

	String getNamespaceURI();

	Element getOwnerElement();

	String getPrefix();

	boolean isSpecified();

	String getValue();

	void setValue(String value);
	
	boolean isId();

}
