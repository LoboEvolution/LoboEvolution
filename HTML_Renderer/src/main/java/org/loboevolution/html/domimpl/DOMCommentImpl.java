/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.domimpl;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * The Class DOMCommentImpl.
 */
public class DOMCommentImpl extends DOMCharacterDataImpl implements Comment {

	/**
	 * Instantiates a new DOM comment impl.
	 *
	 * @param text
	 *            the text
	 */
	public DOMCommentImpl(String text) {
		super(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return "#comment";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return this.getTextContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMNodeImpl#setNodeValue(java.lang.String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.setTextContent(nodeValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.COMMENT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		return new DOMCommentImpl(this.text);
	}
}
