/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
 * <p>TextRegionHuffmanFlags class.</p>
 */
@Slf4j
public class TextRegionHuffmanFlags extends Flags {

    /**
     * Constant <code>SB_HUFF_FS="SB_HUFF_FS"</code>
     */
    public static final String SB_HUFF_FS = "SB_HUFF_FS";
    /**
     * Constant <code>SB_HUFF_DS="SB_HUFF_DS"</code>
     */
    public static final String SB_HUFF_DS = "SB_HUFF_DS";
    /**
     * Constant <code>SB_HUFF_DT="SB_HUFF_DT"</code>
     */
    public static final String SB_HUFF_DT = "SB_HUFF_DT";
    /**
     * Constant <code>SB_HUFF_RDW="SB_HUFF_RDW"</code>
     */
    public static final String SB_HUFF_RDW = "SB_HUFF_RDW";
    /**
     * Constant <code>SB_HUFF_RDH="SB_HUFF_RDH"</code>
     */
    public static final String SB_HUFF_RDH = "SB_HUFF_RDH";
    /**
     * Constant <code>SB_HUFF_RDX="SB_HUFF_RDX"</code>
     */
    public static final String SB_HUFF_RDX = "SB_HUFF_RDX";
    /**
     * Constant <code>SB_HUFF_RDY="SB_HUFF_RDY"</code>
     */
    public static final String SB_HUFF_RDY = "SB_HUFF_RDY";
    /**
     * Constant <code>SB_HUFF_RSIZE="SB_HUFF_RSIZE"</code>
     */
    public static final String SB_HUFF_RSIZE = "SB_HUFF_RSIZE";

    /**
     * {@inheritDoc}
     */
    public void setFlags(final int flagsAsInt) {
        this.flagsAsInt = flagsAsInt;

        /** extract SB_HUFF_FS */
        flags.put(SB_HUFF_FS, flagsAsInt & 3);

        /** extract SB_HUFF_DS */
        flags.put(SB_HUFF_DS, (flagsAsInt >> 2) & 3);

        /** extract SB_HUFF_DT */
        flags.put(SB_HUFF_DT, (flagsAsInt >> 4) & 3);

        /** extract SB_HUFF_RDW */
        flags.put(SB_HUFF_RDW, (flagsAsInt >> 6) & 3);

        /** extract SB_HUFF_RDH */
        flags.put(SB_HUFF_RDH, (flagsAsInt >> 8) & 3);

        /** extract SB_HUFF_RDX */
        flags.put(SB_HUFF_RDX, (flagsAsInt >> 10) & 3);

        /** extract SB_HUFF_RDY */
        flags.put(SB_HUFF_RDY, (flagsAsInt >> 12) & 3);

        /** extract SB_HUFF_RSIZE */
        flags.put(SB_HUFF_RSIZE, (flagsAsInt >> 14) & 1);

        if (JBIG2StreamDecoder.debug)
            log.info("flags: {} ", flags);
    }
}
