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

package org.loboevolution.pdfview.decode;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * decode a JPX encoded imagestream into a byte array.  This class uses Java's
 * image_io JPEG2000 reader to do the decoding.
 * <p>
 * Author Bernd Rosstauscher
 */
public class JPXDecode {

    /**
     * <p>decode.</p>
     *
     * @param dict   a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param buf    a {@link java.nio.ByteBuffer} object.
     * @param params a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return a {@link java.nio.ByteBuffer} object.
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    protected static ByteBuffer decode(final PDFObject dict, final ByteBuffer buf, final PDFObject params) throws PDFParseException {
        final BufferedImage bimg = loadImageData(buf);
        final byte[] output = ImageDataDecoder.decodeImageData(bimg);
        return ByteBuffer.wrap(output);
    }

    /*************************************************************************
     * @param buf a {@link ByteBuffer} object.
     * @return a {@link BufferedImage} object.
     * @throws  {@link PDFParseException} object
     ************************************************************************/

    private static BufferedImage loadImageData(final ByteBuffer buf) throws PDFParseException {
        ImageReader reader = null;
        try {
            final byte[] input = new byte[buf.remaining()];
            buf.get(input);
            final Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType("image/jpeg2000");
            if (!readers.hasNext()) {
                throw new PDFParseException("JPXDecode failed. No reader available");
            }
            reader = readers.next();
            reader.setInput(new MemoryCacheImageInputStream(new ByteArrayInputStream(input)));
            final BufferedImage bimg = reader.read(0);
            return bimg;
        } catch (final IOException e) {
            throw new PDFParseException("JPXDecode failed", e);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
        }

    }
}
