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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.Node;

/**
 * <p>Abstract CharacterDataImpl class.</p>
 *
 *
 *
 */
public class CharacterDataImpl extends EventTargetImpl implements CharacterData {
	
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
	public void appendData(String arg) {
		this.text += arg;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void deleteData(int offset, int count) {
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
	public String getData() {
		return this.text;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.text.length();
	}

	/** {@inheritDoc} */
	@Override
	public String getTextContent() {
		return this.text;
	}

	/** {@inheritDoc} */
	@Override
	public void insertData(int offset, String arg) {
		final StringBuilder buffer = new StringBuilder(this.text);
		final StringBuilder result = buffer.insert(offset, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void replaceData(int offset, int count, String arg) {
		final StringBuilder buffer = new StringBuilder(this.text);
		final StringBuilder result = buffer.replace(offset, offset + count, arg);
		this.text = result.toString();
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) {
		this.text = data;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) {
		this.text = textContent;
		if (!this.notificationsSuspended) {
			informInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String substringData(int offset, int count) {
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
