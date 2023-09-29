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
package org.jpedal.jbig2.segment.region;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.util.BinaryOperation;

/**
 * <p>Abstract RegionSegment class.</p>
 */
@Slf4j
public abstract class RegionSegment extends Segment {
	protected int regionBitmapWidth, regionBitmapHeight;
	protected int regionBitmapXLocation, regionBitmapYLocation;

	protected final RegionFlags regionFlags = new RegionFlags();

	/**
	 * <p>Constructor for RegionSegment.</p>
	 *
	 * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
	 */
	public RegionSegment(final JBIG2StreamDecoder streamDecoder) {
		super(streamDecoder);
	}

	/**
	 * <p>readSegment.</p>
	 *
	 * @throws java.io.IOException if any.
	 * @throws org.jpedal.jbig2.JBIG2Exception if any.
	 */
	public void readSegment() throws IOException, JBIG2Exception {
		short[] buff = new short[4];
		decoder.readByte(buff);
		regionBitmapWidth = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapHeight = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			log.info("Bitmap size = {} x {} ",regionBitmapWidth, regionBitmapHeight);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapXLocation = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapYLocation = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			log.info("Bitmap location = {},{} ",regionBitmapXLocation, regionBitmapYLocation);

		/** extract region Segment flags */
		final short regionFlagsField = decoder.readByte();

		regionFlags.setFlags(regionFlagsField);

		if (JBIG2StreamDecoder.debug)
			log.info("region Segment flags = " + regionFlagsField);
	}
}
