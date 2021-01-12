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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

/**
 * <p>DocumentFragmentImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DocumentFragmentImpl extends NodeImpl implements DocumentFragment {
	/**
	 * <p>Constructor for DocumentFragmentImpl.</p>
	 */
	public DocumentFragmentImpl() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return new DocumentFragmentImpl();
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#document-fragment";
	}

	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return org.w3c.dom.Node.DOCUMENT_FRAGMENT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
	}
}
