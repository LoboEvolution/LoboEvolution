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
package org.jpedal.jbig2.segment.pattern;

import java.io.IOException;
import java.util.logging.Logger;

import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.util.BinaryOperation;

/**
 * <p>PatternDictionarySegment class.</p>
 *
  *
  *
 */
public class PatternDictionarySegment extends Segment {
	
	private static final Logger logger = Logger.getLogger(PatternDictionarySegment.class.getName());
	final PatternDictionaryFlags patternDictionaryFlags = new PatternDictionaryFlags();
	private int width;
	private int height;
	private int grayMax;
	private JBIG2Bitmap[] bitmaps;
	private int size;

	/**
	 * <p>Constructor for PatternDictionarySegment.</p>
	 *
	 * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
	 */
	public PatternDictionarySegment(JBIG2StreamDecoder streamDecoder) {
		super(streamDecoder);
	}

	/**
	 * <p>readSegment.</p>
	 *
	 * @throws java.io.IOException if any.
	 * @throws org.jpedal.jbig2.JBIG2Exception if any.
	 */
	public void readSegment() throws IOException, JBIG2Exception {
		/** read text region Segment flags */
		readPatternDictionaryFlags();

		width = decoder.readByte();
		height = decoder.readByte();

		if (JBIG2StreamDecoder.debug)
			logger.info("pattern dictionary size = " + width + " , " + height);

		short[] buf = new short[4];
		decoder.readByte(buf);
		grayMax = BinaryOperation.getInt32(buf);

		if (JBIG2StreamDecoder.debug)
			logger.info("grey max = " + grayMax);

		boolean useMMR = patternDictionaryFlags.getFlagValue(PatternDictionaryFlags.HD_MMR) == 1;
		int template = patternDictionaryFlags.getFlagValue(PatternDictionaryFlags.HD_TEMPLATE);

		if (!useMMR) {
			arithmeticDecoder.resetGenericStats(template, null);
			arithmeticDecoder.start();
		}

		short[] genericBAdaptiveTemplateX = new short[4], genericBAdaptiveTemplateY = new short[4];

		genericBAdaptiveTemplateX[0] = (short) -width;
		genericBAdaptiveTemplateY[0] = 0;
		genericBAdaptiveTemplateX[1] = -3;
		genericBAdaptiveTemplateY[1] = -1;
		genericBAdaptiveTemplateX[2] = 2;
		genericBAdaptiveTemplateY[2] = -2;
		genericBAdaptiveTemplateX[3] = -2;
		genericBAdaptiveTemplateY[3] = -2;

		size = grayMax + 1;

		JBIG2Bitmap bitmap = new JBIG2Bitmap(size * width, height, arithmeticDecoder, huffmanDecoder, mmrDecoder);
		bitmap.clear(0);
		bitmap.readBitmap(useMMR, template, false, false, null, genericBAdaptiveTemplateX, genericBAdaptiveTemplateY, segmentHeader.getSegmentDataLength() - 7);

		JBIG2Bitmap[] bitmaps = new JBIG2Bitmap[size];

		int x = 0;
		for (int i = 0; i < size; i++) {
			bitmaps[i] = bitmap.getSlice(x, 0, width, height);
			x += width;
		}

        this.bitmaps = bitmaps;
	}


	/**
	 * <p>Getter for the field <code>bitmaps</code>.</p>
	 *
	 * @return an array of {@link org.jpedal.jbig2.image.JBIG2Bitmap} objects.
	 */
	public JBIG2Bitmap[] getBitmaps() {
		return bitmaps;
	}

	private void readPatternDictionaryFlags() throws IOException {
		short patternDictionaryFlagsField = decoder.readByte();

		patternDictionaryFlags.setFlags(patternDictionaryFlagsField);

		if (JBIG2StreamDecoder.debug)
			logger.info("pattern Dictionary flags = " + patternDictionaryFlagsField);
	}

	/**
	 * <p>Getter for the field <code>patternDictionaryFlags</code>.</p>
	 *
	 * @return a {@link org.jpedal.jbig2.segment.pattern.PatternDictionaryFlags} object.
	 */
	public PatternDictionaryFlags getPatternDictionaryFlags() {
		return patternDictionaryFlags;
	}

	/**
	 * <p>Getter for the field <code>size</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return size;
	}
}
