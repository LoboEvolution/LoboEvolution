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

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * <p>CommentImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CommentImpl extends CharacterDataImpl implements Comment {
	/**
	 * <p>Constructor for CommentImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public CommentImpl(String text) {
		super(text);
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return new CommentImpl(this.text);
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#comment";
	}

	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return Node.COMMENT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return getTextContent();
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		setTextContent(nodeValue);
	}
}
