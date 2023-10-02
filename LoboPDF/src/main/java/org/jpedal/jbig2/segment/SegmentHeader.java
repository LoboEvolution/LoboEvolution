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

import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;

/**
 * <p>SegmentHeader class.</p>
 */
@Slf4j
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
     * <p>Getter for the field <code>referredToSegments</code>.</p>
     *
     * @return an array of {@link int} objects.
     */
    public int[] getReferredToSegments() {
        return referredToSegments;
    }

    /**
     * <p>Setter for the field <code>referredToSegments</code>.</p>
     *
     * @param referredToSegments an array of {@link int} objects.
     */
    public void setReferredToSegments(final int[] referredToSegments) {
        this.referredToSegments = referredToSegments;
    }

    /**
     * <p>Getter for the field <code>segmentType</code>.</p>
     *
     * @return a int.
     */
    public int getSegmentType() {
        return segmentType;
    }

    /**
     * <p>Setter for the field <code>segmentType</code>.</p>
     *
     * @param type a int.
     */
    public void setSegmentType(final int type) {
        this.segmentType = type;
    }

    /**
     * <p>Getter for the field <code>segmentNumber</code>.</p>
     *
     * @return a int.
     */
    public int getSegmentNumber() {
        return segmentNumber;
    }

    /**
     * <p>Setter for the field <code>segmentNumber</code>.</p>
     *
     * @param SegmentNumber a int.
     */
    public void setSegmentNumber(final int SegmentNumber) {
        this.segmentNumber = SegmentNumber;
    }

    /**
     * <p>isPageAssociationSizeSet.</p>
     *
     * @return a boolean.
     */
    public boolean isPageAssociationSizeSet() {
        return pageAssociationSizeSet;
    }

    /**
     * <p>isDeferredNonRetainSet.</p>
     *
     * @return a boolean.
     */
    public boolean isDeferredNonRetainSet() {
        return deferredNonRetainSet;
    }

    /**
     * <p>Getter for the field <code>referredToSegmentCount</code>.</p>
     *
     * @return a int.
     */
    public int getReferredToSegmentCount() {
        return referredToSegmentCount;
    }

    /**
     * <p>Setter for the field <code>referredToSegmentCount</code>.</p>
     *
     * @param referredToSegmentCount a int.
     */
    public void setReferredToSegmentCount(final int referredToSegmentCount) {
        this.referredToSegmentCount = referredToSegmentCount;
    }

    /**
     * <p>Getter for the field <code>rententionFlags</code>.</p>
     *
     * @return an array of {@link short} objects.
     */
    public short[] getRententionFlags() {
        return rententionFlags;
    }

    /**
     * <p>Setter for the field <code>rententionFlags</code>.</p>
     *
     * @param rententionFlags an array of {@link short} objects.
     */
    public void setRententionFlags(final short[] rententionFlags) {
        this.rententionFlags = rententionFlags;
    }

    /**
     * <p>Getter for the field <code>pageAssociation</code>.</p>
     *
     * @return a int.
     */
    public int getPageAssociation() {
        return pageAssociation;
    }

    /**
     * <p>Setter for the field <code>pageAssociation</code>.</p>
     *
     * @param pageAssociation a int.
     */
    public void setPageAssociation(final int pageAssociation) {
        this.pageAssociation = pageAssociation;
    }

    /**
     * <p>Setter for the field <code>dataLength</code>.</p>
     *
     * @param dataLength a int.
     */
    public void setDataLength(final int dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * <p>getSegmentDataLength.</p>
     *
     * @return a int.
     */
    public int getSegmentDataLength() {
        return dataLength;
    }
}
