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
 * An object providing methods which are not dependent on any particular
 * document. Such an object is returned by the Document implementation property.
 */
public interface DOMImplementation {

	/**
	 * <p>createDocument.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @param doctype a {@link org.loboevolution.html.node.DocumentType} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype);

	/**
	 * <p>createDocumentType.</p>
	 *
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @param publicId a {@link java.lang.String} object.
	 * @param systemId a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.DocumentType} object.
	 */
	DocumentType createDocumentType(String qualifiedName, String publicId, String systemId);

	/**
	 * <p>createHTMLDocument.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document createHTMLDocument(String title);

	/**
	 * <p>createHTMLDocument.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document createHTMLDocument();

    boolean hasFeature(String xml, String s);

    Object getFeature(String core, String s);
}
