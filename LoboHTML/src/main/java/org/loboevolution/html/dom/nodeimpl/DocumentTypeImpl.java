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
/*
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import lombok.NoArgsConstructor;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * <p>DocumentTypeImpl class.</p>
 */
@NoArgsConstructor
public class DocumentTypeImpl extends EventTargetImpl implements DocumentType {
	private String publicId;
	private String qualifiedName;
	private String systemId;
	private NamedNodeMap notation;
	private NamedNodeMap entities;

	/**
	 * <p>Constructor for DocumentTypeImpl.</p>
	 *
	 * @param qname a {@link java.lang.String} object.
	 * @param publicId a {@link java.lang.String} object.
	 * @param systemId a {@link java.lang.String} object.
	 */
	public DocumentTypeImpl(String qname, String publicId, String systemId) {
		this.qualifiedName = qname;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return this.qualifiedName;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return getName();
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.DOCUMENT_TYPE_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getPublicId() {
		return this.publicId;
	}

	@Override
	public Node appendChild(Node newChild) {
		throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append node.");
	}

	/** {@inheritDoc} */
	@Override
	public String getSystemId() {
		return this.systemId;
	}

	@Override
	public NamedNodeMap getNotations() {
		if(this.notation == null) this.notation = new NamedNodeMapImpl(this, new NodeListImpl());
		return this.notation;
	}

	@Override
	public NamedNodeMap getEntities() {
		if(this.entities == null) this.entities = new NamedNodeMapImpl(this, new NodeListImpl());
		return this.entities;
	}

	@Override
	public String getInternalSubset() {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE");
		if (Strings.isNotBlank(getName())) {
			builder.append(" ");
			builder.append(getName());
		}

		if (Strings.isNotBlank(getPublicId())) {
			builder.append(" PUBLIC \"");
			builder.append(getPublicId());
			builder.append("\"");
			if (Strings.isNotBlank(getSystemId())) {
				builder.append(" \"");
				builder.append(getSystemId());
				builder.append("\"");
			}
		} else {
			if (Strings.isNotBlank(getSystemId())) {
				builder.append(" SYSTEM \"");
				builder.append(getSystemId());
				builder.append("\"");
			}
		}

		builder.append(">");

		return builder.toString();
	}
}
