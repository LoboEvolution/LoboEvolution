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
package org.jpedal.jbig2.segment.pattern;

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Flags;

/**
 * <p>PatternDictionaryFlags class.</p>
 */
@Slf4j
public class PatternDictionaryFlags extends Flags {
    /**
     * Constant <code>HD_MMR="HD_MMR"</code>
     */
    public static final String HD_MMR = "HD_MMR";
    /**
     * Constant <code>HD_TEMPLATE="HD_TEMPLATE"</code>
     */
    public static final String HD_TEMPLATE = "HD_TEMPLATE";

    /**
     * {@inheritDoc}
     */
    public void setFlags(final int flagsAsInt) {
        this.flagsAsInt = flagsAsInt;
        flags.put(HD_MMR, flagsAsInt & 1);
        flags.put(HD_TEMPLATE, (flagsAsInt >> 1) & 3);
        if (JBIG2StreamDecoder.debug)
            log.info("flags: {}", flags);
    }
}
