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
 * Created on Oct 16, 2005
 */
package org.loboevolution.html.parser;

import org.xml.sax.Locator;

class LocatorImpl implements Locator {
	private final int columnNumber;
	private final int lineNumber;
	private final String publicId;
	private final String systemId;

	/**
	 * <p>Constructor for LocatorImpl.</p>
	 *
	 * @param pid a {@link java.lang.String} object.
	 * @param sid a {@link java.lang.String} object.
	 * @param lnumber a int.
	 * @param cnumber a int.
	 */
	public LocatorImpl(String pid, String sid, int lnumber, int cnumber) {
		super();
		this.publicId = pid;
		this.systemId = sid;
		this.lineNumber = lnumber;
		this.columnNumber = cnumber;
	}

	/** {@inheritDoc} */
	@Override
	public int getColumnNumber() {
		return this.columnNumber;
	}

	/** {@inheritDoc} */
	@Override
	public int getLineNumber() {
		return this.lineNumber;
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
