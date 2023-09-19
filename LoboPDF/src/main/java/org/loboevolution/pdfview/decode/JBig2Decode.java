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

package org.loboevolution.pdfview.decode;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.jpedal.jbig2.JBIG2Decoder;
import org.jpedal.jbig2.JBIG2Exception;
import org.loboevolution.pdfview.PDFObject;

/**
 ***************************************************************************
 * Decoder for jbig2 images within PDFs.
 * Copied from
 * https://pdf-renderer.dev.java.net/issues/show_bug.cgi?id=67
 *
 *  Problem is also described in:
 *	http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4799898
 *
 * @since 17.11.2010
 ***************************************************************************
  *
  *
 */
public class JBig2Decode {	
	/**
	 * <p>decode.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 * @param params a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @return a {@link java.nio.ByteBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	protected static ByteBuffer decode(PDFObject dict, ByteBuffer buf,
			PDFObject params) throws IOException {

		byte[] bytes = new byte[buf.remaining()];
		buf.get(bytes, 0, bytes.length);

		return ByteBuffer.wrap(decode(dict, bytes));
	}


	/**
	 * <p>decode.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param source an array of {@link byte} objects.
	 * @return an array of {@link byte} objects.
	 * @throws java.io.IOException if any.
	 */
	protected static byte[] decode(PDFObject dict, byte[] source) throws IOException {
		JBIG2Decoder decoder;
		decoder = new JBIG2Decoder();
		try {
			byte[] globals = getOptionFieldBytes(dict, "JBIG2Globals");
			if (globals != null) {
				decoder.setGlobalData(globals);
			}
			decoder.decodeJBIG2(source);
		} catch (JBIG2Exception ex) {
			IOException ioException;

			ioException = new IOException();
			ioException.initCause(ex);
			throw ioException;
		}
		return decoder.getPageAsJBIG2Bitmap(0).getData(true);
	}


	/**
	 * <p>getOptionFieldBytes.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param name a {@link java.lang.String} object.
	 * @return an array of {@link byte} objects.
	 * @throws java.io.IOException if any.
	 */
	public static byte[] getOptionFieldBytes(PDFObject dict, String name) throws IOException {

		PDFObject dictParams =  dict.getDictRef("DecodeParms");

		if (dictParams == null) {
			return null;
		}
		PDFObject value = dictParams.getDictRef(name);
		if (value == null) {
			return null;
		}
		return value.getStream();
	}

}
