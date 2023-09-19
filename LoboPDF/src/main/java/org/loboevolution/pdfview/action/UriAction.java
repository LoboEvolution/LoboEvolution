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

package org.loboevolution.pdfview.action;

import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;

/**
 ***************************************************************************
 * URI action, containing a web link
 *
 * Author  Katja Sondermann
 * @since 07.07.2009
 ***************************************************************************
  *
 */
public class UriAction extends PDFAction {

	/** The URL this action links to */
	private final String uri;
	
	/**
	 ***********************************************************************
	 * Constructor, reading the URL from the given action object
	 *
	 * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @throws java.io.IOException if any.
	 */
	public UriAction(PDFObject obj, PDFObject root) throws IOException {
		super("URI");
		this.uri = PdfObjectParseUtil.parseStringFromDict("URI", obj, true);
	}
	
	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public UriAction(String uri) throws IOException {
		super("URI");
		this.uri = uri;
	}

	/**
	 ***********************************************************************
	 * Get the URI this action directs to
	 *
	 * @return String
	 ***********************************************************************
	 */
	public String getUri() {
		return this.uri;
	}
}
