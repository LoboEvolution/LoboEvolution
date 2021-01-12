/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.http.UserAgentContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/**
 * <p>DOMImplementationImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DOMImplementationImpl implements DOMImplementation {
	private final UserAgentContext context;

	/**
	 * <p>Constructor for DOMImplementationImpl.</p>
	 *
	 * @param context a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public DOMImplementationImpl(UserAgentContext context) {
		this.context = context;
	}

	/** {@inheritDoc} */
	@Override
	public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
		return new HTMLDocumentImpl(this.context);
	}
	
	/**
	 * <p>createHTMLDocument.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Document} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Document createHTMLDocument(String title) throws DOMException {
		HTMLDocumentImpl doc = new HTMLDocumentImpl(this.context);
		final Element body = doc.createElement("BODY");
		doc.setTitle(title);
		doc.setBody((HTMLElementImpl)body);
		return doc;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
		return new DocumentTypeImpl(qualifiedName, publicId, systemId);
	}

	/** {@inheritDoc} */
	@Override
	public Object getFeature(String feature, String version) {
		if ("HTML".equals(feature) && "2.0".compareTo(version) <= 0) {
			return this;
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasFeature(String feature, String version) {
		return "HTML".equals(feature) && "2.0".compareTo(version) <= 0;
	}
}
