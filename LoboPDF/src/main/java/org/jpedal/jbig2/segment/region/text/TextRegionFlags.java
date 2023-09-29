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
package org.jpedal.jbig2.segment.region.text;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Flags;

/**
 * <p>TextRegionFlags class.</p>
 */
@Slf4j
public class TextRegionFlags extends Flags {

	/** Constant <code>SB_HUFF="SB_HUFF"</code> */
	public static final String SB_HUFF = "SB_HUFF";
	/** Constant <code>SB_REFINE="SB_REFINE"</code> */
	public static final String SB_REFINE = "SB_REFINE";
	/** Constant <code>LOG_SB_STRIPES="LOG_SB_STRIPES"</code> */
	public static final String LOG_SB_STRIPES = "LOG_SB_STRIPES";
	/** Constant <code>REF_CORNER="REF_CORNER"</code> */
	public static final String REF_CORNER = "REF_CORNER";
	/** Constant <code>TRANSPOSED="TRANSPOSED"</code> */
	public static final String TRANSPOSED = "TRANSPOSED";
	/** Constant <code>SB_COMB_OP="SB_COMB_OP"</code> */
	public static final String SB_COMB_OP = "SB_COMB_OP";
	/** Constant <code>SB_DEF_PIXEL="SB_DEF_PIXEL"</code> */
	public static final String SB_DEF_PIXEL = "SB_DEF_PIXEL";
	/** Constant <code>SB_DS_OFFSET="SB_DS_OFFSET"</code> */
	public static final String SB_DS_OFFSET = "SB_DS_OFFSET";
	/** Constant <code>SB_R_TEMPLATE="SB_R_TEMPLATE"</code> */
	public static final String SB_R_TEMPLATE = "SB_R_TEMPLATE";

	/** {@inheritDoc} */
	public void setFlags(final int flagsAsInt) {
		this.flagsAsInt = flagsAsInt;

		/** extract SB_HUFF */
		flags.put(SB_HUFF, flagsAsInt & 1);

		/** extract SB_REFINE */
		flags.put(SB_REFINE, (flagsAsInt >> 1) & 1);

		/** extract LOG_SB_STRIPES */
		flags.put(LOG_SB_STRIPES, (flagsAsInt >> 2) & 3);

		/** extract REF_CORNER */
		flags.put(REF_CORNER, (flagsAsInt >> 4) & 3);

		/** extract TRANSPOSED */
		flags.put(TRANSPOSED, (flagsAsInt >> 6) & 1);

		/** extract SB_COMB_OP */
		flags.put(SB_COMB_OP, (flagsAsInt >> 7) & 3);

		/** extract SB_DEF_PIXEL */
		flags.put(SB_DEF_PIXEL, (flagsAsInt >> 9) & 1);

		int sOffset = (flagsAsInt >> 10) & 0x1f;
		if ((sOffset & 0x10) != 0) {
			sOffset |= -1 - 0x0f;
		}
		flags.put(SB_DS_OFFSET, sOffset);

		/** extract SB_R_TEMPLATE */
		flags.put(SB_R_TEMPLATE, (flagsAsInt >> 15) & 1);

		if (JBIG2StreamDecoder.debug)
			log.info("flags: {} ", flags);
	}
}
