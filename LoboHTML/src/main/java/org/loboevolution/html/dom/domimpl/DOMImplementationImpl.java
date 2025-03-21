/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.dom.nodeimpl.DocumentTypeImpl;
import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.http.UserAgentContext;
import java.util.Objects;

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
	public DOMImplementationImpl(final UserAgentContext context) {
		this.context = context;
	}

    /** {@inheritDoc} */
	@Override
	public Document createDocument(final String namespaceURI, final String qualifiedName, final DocumentType doctype) throws DOMException{
		final HTMLDocumentImpl doc = new HTMLDocumentImpl(this.context);
		doc.setDoctype(doctype);

		if (Strings.isNotBlank(namespaceURI)) {
			doc.setNamespaceURI(namespaceURI);
			doc.setXml(true);
		}

		if (doctype != null && doctype.getOwnerDocument() == null) {
			doctype.setOwnerDocument(doc);
		}

		if(doctype != null && !Objects.equals(doctype.getOwnerDocument(), doc)) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (Strings.isNotBlank(qualifiedName)) {

			if (qualifiedName.contains(":")) {
				final String[] split = qualifiedName.split(":");
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


			final ElementImpl elem;
			if (Strings.isNotBlank(namespaceURI)) {
				elem = (ElementImpl) doc.createElementNS(namespaceURI, qualifiedName);
			} else {
				elem = (ElementImpl) doc.createElement(qualifiedName);
			}
			doc.appendChild(elem);
		}

		return doc;
	}

	/** {@inheritDoc} */
	@Override
	public Document createHTMLDocument() {
		final HTMLDocumentImpl doc = new HTMLDocumentImpl(this.context);
		final Element body = doc.createElement("BODY");
		doc.setBody((HTMLElementImpl)body);
		return doc;
	}

	@Override
	public boolean hasFeature(final String ftr, final String version) {
		String feature = ftr;
		if(Strings.isNotBlank(feature)) feature = feature.toLowerCase();
		if(Strings.isNotBlank(feature) && feature.startsWith("+")) feature = feature.substring(1).toLowerCase();
		return "core".equals(feature) || "xml".equals(feature)
				|| "1.0".equals(version) || "2.0".equals(version) || "3.0".equals(version);
	}

	@Override
	public Object getFeature(final String core, final String s) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>createHTMLDocument.</p>
	 */
	public Document createHTMLDocument(final String title) {
		final DocumentImpl doc = (DocumentImpl) createHTMLDocument();
		doc.setTitle(title);
		return doc;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentType createDocumentType(final String qualifiedName, final String publicId, final String systemId) {

		if (Strings.isBlank(qualifiedName)) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The qualified name contains the invalid character");
		}

		final int prefixSeparator = qualifiedName.lastIndexOf(":");
		if (prefixSeparator != -1) {
			final String prefix = qualifiedName.substring(0, prefixSeparator);
			final String localName = qualifiedName.substring(prefixSeparator + 1);

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
