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
package org.jpedal.jbig2.segment.pageinformation;

import java.io.IOException;
import java.util.logging.Logger;

import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.util.BinaryOperation;

/**
 * <p>PageInformationSegment class.</p>
 *
  *
  *
 */
public class PageInformationSegment extends Segment {

	private static final Logger logger = Logger.getLogger(PageInformationSegment.class.getName());
	private int pageBitmapHeight, pageBitmapWidth;
	private int yResolution, xResolution;

	final PageInformationFlags pageInformationFlags = new PageInformationFlags();
	private int pageStriping;

	private JBIG2Bitmap pageBitmap;

	/**
	 * <p>Constructor for PageInformationSegment.</p>
	 *
	 * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
	 */
	public PageInformationSegment(final JBIG2StreamDecoder streamDecoder) {
		super(streamDecoder);
	}

	/**
	 * <p>Getter for the field <code>pageInformationFlags</code>.</p>
	 *
	 * @return a {@link org.jpedal.jbig2.segment.pageinformation.PageInformationFlags} object.
	 */
	public PageInformationFlags getPageInformationFlags() {
		return pageInformationFlags;
	}

	/**
	 * <p>Getter for the field <code>pageBitmap</code>.</p>
	 *
	 * @return a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
	 */
	public JBIG2Bitmap getPageBitmap() {
		return pageBitmap;
	}

	/**
	 * <p>readSegment.</p>
	 *
	 * @throws java.io.IOException if any.
	 * @throws org.jpedal.jbig2.JBIG2Exception if any.
	 */
	public void readSegment() throws IOException, JBIG2Exception {

		if (JBIG2StreamDecoder.debug)
			logger.info("==== Reading Page Information Dictionary ====");

		short[] buff = new short[4];
		decoder.readByte(buff);
		pageBitmapWidth = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		pageBitmapHeight = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			logger.info("Bitmap size = " + pageBitmapWidth + 'x' + pageBitmapHeight);

		buff = new short[4];
		decoder.readByte(buff);
		xResolution = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		yResolution = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			logger.info("Resolution = " + xResolution + 'x' + yResolution);

		/** extract page information flags */
		final short pageInformationFlagsField = decoder.readByte();

		pageInformationFlags.setFlags(pageInformationFlagsField);

		if (JBIG2StreamDecoder.debug)
			logger.info("symbolDictionaryFlags = " + pageInformationFlagsField);

		buff = new short[2];
		decoder.readByte(buff);
		pageStriping = BinaryOperation.getInt16(buff);

		if (JBIG2StreamDecoder.debug)
			logger.info("Page Striping = " + pageStriping);

		final int defPix = pageInformationFlags.getFlagValue(PageInformationFlags.DEFAULT_PIXEL_VALUE);

		final int height;

		if (pageBitmapHeight == -1) {
			height = pageStriping & 0x7fff;
		} else {
			height = pageBitmapHeight;
		}

		pageBitmap = new JBIG2Bitmap(pageBitmapWidth, height, arithmeticDecoder, huffmanDecoder, mmrDecoder);
		pageBitmap.clear(defPix);
	}

	/**
	 * <p>Getter for the field <code>pageBitmapHeight</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPageBitmapHeight() {
		return pageBitmapHeight;
	}
}
