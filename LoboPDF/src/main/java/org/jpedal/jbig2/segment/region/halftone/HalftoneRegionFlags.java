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
package org.jpedal.jbig2.segment.region.halftone;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Flags;

/**
 * <p>HalftoneRegionFlags class.</p>
 */
@Slf4j
public class HalftoneRegionFlags extends Flags {

    /**
     * Constant <code>H_MMR="H_MMR"</code>
     */
    public static final String H_MMR = "H_MMR";
    /**
     * Constant <code>H_TEMPLATE="H_TEMPLATE"</code>
     */
    public static final String H_TEMPLATE = "H_TEMPLATE";
    /**
     * Constant <code>H_ENABLE_SKIP="H_ENABLE_SKIP"</code>
     */
    public static final String H_ENABLE_SKIP = "H_ENABLE_SKIP";
    /**
     * Constant <code>H_COMB_OP="H_COMB_OP"</code>
     */
    public static final String H_COMB_OP = "H_COMB_OP";
    /**
     * Constant <code>H_DEF_PIXEL="H_DEF_PIXEL"</code>
     */
    public static final String H_DEF_PIXEL = "H_DEF_PIXEL";

    /**
     * {@inheritDoc}
     */
    public void setFlags(final int flagsAsInt) {
        this.flagsAsInt = flagsAsInt;
        flags.put(H_MMR, flagsAsInt & 1);
        flags.put(H_TEMPLATE, (flagsAsInt >> 1) & 3);
        flags.put(H_ENABLE_SKIP, (flagsAsInt >> 3) & 1);
        flags.put(H_COMB_OP, (flagsAsInt >> 4) & 7);
        flags.put(H_DEF_PIXEL, (flagsAsInt >> 7) & 1);
        if (JBIG2StreamDecoder.debug)
            log.info("flags: {} ", flags);
    }
}
