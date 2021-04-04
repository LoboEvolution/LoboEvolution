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

package org.loboevolution.html.node;

/**
 * An object providing methods which are not dependent on any particular
 * document. Such an object is returned by the Document.implementation property.
 *
 *
 *
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
}
