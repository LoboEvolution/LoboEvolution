/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.common.Objects;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.http.UserAgentContext;

/**
 * <p>DOMImplementationImpl class.</p>
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
	public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException{
		HTMLDocumentImpl doc = new HTMLDocumentImpl(this.context);
		doc.setDoctype(doctype);
		doc.setNamespaceURI(namespaceURI);
		if (doctype != null && doctype.getOwnerDocument() == null) {
			doctype.setOwnerDocument(doc);
		}

		if(doctype != null && !Objects.equals(doctype.getOwnerDocument(), doc)) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (Strings.isNotBlank(qualifiedName)) {

			if (qualifiedName.contains(":")) {
				String[] split = qualifiedName.split(":");
				if (split.length != 2) {
					throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
				}
				if (Strings.isBlank(split[0]) || Strings.isBlank(split[1])) {
					throw new DOMException(DOMException.NAMESPACE_ERR, "The qualified name provided has an empty local name.");
				}

				if (!Strings.isXMLIdentifier(split[0]) || !Strings.isXMLIdentifier(split[1])) {
					throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
				}

				if (Strings.isBlank(namespaceURI)) {
					throw new DOMException(DOMException.NAMESPACE_ERR, "The namespace URI provided is not valid");
				}
			}


			ElementImpl elem;
			if (Strings.isNotBlank(namespaceURI)) {
				elem = (ElementImpl) doc.createElementNS(namespaceURI, qualifiedName);
			} else {
				elem = (ElementImpl) doc.createElement(qualifiedName);
			}
			doc.appendChild(elem);
		} else {
			if (qualifiedName != null && qualifiedName.length() == 0)
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		return doc;
	}
	
	/** {@inheritDoc} */
	@Override
	public Document createHTMLDocument() {
		HTMLDocumentImpl doc = new HTMLDocumentImpl(this.context);
		final Element body = doc.createElement("BODY");
		doc.setBody((HTMLElementImpl)body);
		return doc;
	}

	@Override
	public boolean hasFeature(String feature, String version) {
		return ("CORE".equalsIgnoreCase(feature) || "XML".equalsIgnoreCase(feature))
				|| ("1.0".equals(version) || "2.0".equals(version));
	}

	@Override
	public Object getFeature(String core, String s) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>createHTMLDocument.</p>
	 */
	public Document createHTMLDocument(String title) {
		DocumentImpl doc = (DocumentImpl) createHTMLDocument();
		doc.setTitle(title);
		return doc;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) {

		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		int prefixSeparator = qualifiedName.lastIndexOf(":");
		if (prefixSeparator != -1) {
			String prefix = qualifiedName.substring(0, prefixSeparator);
			String localName = qualifiedName.substring(prefixSeparator + 1);

			if (!Strings.isXMLIdentifier(prefix)) {
				throw new DOMException(DOMException.NAMESPACE_ERR, qualifiedName);
			}

			if (!Strings.isXMLIdentifier(localName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, qualifiedName);
			}
		} else {
			if (!Strings.isXMLIdentifier(qualifiedName)) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, qualifiedName);
			}
		}

		return new DocumentTypeImpl(qualifiedName, publicId, systemId);
	}
}
