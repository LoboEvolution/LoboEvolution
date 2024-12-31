/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.jpedal.jbig2.segment.symboldictionary;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Flags;

/**
 * <p>SymbolDictionaryFlags class.</p>
 */
@Slf4j
public class SymbolDictionaryFlags extends Flags {
    /**
     * Constant <code>SD_HUFF="SD_HUFF"</code>
     */
    public static final String SD_HUFF = "SD_HUFF";
    /**
     * Constant <code>SD_REF_AGG="SD_REF_AGG"</code>
     */
    public static final String SD_REF_AGG = "SD_REF_AGG";
    /**
     * Constant <code>SD_HUFF_DH="SD_HUFF_DH"</code>
     */
    public static final String SD_HUFF_DH = "SD_HUFF_DH";
    /**
     * Constant <code>SD_HUFF_DW="SD_HUFF_DW"</code>
     */
    public static final String SD_HUFF_DW = "SD_HUFF_DW";
    /**
     * Constant <code>SD_HUFF_BM_SIZE="SD_HUFF_BM_SIZE"</code>
     */
    public static final String SD_HUFF_BM_SIZE = "SD_HUFF_BM_SIZE";
    /**
     * Constant <code>SD_HUFF_AGG_INST="SD_HUFF_AGG_INST"</code>
     */
    public static final String SD_HUFF_AGG_INST = "SD_HUFF_AGG_INST";
    /**
     * Constant <code>BITMAP_CC_USED="BITMAP_CC_USED"</code>
     */
    public static final String BITMAP_CC_USED = "BITMAP_CC_USED";
    /**
     * Constant <code>BITMAP_CC_RETAINED="BITMAP_CC_RETAINED"</code>
     */
    public static final String BITMAP_CC_RETAINED = "BITMAP_CC_RETAINED";
    /**
     * Constant <code>SD_TEMPLATE="SD_TEMPLATE"</code>
     */
    public static final String SD_TEMPLATE = "SD_TEMPLATE";
    /**
     * Constant <code>SD_R_TEMPLATE="SD_R_TEMPLATE"</code>
     */
    public static final String SD_R_TEMPLATE = "SD_R_TEMPLATE";

    /**
     * {@inheritDoc}
     */
    public void setFlags(final int flagsAsInt) {
        this.flagsAsInt = flagsAsInt;

        /* extract SD_HUFF */
        flags.put(SD_HUFF, flagsAsInt & 1);

        /* extract SD_REF_AGG */
        flags.put(SD_REF_AGG, (flagsAsInt >> 1) & 1);

        /* extract SD_HUFF_DH */
        flags.put(SD_HUFF_DH, (flagsAsInt >> 2) & 3);

        /* extract SD_HUFF_DW */
        flags.put(SD_HUFF_DW, (flagsAsInt >> 4) & 3);

        /* extract SD_HUFF_BM_SIZE */
        flags.put(SD_HUFF_BM_SIZE, (flagsAsInt >> 6) & 1);

        /* extract SD_HUFF_AGG_INST */
        flags.put(SD_HUFF_AGG_INST, (flagsAsInt >> 7) & 1);

        /* extract BITMAP_CC_USED */
        flags.put(BITMAP_CC_USED, (flagsAsInt >> 8) & 1);

        /* extract BITMAP_CC_RETAINED */
        flags.put(BITMAP_CC_RETAINED, (flagsAsInt >> 9) & 1);

        /* extract SD_TEMPLATE */
        flags.put(SD_TEMPLATE, (flagsAsInt >> 10) & 3);

        /* extract SD_R_TEMPLATE */
        flags.put(SD_R_TEMPLATE, (flagsAsInt >> 12) & 1);

        if (JBIG2StreamDecoder.debug)
            log.info("flags: {} ", flags);
    }
}
