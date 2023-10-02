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
package org.jpedal.jbig2;

import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.image.JBIG2Bitmap;
import org.jpedal.jbig2.segment.Segment;
import org.jpedal.jbig2.segment.pageinformation.PageInformationSegment;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>JBIG2Decoder class.</p>
 */
public class JBIG2Decoder {

    private final JBIG2StreamDecoder streamDecoder;

    /**
     * Constructor
     */
    public JBIG2Decoder() {
        streamDecoder = new JBIG2StreamDecoder();
    }

    /**
     * If the data stream is taken from a PDF, there may be some global data. Pass any global data
     * in here.  Call this method before decodeJBIG2(...)
     *
     * @param data global data
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void setGlobalData(final byte[] data) throws JBIG2Exception {
        streamDecoder.setGlobalData(data);
    }

    /**
     * Decodes a JBIG2 image from a File object
     *
     * @param file File to decode
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void decodeJBIG2(final File file) throws IOException, JBIG2Exception {
        decodeJBIG2(file.getAbsolutePath());
    }

    /**
     * Decodes a JBIG2 image from a String path
     *
     * @param file Must be the full path to the image
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void decodeJBIG2(final String file) throws IOException, JBIG2Exception {
        decodeJBIG2(Files.newInputStream(Paths.get(file)));
    }

    /**
     * Decodes a JBIG2 image from an InputStream
     *
     * @param inputStream InputStream
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void decodeJBIG2(final InputStream inputStream) throws IOException, JBIG2Exception {
        final int availiable = inputStream.available();

        final byte[] bytes = new byte[availiable];
        inputStream.read(bytes);

        decodeJBIG2(bytes);
    }

    /**
     * Decodes a JBIG2 image from a byte array
     *
     * @param data the raw data stream
     * @throws java.io.IOException             if any.
     * @throws org.jpedal.jbig2.JBIG2Exception if any.
     */
    public void decodeJBIG2(final byte[] data) throws IOException, JBIG2Exception {
        streamDecoder.decodeJBIG2(data);
    }

    /**
     * <p>getPageAsBufferedImage.</p>
     *
     * @param page a int.
     * @return a {@link java.awt.image.BufferedImage} object.
     */
    public BufferedImage getPageAsBufferedImage(int page) {
        page++;
        final JBIG2Bitmap pageBitmap = streamDecoder.findPageSegement(page).getPageBitmap();

        final byte[] bytes = pageBitmap.getData(true);

        if (bytes == null)
            return null;

        // make a a DEEP copy so we cant alter
        final int len = bytes.length;
        final byte[] copy = new byte[len];
        System.arraycopy(bytes, 0, copy, 0, len);

        // byte[] data = pageBitmap.getData(true).clone();
        final int width = pageBitmap.getWidth();
        final int height = pageBitmap.getHeight();

        /** create an image from the raw data */
        final DataBuffer db = new DataBufferByte(copy, copy.length);

        final WritableRaster raster = Raster.createPackedRaster(db, width, height, 1, null);
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        image.setData(raster);


        return image;
    }

    /**
     * <p>isNumberOfPagesKnown.</p>
     *
     * @return a boolean.
     */
    public boolean isNumberOfPagesKnown() {
        return streamDecoder.isNumberOfPagesKnown();
    }

    /**
     * <p>getNumberOfPages.</p>
     *
     * @return a int.
     */
    public int getNumberOfPages() {
        final int pages = streamDecoder.getNumberOfPages();
        if (streamDecoder.isNumberOfPagesKnown() && pages != 0)
            return pages;

        int noOfPages = 0;

        final List<Segment> segments = getAllSegments();
        for (final Segment segment : segments) {
            if (segment.getSegmentHeader().getSegmentType() == Segment.PAGE_INFORMATION)
                noOfPages++;
        }

        return noOfPages;
    }

    /**
     * <p>getAllSegments.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Segment> getAllSegments() {
        return streamDecoder.getAllSegments();
    }

    /**
     * <p>findPageSegement.</p>
     *
     * @param page a int.
     * @return a {@link org.jpedal.jbig2.segment.pageinformation.PageInformationSegment} object.
     */
    public PageInformationSegment findPageSegement(int page) {
        page++;
        return streamDecoder.findPageSegement(page);
    }

    /**
     * <p>findSegment.</p>
     *
     * @param segmentNumber a int.
     * @return a {@link org.jpedal.jbig2.segment.Segment} object.
     */
    public Segment findSegment(final int segmentNumber) {
        return streamDecoder.findSegment(segmentNumber);
    }

    /**
     * <p>getPageAsJBIG2Bitmap.</p>
     *
     * @param page a int.
     * @return a {@link org.jpedal.jbig2.image.JBIG2Bitmap} object.
     */
    public JBIG2Bitmap getPageAsJBIG2Bitmap(int page) {
        page++;
        return streamDecoder.findPageSegement(page).getPageBitmap();
    }

    /**
     * <p>isRandomAccessOrganisationUsed.</p>
     *
     * @return a boolean.
     */
    public boolean isRandomAccessOrganisationUsed() {
        return streamDecoder.isRandomAccessOrganisationUsed();
    }
}
