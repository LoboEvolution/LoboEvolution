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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.CharacterData;

/**
 * <p>Abstract CharacterDataImpl class.</p>
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
		int dl = text.length();
		if (offset < 0 || count < 0 || offset >= dl) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
		}

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
		int dl = text.length();
		if (offset < 0 || offset > dl) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
		}
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
		try {
			int dl = text.length();
			if (offset < 0 || offset > dl) {
				throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
			}
			final StringBuilder buffer = new StringBuilder(this.text);
			final StringBuilder result = buffer.replace(offset, offset + count, arg);
			this.text = result.toString();
			if (!this.notificationsSuspended) {
				informInvalid();
			}
		} catch (StringIndexOutOfBoundsException e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			int dl = text.length();
			if (offset < 0 || offset > dl || count < 0) {
				throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
			}
			return this.text.substring(offset, offset + count);
		} catch (StringIndexOutOfBoundsException e) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Wrong arguments");
		}
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
