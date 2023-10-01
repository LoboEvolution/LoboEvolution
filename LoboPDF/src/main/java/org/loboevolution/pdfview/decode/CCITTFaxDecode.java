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

import lombok.extern.slf4j.Slf4j;
import org.jpedal.io.filter.ccitt.CCITT1D;
import org.jpedal.io.filter.ccitt.CCITT2D;
import org.jpedal.io.filter.ccitt.CCITTDecoder;
import org.jpedal.io.filter.ccitt.CCITTMix;
import org.loboevolution.pdfview.PDFObject;

/**
 * <p>CCITTFaxDecode class.</p>
 */
@Slf4j
public class CCITTFaxDecode {

	/**
	 * <p>decode.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 * @param params a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @return a {@link java.nio.ByteBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	protected static ByteBuffer decode(final PDFObject dict, final ByteBuffer buf,
                                       final PDFObject params) throws IOException {

		final byte[] bytes = new byte[buf.remaining()];
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
	protected static byte[] decode(final PDFObject dict, final byte[] source) throws IOException {
		int width = 1728;
		PDFObject widthDef = dict.getDictRef("Width");
		if (widthDef == null) {
			widthDef = dict.getDictRef("W");
		}
		if (widthDef != null) {
			width = widthDef.getIntValue();
		}
		int height = 0;
		PDFObject heightDef = dict.getDictRef("Height");
		if (heightDef == null) {
			heightDef = dict.getDictRef("H");
		}
		if (heightDef != null) {
			height = heightDef.getIntValue();
		}

		final int columns = getOptionFieldInt(dict, "Columns", width);
		final int rows = getOptionFieldInt(dict, "Rows", height);
		final int k = getOptionFieldInt(dict, "K", 0);
		final boolean align = getOptionFieldBoolean(dict, "EncodedByteAlign", false);
		final boolean blackIsOne = getOptionFieldBoolean(dict, "BlackIs1", false);

		final CCITTDecoder decoder;
		if (k == 0){
			// Pure 1D decoding, group3
			decoder = new CCITT1D(source, columns, rows, blackIsOne, align);
		} else if (k < 0) {
			// Pure 2D, group 4
			decoder = new CCITT2D(source, columns, rows, blackIsOne, align);
		} else /*if (k > 0)*/ {
			// Mixed 1/2 D encoding we can use either for maximum compression
			// A 1D line can be followed by up to K-1 2D lines
			decoder = new CCITTMix(source, columns, rows, blackIsOne, align);
		}

		byte[] result;
		try {
			result = decoder.decode();
		} catch (final RuntimeException e) {
			log.info("Error decoding CCITTFax image k: {} ", k);
			if (k >= 0) {
				result = new CCITT2D(source, columns, rows, blackIsOne, align).decode();
			} else {
				throw e;
			}
		}

		return result;
	}

	/**
	 * <p>getOptionFieldInt.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a int.
	 * @return a int.
	 * @throws java.io.IOException if any.
	 */
	public static int getOptionFieldInt(final PDFObject dict, final String name, final int defaultValue) throws IOException {

        final PDFObject dictParams = getDecodeParams(dict);

		if (dictParams == null) {
			return defaultValue;
		}
		final PDFObject value = dictParams.getDictRef(name);
		if (value == null) {
			return defaultValue;
		}
		return value.getIntValue();
	}

	/**
	 * <p>getOptionFieldBoolean.</p>
	 *
	 * @param dict a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param name a {@link java.lang.String} object.
	 * @param defaultValue a boolean.
	 * @return a boolean.
	 * @throws java.io.IOException if any.
	 */
	public static boolean getOptionFieldBoolean(final PDFObject dict, final String name, final boolean defaultValue) throws IOException {

        final PDFObject dictParams = getDecodeParams(dict);

		if (dictParams == null) {
			return defaultValue;
		}
		final PDFObject value = dictParams.getDictRef(name);
		if (value == null) {
			return defaultValue;
		}
		return value.getBooleanValue();
	}

    private static PDFObject getDecodeParams(final PDFObject dict) throws IOException {
        final PDFObject decdParams = dict.getDictRef("DecodeParms");
        if (decdParams != null && decdParams.getType() == PDFObject.ARRAY) {
            return decdParams.getArray()[0];
        }
        return decdParams;
    }
}
