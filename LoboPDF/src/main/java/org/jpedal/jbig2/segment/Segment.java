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
package org.jpedal.jbig2.segment;

import java.io.IOException;

import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.decoders.ArithmeticDecoder;
import org.jpedal.jbig2.decoders.HuffmanDecoder;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.decoders.MMRDecoder;

/**
 * <p>Abstract Segment class.</p>
 *
  *
  *
 */
public abstract class Segment {

	/** Constant <code>SYMBOL_DICTIONARY=0</code> */
	public static final int SYMBOL_DICTIONARY = 0;
	/** Constant <code>INTERMEDIATE_TEXT_REGION=4</code> */
	public static final int INTERMEDIATE_TEXT_REGION = 4;
	/** Constant <code>IMMEDIATE_TEXT_REGION=6</code> */
	public static final int IMMEDIATE_TEXT_REGION = 6;
	/** Constant <code>IMMEDIATE_LOSSLESS_TEXT_REGION=7</code> */
	public static final int IMMEDIATE_LOSSLESS_TEXT_REGION = 7;
	/** Constant <code>PATTERN_DICTIONARY=16</code> */
	public static final int PATTERN_DICTIONARY = 16;
	/** Constant <code>INTERMEDIATE_HALFTONE_REGION=20</code> */
	public static final int INTERMEDIATE_HALFTONE_REGION = 20;
	/** Constant <code>IMMEDIATE_HALFTONE_REGION=22</code> */
	public static final int IMMEDIATE_HALFTONE_REGION = 22;
	/** Constant <code>IMMEDIATE_LOSSLESS_HALFTONE_REGION=23</code> */
	public static final int IMMEDIATE_LOSSLESS_HALFTONE_REGION = 23;
	/** Constant <code>INTERMEDIATE_GENERIC_REGION=36</code> */
	public static final int INTERMEDIATE_GENERIC_REGION = 36;
	/** Constant <code>IMMEDIATE_GENERIC_REGION=38</code> */
	public static final int IMMEDIATE_GENERIC_REGION = 38;
	/** Constant <code>IMMEDIATE_LOSSLESS_GENERIC_REGION=39</code> */
	public static final int IMMEDIATE_LOSSLESS_GENERIC_REGION = 39;
	/** Constant <code>INTERMEDIATE_GENERIC_REFINEMENT_REGION=40</code> */
	public static final int INTERMEDIATE_GENERIC_REFINEMENT_REGION = 40;
	/** Constant <code>IMMEDIATE_GENERIC_REFINEMENT_REGION=42</code> */
	public static final int IMMEDIATE_GENERIC_REFINEMENT_REGION = 42;
	/** Constant <code>IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION=43</code> */
	public static final int IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION = 43;
	/** Constant <code>PAGE_INFORMATION=48</code> */
	public static final int PAGE_INFORMATION = 48;
	/** Constant <code>END_OF_PAGE=49</code> */
	public static final int END_OF_PAGE = 49;
	/** Constant <code>END_OF_STRIPE=50</code> */
	public static final int END_OF_STRIPE = 50;
	/** Constant <code>END_OF_FILE=51</code> */
	public static final int END_OF_FILE = 51;
	/** Constant <code>PROFILES=52</code> */
	public static final int PROFILES = 52;
	/** Constant <code>TABLES=53</code> */
	public static final int TABLES = 53;
	/** Constant <code>EXTENSION=62</code> */
	public static final int EXTENSION = 62;
	/** Constant <code>BITMAP=70</code> */
	public static final int BITMAP = 70;

	protected SegmentHeader segmentHeader;

	protected final HuffmanDecoder huffmanDecoder;

	protected final ArithmeticDecoder arithmeticDecoder;

	protected final MMRDecoder mmrDecoder;

	protected final JBIG2StreamDecoder decoder;

	/**
	 * <p>Constructor for Segment.</p>
	 *
	 * @param streamDecoder a {@link org.jpedal.jbig2.decoders.JBIG2StreamDecoder} object.
	 */
	public Segment(JBIG2StreamDecoder streamDecoder) {
		this.decoder = streamDecoder;

//		try {
			//huffDecoder = HuffmanDecoder.getInstance();
//			arithmeticDecoder = ArithmeticDecoder.getInstance();
			
			huffmanDecoder = decoder.getHuffmanDecoder();
			arithmeticDecoder = decoder.getArithmeticDecoder();
			mmrDecoder = decoder.getMMRDecoder();
			
//		} catch (JBIG2Exception e) {
//			logger.log(Level.SEVERE, e.getMessage(), e);
//		}
	}

	/**
	 * <p>readATValue.</p>
	 *
	 * @return a short.
	 * @throws java.io.IOException if any.
	 */
	protected short readATValue() throws IOException {
		short atValue;
		short c0 = atValue = decoder.readByte();

		if ((c0 & 0x80) != 0) {
			atValue |= -1 - 0xff;
		}

		return atValue;
	}

	/**
	 * <p>Getter for the field <code>segmentHeader</code>.</p>
	 *
	 * @return a {@link org.jpedal.jbig2.segment.SegmentHeader} object.
	 */
	public SegmentHeader getSegmentHeader() {
		return segmentHeader;
	}

	/**
	 * <p>Setter for the field <code>segmentHeader</code>.</p>
	 *
	 * @param segmentHeader a {@link org.jpedal.jbig2.segment.SegmentHeader} object.
	 */
	public void setSegmentHeader(SegmentHeader segmentHeader) {
		this.segmentHeader = segmentHeader;
	}

	/**
	 * <p>readSegment.</p>
	 *
	 * @throws java.io.IOException if any.
	 * @throws org.jpedal.jbig2.JBIG2Exception if any.
	 */
	public abstract void readSegment() throws IOException, JBIG2Exception;
}
