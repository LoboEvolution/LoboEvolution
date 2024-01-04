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
package org.jpedal.jbig2.jai;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>JBIG2ImageReaderSpi class.</p>
 */
public class JBIG2ImageReaderSpi extends ImageReaderSpi {

    /**
     * <p>Constructor for JBIG2ImageReaderSpi.</p>
     */
    public JBIG2ImageReaderSpi() {
        super("JPedal", // vendorName
                "1.0", // version
                new String[]{"jbig2", "JBIG2"}, // names
                new String[]{"jb2", "jbig2", "JB2", "JBIG2"}, // suffixes
                new String[]{"image/x-jbig2", "image/x-jb2"}, // MIMETypes
                "org.jpedal.jbig2.jai.JBIG2ImageReader", // readerClassName
                new Class[]{ImageInputStream.class}, // inputTypes
                null, // writerSpiNames
                false, // supportsStandardStreamMetadataFormat
                null, // nativeStreamMetadataFormatName
                null, // nativeStreamMetadataFormatClassName
                null, // extraStreamMetadataFormatNames
                null, // extraStreamMetadataFormatClassNames
                false, // supportsStandardImageMetadataFormat
                null, // nativeImageMetadataFormatName
                null, // nativeImageMetadataFormatClassName
                null, // extraImageMetadataFormatNames
                null); // extraImageMetadataFormatClassNames

    }

    /**
     * {@inheritDoc}
     */
    public boolean canDecodeInput(final Object input) throws IOException {

        // The input source must be an ImageInputStream because the constructor
        // passes STANDARD_INPUT_TYPE (an array consisting of ImageInputStream)
        // as the only type of input source that it will deal with to its
        // superclass.

        if (!(input instanceof ImageInputStream))
            return false;

        final ImageInputStream stream = (ImageInputStream) input;

        // Read and validate the input source's header.
        final byte[] header = new byte[8];
        try {
            // The input source's current position must be preserved so that
            // other ImageReaderSpis can determine if they can decode the input
            // source's format, should this input source be unable to handle the
            // decoding. Because the input source is an ImageInputStream, its
            // mark() and reset() methods are called to preserve the current
            // position.

            stream.mark();
            stream.read(header);
            stream.reset();
        } catch (final IOException e) {
            return false;
        }

        final byte[] controlHeader = new byte[]{(byte) 151, 74, 66, 50, 13, 10, 26, 10};

        return Arrays.equals(controlHeader, header);
    }

    /**
     * {@inheritDoc}
     */
    public ImageReader createReaderInstance(final Object extension) throws IOException {
        // Inform the JBIG2 image reader that this JBIG2 image reader SPI is the
        // originating provider -- the object that creates the JBIG2 image
        // reader.
        return new JBIG2ImageReader(this);
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription(final java.util.Locale locale) {
        return "JPedal JBIG2 Image Decoder provided by IDRsolutions.  See http://www.jpedal.org/jbig.php";
    }
}
