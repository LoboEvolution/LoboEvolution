/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.domimpl;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * The Class DOMCharacterDataImpl.
 */
public abstract class DOMCharacterDataImpl extends DOMNodeImpl implements CharacterData {

	/** The text. */
	protected volatile String text;

	/**
	 * Instantiates a new DOM character data impl.
	 */
	public DOMCharacterDataImpl() {
		super();
	}

	/**
	 * Instantiates a new DOM character data impl.
	 *
	 * @param text
	 *            the text
	 */
	public DOMCharacterDataImpl(String text) {
		this.text = text;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName() {
		return "HTMLCharacterData";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getTextContent()
	 */
	@Override
	public String getTextContent() throws DOMException {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#setTextContent(java.lang.String)
	 */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.text = textContent;
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#cloneNode(boolean)
	 */
	@Override
	public Node cloneNode(boolean deep) {
		DOMCharacterDataImpl newNode = (DOMCharacterDataImpl) super.cloneNode(deep);
		newNode.setData(this.getData());
		return newNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#appendData(java.lang.String)
	 */
	@Override
	public void appendData(String arg) throws DOMException {
		this.text += arg;
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#deleteData(int, int)
	 */
	@Override
	public void deleteData(int offset, int count) throws DOMException {
		StringBuffer buffer = new StringBuffer(this.text);
		StringBuffer result = buffer.delete(offset, offset + count);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#getData()
	 */
	@Override
	public String getData() throws DOMException {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#getLength()
	 */
	@Override
	public int getLength() {
		return this.text.length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#insertData(int, java.lang.String)
	 */
	@Override
	public void insertData(int offset, String arg) throws DOMException {
		StringBuffer buffer = new StringBuffer(this.text);
		StringBuffer result = buffer.insert(offset, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#replaceData(int, int, java.lang.String)
	 */
	@Override
	public void replaceData(int offset, int count, String arg) throws DOMException {
		StringBuffer buffer = new StringBuffer(this.text);
		StringBuffer result = buffer.replace(offset, offset + count, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#setData(java.lang.String)
	 */
	@Override
	public void setData(String data) throws DOMException {
		this.text = data;
		if (!this.notificationsSuspended) {
			this.informInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.CharacterData#substringData(int, int)
	 */
	@Override
	public String substringData(int offset, int count) throws DOMException {
		return this.text.substring(offset, offset + count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#toString()
	 */
	@Override
	public String toString() {
		String someText = this.text;
		int length = someText.length();
		if ((someText != null) && (someText.length() > 32)) {
			someText = someText.substring(0, 29) + "...";
		}
		return this.getNodeName() + "[length=" + length + ",text=" + someText + "]";
	}

}
