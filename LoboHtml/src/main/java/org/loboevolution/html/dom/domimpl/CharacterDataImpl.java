/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * <p>Abstract CharacterDataImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class CharacterDataImpl extends NodeImpl implements CharacterData {
	protected volatile String text;

	/**
	 * <p>Constructor for CharacterDataImpl.</p>
	 */
	public CharacterDataImpl() {
		super();
	}

	/**
	 * <p>Constructor for CharacterDataImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public CharacterDataImpl(String text) {
		this.text = text;
	}

	/** {@inheritDoc} */
	@Override
	public void appendData(String arg) throws DOMException {
		this.text += arg;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#cloneNode(boolean)
	 */
	/** {@inheritDoc} */
	@Override
	public Node cloneNode(boolean deep) {
		final CharacterDataImpl newNode = (CharacterDataImpl) super.cloneNode(deep);
		newNode.setData(getData());
		return newNode;
	}

	/** {@inheritDoc} */
	@Override
	public void deleteData(int offset, int count) throws DOMException {
		final StringBuilder buffer = new StringBuilder(this.text);
		final StringBuilder result = buffer.delete(offset, offset + count);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/**
	 * <p>getClassName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getClassName() {
		return "HTMLCharacterData";
	}

	/** {@inheritDoc} */
	@Override
	public String getData() throws DOMException {
		return this.text;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.text.length();
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() throws DOMException {
		return this.text;
	}

	/** {@inheritDoc} */
	@Override
	public void insertData(int offset, String arg) throws DOMException {
		final StringBuilder buffer = new StringBuilder(this.text);
		final StringBuilder result = buffer.insert(offset, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void replaceData(int offset, int count, String arg) throws DOMException {
		final StringBuilder buffer = new StringBuilder(this.text);
		final StringBuilder result = buffer.replace(offset, offset + count, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) throws DOMException {
		this.text = data;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.text = textContent;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String substringData(int offset, int count) throws DOMException {
		return this.text.substring(offset, offset + count);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		String someText = this.text;
		final int length = someText.length();
		if (someText != null && someText.length() > 32) {
			someText = someText.substring(0, 29) + "...";
		}
		return getNodeName() + "[length=" + length + ",text=" + someText + "]";
	}

}
