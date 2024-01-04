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
package org.jpedal.jbig2.segment;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;

/**
 * <p>SegmentHeader class.</p>
 */
@Slf4j
@Data
public class SegmentHeader {
    private int segmentNumber;

    private int segmentType;
    private boolean pageAssociationSizeSet;
    private boolean deferredNonRetainSet;

    private int referredToSegmentCount;
    private short[] rententionFlags;

    private int[] referredToSegments;
    private int pageAssociation;
    private int dataLength;

    /**
     * <p>setSegmentHeaderFlags.</p>
     *
     * @param SegmentHeaderFlags a short.
     */
    public void setSegmentHeaderFlags(final short SegmentHeaderFlags) {
        segmentType = SegmentHeaderFlags & 63; // 63 = 00111111
        pageAssociationSizeSet = (SegmentHeaderFlags & 64) == 64; // 64 = // 01000000
        deferredNonRetainSet = (SegmentHeaderFlags & 80) == 80; // 64 = 10000000

        if (JBIG2StreamDecoder.debug) {
            log.info("SegmentType {} ", segmentType);
            log.info("pageAssociationSizeSet {} ", pageAssociationSizeSet);
            log.info("deferredNonRetainSet {} ", deferredNonRetainSet);
        }
    }

    /**
     * <p>getSegmentDataLength.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getSegmentDataLength() {
        return dataLength;
    }
}
