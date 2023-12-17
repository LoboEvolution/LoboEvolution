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
package org.jpedal.jbig2.decoders;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jpedal.jbig2.JBIG2Exception;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.io.StreamReader;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.segment.SegmentHeader;
import org.jpedal.jbig2.segment.extensions.ExtensionSegment;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;
import org.jpedal.jbig2.segment.pattern.PatternDictionarySegment;
import org.jpedal.jbig2.segment.region.generic.GenericRegionSegment;
import org.jpedal.jbig2.segment.region.halftone.HalftoneRegionSegment;
import org.jpedal.jbig2.segment.region.refinement.RefinementRegionSegment;
import org.jpedal.jbig2.segment.region.text.TextRegionSegment;
import org.jpedal.jbig2.segment.stripes.EndOfStripeSegment;
import org.jpedal.jbig2.segment.symboldictionary.SymbolDictionarySegment;
import org.jpedal.jbig2.util.BinaryOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>JBIG2StreamDecoder class.</p>
 */
@Slf4j
@Data
public class JBIG2StreamDecoder {

    /**
     * Constant <code>debug=false</code>
     */
    public static final boolean debug = false;
    private final List<Segment> segments = new ArrayList<>();
    private final List<JBIG2Bitmap> bitmaps = new ArrayList<>();
    private StreamReader reader;
    private boolean noOfPagesKnown;
    private boolean randomAccessOrganisation;
    private int noOfPages = -1;
    private byte[] globalData;
    private ArithmeticDecoder arithmeticDecoder;
    private HuffmanDecoder huffmanDecoder;
    private MMRDecoder mmrDecoder;

    /**
     * <p>movePointer.</p>
     *
     * @param i a int.
     */
    public void movePointer(final int i) {
        reader.movePointer(i);
    }

    /**
     * <p>decodeJBIG2.</p>
     *
     * @param data an array of {@link byte} objects.
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void decodeJBIG2(final byte[] data) throws IOException, JBIG2Exception {
        reader = new StreamReader(data);

        resetDecoder();

        final boolean validFile = checkHeader();
        if (JBIG2StreamDecoder.debug)
            log.info("validFile {} ", validFile);

        if (!validFile) {
            /*
             * Assume this is a stream from a PDF so there is no file header,
             * end of page segments, or end of file segments. Organisation must
             * be sequential, and the number of pages is assumed to be 1.
             */

            noOfPagesKnown = true;
            randomAccessOrganisation = false;
            noOfPages = 1;

