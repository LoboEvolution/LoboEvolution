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
import lombok.NoArgsConstructor;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import java.util.HashMap;

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
	public String getNodeValue() {
		return null;
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
