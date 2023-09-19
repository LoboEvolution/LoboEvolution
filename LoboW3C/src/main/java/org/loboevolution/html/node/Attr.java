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

package org.loboevolution.html.node;

/**
 * A DOM element's attribute as an object. In most DOM methods, you will
 * probably directly retrieve the attribute as a string (e.g.,
 * Element.getAttribute(), but certain functions (e.g.,
 * Element.getAttributeNode()) or means of iterating give Attr types.
 */
public interface Attr extends Node {

	/**
	 * <p>getLocalName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLocalName();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>getNamespaceURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getNamespaceURI();

	/**
	 * <p>getOwnerElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node getOwnerElement();

	/**
	 * <p>getPrefix.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPrefix();

	/**
	 * <p>getCurrentNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.TypeInfo} object.
	 */
	TypeInfo getSchemaTypeInfo();

	/**
	 * <p>isSpecified.</p>
	 *
	 * @return a boolean.
	 */
	boolean isSpecified();

	/**
	 * <p>getValue.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();

	/**
	 * <p>setValue.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

	/**
	 * <p>setOwnerElement.</p>
	 * @param element a {@link org.loboevolution.html.node.Node} object.
	 */
	void setOwnerElement(Node element);
	
	/**
	 * <p>isId.</p>
	 *
	 * @return a boolean.
	 */
	boolean isId();
}