            /* check to see if there is any global data to be read */
            if (globalData != null) {
                /* set the reader to read from the global data */
                reader = new StreamReader(globalData);

                huffmanDecoder = new HuffmanDecoder(reader);
                mmrDecoder = new MMRDecoder(reader);
                arithmeticDecoder = new ArithmeticDecoder(reader);

                /* read in the global data segments */
                readSegments();

                /* set the reader back to the main data */
                reader = new StreamReader(data);
            } else {
                /*
                 * There's no global data, so move the file pointer back to the
                 * start of the stream
                 */
                reader.movePointer(-8);
            }
        } else {
            /*
             * We have the file header, so assume it is a valid stand-alone
             * file.
             */

            if (JBIG2StreamDecoder.debug)
                log.info("==== File Header ====");

            setFileHeaderFlags();

            if (JBIG2StreamDecoder.debug) {
                log.info("randomAccessOrganisation {} ", randomAccessOrganisation);
                log.info("noOfPagesKnown {} ", noOfPagesKnown);
            }

            if (noOfPagesKnown) {
                noOfPages = getNoOfPages();

                if (JBIG2StreamDecoder.debug)
                    log.info("noOfPages {} ", noOfPages);
            }
        }

        huffmanDecoder = new HuffmanDecoder(reader);
        mmrDecoder = new MMRDecoder(reader);
        arithmeticDecoder = new ArithmeticDecoder(reader);

        /* read in the main segment data */
        readSegments();
    }

    private void resetDecoder() {
        noOfPagesKnown = false;
        randomAccessOrganisation = false;

        noOfPages = -1;

        segments.clear();
        bitmaps.clear();
    }

    private void readSegments() throws IOException, JBIG2Exception {

        if (JBIG2StreamDecoder.debug)
            log.info("==== Segments ====");

        boolean finished = false;
        while (!reader.isFinished() && !finished) {

            final SegmentHeader segmentHeader = new SegmentHeader();

            if (JBIG2StreamDecoder.debug)
                log.info("==== Segment Header ====");

            readSegmentHeader(segmentHeader);

            // read the Segment data
            Segment segment = null;

            final int segmentType = segmentHeader.getSegmentType();
            final int[] referredToSegments = segmentHeader.getReferredToSegments();
            final int noOfReferredToSegments = segmentHeader.getReferredToSegmentCount();

            switch (segmentType) {
                case Segment.SYMBOL_DICTIONARY:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Segment Symbol Dictionary ====");

                    segment = new SymbolDictionarySegment(this);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.INTERMEDIATE_TEXT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Intermediate Text Region ====");

                    segment = new TextRegionSegment(this, false);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_TEXT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Text Region ====");

                    segment = new TextRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_LOSSLESS_TEXT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Lossless Text Region ====");

                    segment = new TextRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.PATTERN_DICTIONARY:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Pattern Dictionary ====");

                    segment = new PatternDictionarySegment(this);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.INTERMEDIATE_HALFTONE_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Intermediate Halftone Region ====");

                    segment = new HalftoneRegionSegment(this, false);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_HALFTONE_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Halftone Region ====");

                    segment = new HalftoneRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_LOSSLESS_HALFTONE_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Lossless Halftone Region ====");

                    segment = new HalftoneRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.INTERMEDIATE_GENERIC_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Intermediate Generic Region ====");

                    segment = new GenericRegionSegment(this, false);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_GENERIC_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Generic Region ====");

                    segment = new GenericRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_LOSSLESS_GENERIC_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Lossless Generic Region ====");

                    segment = new GenericRegionSegment(this, true);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.INTERMEDIATE_GENERIC_REFINEMENT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Intermediate Generic Refinement Region ====");

                    segment = new RefinementRegionSegment(this, false, referredToSegments, noOfReferredToSegments);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_GENERIC_REFINEMENT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate Generic Refinement Region ====");

                    segment = new RefinementRegionSegment(this, true, referredToSegments, noOfReferredToSegments);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Immediate lossless Generic Refinement Region ====");

                    segment = new RefinementRegionSegment(this, true, referredToSegments, noOfReferredToSegments);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.PAGE_INFORMATION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Page Information Dictionary ====");

                    segment = new PageInformationSegment(this);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                case Segment.END_OF_PAGE:
                    continue;

                case Segment.END_OF_STRIPE:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== End of Stripes ====");

                    segment = new EndOfStripeSegment(this);

                    segment.setSegmentHeader(segmentHeader);
                    break;

                case Segment.END_OF_FILE:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== End of File ====");

                    finished = true;

                    continue;

                case Segment.PROFILES:
                    if (JBIG2StreamDecoder.debug)
                        log.info("PROFILES UNIMPLEMENTED");
                    break;

                case Segment.TABLES:
                    if (JBIG2StreamDecoder.debug)
                        log.info("TABLES UNIMPLEMENTED");
                    break;

                case Segment.EXTENSION:
                    if (JBIG2StreamDecoder.debug)
                        log.info("==== Extensions ====");

                    segment = new ExtensionSegment(this);

                    segment.setSegmentHeader(segmentHeader);

                    break;

                default:
                    log.info("Unknown Segment type in JBIG2 stream");

                    break;
            }

            if (!randomAccessOrganisation) {
                segment.readSegment();
            }

            segments.add(segment);
        }

        if (randomAccessOrganisation) {
            for (final Segment segment : segments) {
                segment.readSegment();
            }
        }
    }

    /**
     * <p>findPageSegement.</p>
     *
     * @param page a int.
     * @return a {@link org.jpedal.jbig2.segment.pageinformation.PageInformationSegment} object.
     */
    public PageInformationSegment findPageSegement(final int page) {
        for (final Segment segment : segments) {
            final SegmentHeader segmentHeader = segment.getSegmentHeader();
            if (segmentHeader.getSegmentType() == Segment.PAGE_INFORMATION && segmentHeader.getPageAssociation() == page) {
                return (PageInformationSegment) segment;
            }
        }

        return null;
    }

    /**
     * <p>findSegment.</p>
     *
     * @param segmentNumber a int.
     * @return a {@link org.jpedal.jbig2.segment.Segment} object.
     */
    public Segment findSegment(final int segmentNumber) {
        for (final Segment segment : segments) {
            if (segment.getSegmentHeader().getSegmentNumber() == segmentNumber) {
                return segment;
            }
        }

        return null;
    }

    private void readSegmentHeader(final SegmentHeader segmentHeader) throws JBIG2Exception {
        handleSegmentNumber(segmentHeader);

        handleSegmentHeaderFlags(segmentHeader);

        handleSegmentReferredToCountAndRententionFlags(segmentHeader);

        handleReferedToSegmentNumbers(segmentHeader);

        handlePageAssociation(segmentHeader);

        if (segmentHeader.getSegmentType() != Segment.END_OF_FILE)
            handleSegmentDataLength(segmentHeader);
    }

    private void handlePageAssociation(final SegmentHeader segmentHeader) {
        final int pageAssociation;

        final boolean isPageAssociationSizeSet = segmentHeader.isPageAssociationSizeSet();
        if (isPageAssociationSizeSet) { // field is 4 bytes long
            final short[] buf = new short[4];
            reader.readByte(buf);
            pageAssociation = BinaryOperation.getInt32(buf);
        } else { // field is 1 byte long
            pageAssociation = reader.readByte();
        }

        segmentHeader.setPageAssociation(pageAssociation);

        if (JBIG2StreamDecoder.debug)
            log.info("pageAssociation {} ", pageAssociation);
    }

    private void handleSegmentNumber(final SegmentHeader segmentHeader) {
        final short[] segmentBytes = new short[4];
        reader.readByte(segmentBytes);

        final int segmentNumber = BinaryOperation.getInt32(segmentBytes);

        if (JBIG2StreamDecoder.debug)
            log.info("SegmentNumber {} ", segmentNumber);
        segmentHeader.setSegmentNumber(segmentNumber);
    }

    private void handleSegmentHeaderFlags(final SegmentHeader segmentHeader) {
        final short segmentHeaderFlags = reader.readByte();
        segmentHeader.setSegmentHeaderFlags(segmentHeaderFlags);
    }

    private void handleSegmentReferredToCountAndRententionFlags(final SegmentHeader segmentHeader) throws JBIG2Exception {
        final short referedToSegmentCountAndRetentionFlags = reader.readByte();

        int referredToSegmentCount = (referedToSegmentCountAndRetentionFlags & 224) >> 5; // 224
        // =
        // 11100000

        short[] retentionFlags;
        /* take off the first three bits of the first byte */
        final short firstByte = (short) (referedToSegmentCountAndRetentionFlags & 31); // 31 =
        // 00011111

        if (referredToSegmentCount <= 4) { // short form

            retentionFlags = new short[1];
            retentionFlags[0] = firstByte;

        } else if (referredToSegmentCount == 7) { // long form

            final short[] longFormCountAndFlags = new short[4];
            /* add the first byte of the four */
            longFormCountAndFlags[0] = firstByte;

            for (int i = 1; i < 4; i++)
                // add the next 3 bytes to the array
                longFormCountAndFlags[i] = reader.readByte();

            /* get the count of the referred to Segments */
            referredToSegmentCount = BinaryOperation.getInt32(longFormCountAndFlags);

            /* calculate the number of bytes in this field */
            final int noOfBytesInField = (int) Math.ceil(4 + ((referredToSegmentCount + 1) / 8d));
            // log.info("noOfBytesInField {} ",  noOfBytesInField);

            final int noOfRententionFlagBytes = noOfBytesInField - 4;
            retentionFlags = new short[noOfRententionFlagBytes];
            reader.readByte(retentionFlags);

        } else { // error
            throw new JBIG2Exception("Error, 3 bit Segment count field " + referredToSegmentCount);
        }

        segmentHeader.setReferredToSegmentCount(referredToSegmentCount);

        if (JBIG2StreamDecoder.debug)
            log.info("referredToSegmentCount {} ", referredToSegmentCount);

        segmentHeader.setRententionFlags(retentionFlags);

        if (JBIG2StreamDecoder.debug)
            log.info("retentionFlags = ");

        if (JBIG2StreamDecoder.debug) {
            for (final short s : retentionFlags) {
                log.info(s + " ");
            }
        }
    }

    private void handleReferedToSegmentNumbers(final SegmentHeader segmentHeader) {
        final int referredToSegmentCount = segmentHeader.getReferredToSegmentCount();
        final int[] referredToSegments = new int[referredToSegmentCount];

        final int segmentNumber = segmentHeader.getSegmentNumber();

        if (segmentNumber <= 256) {
            for (int i = 0; i < referredToSegmentCount; i++)
                referredToSegments[i] = reader.readByte();
        } else if (segmentNumber <= 65536) {
            final short[] buf = new short[2];
            for (int i = 0; i < referredToSegmentCount; i++) {
                reader.readByte(buf);
                referredToSegments[i] = BinaryOperation.getInt16(buf);
            }
        } else {
            final short[] buf = new short[4];
            for (int i = 0; i < referredToSegmentCount; i++) {
                reader.readByte(buf);
                referredToSegments[i] = BinaryOperation.getInt32(buf);
            }
        }

        segmentHeader.setReferredToSegments(referredToSegments);

        if (JBIG2StreamDecoder.debug) {
            log.info("referredToSegments = ");
            for (final int i : referredToSegments)
                log.info(i + " ");
            log.info("");
        }
    }

    private int getNoOfPages() {
        final short[] noOfPages = new short[4];
        reader.readByte(noOfPages);

        return BinaryOperation.getInt32(noOfPages);
    }

    private void handleSegmentDataLength(final SegmentHeader segmentHeader) {
        final short[] buf = new short[4];
        reader.readByte(buf);

        final int dateLength = BinaryOperation.getInt32(buf);
        segmentHeader.setDataLength(dateLength);

        if (JBIG2StreamDecoder.debug)
            log.info("dateLength {} ", dateLength);
    }

    private void setFileHeaderFlags() {
        final short headerFlags = reader.readByte();

        if ((headerFlags & 0xfc) != 0) {
            log.info("Warning, reserved bits (2-7) of file header flags are not zero " + headerFlags);
        }

        final int fileOrganisation = headerFlags & 1;
        randomAccessOrganisation = fileOrganisation == 0;

        final int pagesKnown = headerFlags & 2;
        noOfPagesKnown = pagesKnown == 0;
    }

    private boolean checkHeader() {
        final short[] controlHeader = new short[]{151, 74, 66, 50, 13, 10, 26, 10};
        final short[] actualHeader = new short[8];
        reader.readByte(actualHeader);

        return Arrays.equals(controlHeader, actualHeader);
    }

    /**
     * <p>readBits.</p>
     *
     * @param num a int.
     * @return a int.
     */
    public int readBits(final int num) {
        return reader.readBits(num);
    }

    /**
     * <p>readBit.</p>
     * @return a int.
     */
    public int readBit() {
        return reader.readBit();
    }

    /**
     * <p>readByte.</p>
     * @param buff an array of {@link short} objects.
     */
    public void readByte(final short[] buff) {
        reader.readByte(buff);
    }

    /**
     * <p>consumeRemainingBits.</p>
     */
    public void consumeRemainingBits() {
        reader.consumeRemainingBits();
    }

    /**
     * <p>readByte.</p>
     *
     * @return a short.
     * @throws java.io.IOException if any.
     */
    public short readByte() throws IOException {
        return reader.readByte();
    }

    /**
     * <p>appendBitmap.</p>
     *
     * @param bitmap a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     */
    public void appendBitmap(final JBIG2Bitmap bitmap) {
        bitmaps.add(bitmap);
    }

    /**
     * <p>findBitmap.</p>
     *
     * @param bitmapNumber a int.
     * @return a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     */
    public JBIG2Bitmap findBitmap(final int bitmapNumber) {
        for (final JBIG2Bitmap bitmap : bitmaps) {
            if (bitmap.getBitmapNumber() == bitmapNumber) {
                return bitmap;
            }
        }

        return null;
    }

    /**
     * <p>getPageAsJBIG2Bitmap.</p>
     *
     * @param i a int.
     * @return a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     */
    public JBIG2Bitmap getPageAsJBIG2Bitmap(final int i) {
        return findPageSegement(1).getPageBitmap();
    }

    /**
     * <p>isNumberOfPagesKnown.</p>
     *
     * @return a boolean.
     */
    public boolean isNumberOfPagesKnown() {
        return noOfPagesKnown;
    }

    /**
     * <p>getNumberOfPages.</p>
     * @return a int.
     */
    public int getNumberOfPages() {
        return noOfPages;
    }

    /**
     * <p>isRandomAccessOrganisationUsed.</p>
     * @return a boolean.
     */
    public boolean isRandomAccessOrganisationUsed() {
        return randomAccessOrganisation;
    }

    /**
     * <p>getAllSegments.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Segment> getAllSegments() {
        return segments;
    }
}
