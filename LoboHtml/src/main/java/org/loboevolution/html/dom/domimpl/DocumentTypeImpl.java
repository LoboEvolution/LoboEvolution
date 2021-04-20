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
/*
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

/**
 * <p>DocumentTypeImpl class.</p>
 *
 *
 *
 */
public class DocumentTypeImpl extends EventTargetImpl implements DocumentType {
	private final String publicId;
	private final String qualifiedName;
	private final String systemId;

	/**
	 * <p>Constructor for DocumentTypeImpl.</p>
	 *
	 * @param qname a {@link java.lang.String} object.
	 * @param publicId a {@link java.lang.String} object.
	 * @param systemId a {@link java.lang.String} object.
	 */
	public DocumentTypeImpl(String qname, String publicId, String systemId) {
		super();
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
	public NodeType getNodeType() {
		return NodeType.DOCUMENT_TYPE_NODE;
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

	/** {@inheritDoc} */
	@Override
	public String getSystemId() {
		return this.systemId;
	}
}
