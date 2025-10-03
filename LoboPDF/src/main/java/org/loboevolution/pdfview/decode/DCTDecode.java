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

package org.loboevolution.pdfview.decode;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * decode a DCT encoded array into a byte array.  This class uses Java's
 * built-in JPEG image class to do the decoding.
 * <p>
 * Author Mike Wessler
 */
@Slf4j
public class DCTDecode {

    /**
     * decode an array of bytes in DCT format.
     * <p>
     * DCT is the format used by JPEG images, so this class simply
     * loads the DCT-format bytes as an image, then reads the bytes out
     * of the image to create the array.  Unfortunately, their most
     * likely use is to get turned BACK into an image, so this isn't
     * terribly efficient... but is is general... don't hit, please.
     * <p>
     * The DCT-encoded stream may have 1, 3 or 4 samples per pixel, depending
     * on the colorspace of the image.  In decoding, we look for the colorspace
     * in the stream object's dictionary to decide how to decode this image.
     * If no colorspace is present, we guess 3 samples per pixel.
     *
     * @param dict   the stream dictionary
     * @param buf    the DCT-encoded buffer
     * @param params the parameters to the decoder (ignored)
     * @return the decoded buffer
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    protected static ByteBuffer decode(final PDFObject dict, final ByteBuffer buf, final PDFObject params) throws PDFParseException {
        // BEGIN PATCH W. Randelshofer Completely rewrote decode routine in
        // order to
        // support JPEG images in the CMYK color space.
        final BufferedImage bimg = loadImageData(buf);
        final byte[] output = ImageDataDecoder.decodeImageData(bimg);
        return ByteBuffer.wrap(output);
        // END PATCH W. Randelshofer Completely rewrote decode routine in order
        // to
        // support JPEG images in the CMYK color space.

    }


    /*************************************************************************
     * @param buf  a {@link java.nio.ByteBuffer} object.
     * @return a {@link java.awt.image.BufferedImage} object.
     * @throws PDFParseException in case of error
     ************************************************************************/

    private static BufferedImage loadImageData(final ByteBuffer buf)
            throws PDFParseException {
        buf.rewind();
        final byte[] input = new byte[buf.remaining()];
        buf.get(input);
        BufferedImage bimg;
        try {
            try {
                bimg = ImageIO.read(new ByteArrayInputStream(input));
            } catch (final IllegalArgumentException colorProfileMismatch) {
                // we experienced this problem with an embedded jpeg
                // that specified a icc color profile with 4 components
                // but the raster had only 3 bands (apparently YCC encoded)
                final Image img = Toolkit.getDefaultToolkit().createImage(input);
                // wait until image is loaded using ImageIcon for convenience
                final ImageIcon imageIcon = new ImageIcon(img);
                // copy to buffered image
                bimg = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                bimg.getGraphics().drawImage(img, 0, 0, null);
            }
        } catch (final Exception ex) {
            throw new PDFParseException("DCTDecode failed", ex);
        }

        return bimg;
    }
}


/**
 * Image tracker.  I'm not sure why I'm not using the default Java
 * image tracker for this one.
 */
@Slf4j
class MyTracker implements ImageObserver {
    boolean done = false;

    /**
     * create a new MyTracker that watches this image.
     * The image will start loading immediately.
     *
     * @param img a {@link java.awt.Image} object.
     */
    public MyTracker(final Image img) {
        img.getWidth(this);
    }

    /**
     * {@inheritDoc}
     * More information has come in about the image.
     */
    @Override
    public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y,
                               final int width, final int height) {
        if ((infoflags & (ALLBITS | ERROR | ABORT)) != 0) {
            synchronized (this) {
                this.done = true;
                notifyAll();
            }
            return false;
        }
        return true;
    }

    /**
     * Wait until the image is done, then return.
     */
    public synchronized void waitForAll() {
        while (!this.done) {
            try {
                wait();
            } catch (final InterruptedException e) {
                log.info(e.getMessage());
            }
        }
    }
}
