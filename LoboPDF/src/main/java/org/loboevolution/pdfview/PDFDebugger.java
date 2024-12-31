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

package org.loboevolution.pdfview;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * <p>PDFDebugger class.</p>
 */
@Slf4j
public class PDFDebugger {
    /**
     * Constant <code>DEBUG_DCTDECODE_DATA="debugdctdecode"</code>
     */
    public static final String DEBUG_DCTDECODE_DATA = "debugdctdecode";
    /**
     * Constant <code>DEBUG_TEXT=false</code>
     */
    public static final boolean DEBUG_TEXT = false;
    /**
     * Constant <code>DEBUG_IMAGES=false</code>
     */
    public static final boolean DEBUG_IMAGES = false;
    /**
     * Constant <code>DEBUG_OPERATORS=false</code>
     */
    public static final boolean DEBUG_OPERATORS = false;
    /**
     * Constant <code>DEBUG_PATH=false</code>
     */
    public static final boolean DEBUG_PATH = false;
    /**
     * Constant <code>DEBUG_STOP_AT_INDEX=0</code>
     */
    public static final int DEBUG_STOP_AT_INDEX = 0;
    /**
     * Constant <code>DISABLE_TEXT=false</code>
     */
    public static final boolean DISABLE_TEXT = false;
    /**
     * Constant <code>DISABLE_IMAGES=false</code>
     */
    public static final boolean DISABLE_IMAGES = false;
    /**
     * Constant <code>DISABLE_PATH_STROKE=false</code>
     */
    public static final boolean DISABLE_PATH_STROKE = false;
    /**
     * Constant <code>DISABLE_PATH_FILL=false</code>
     */
    public static final boolean DISABLE_PATH_FILL = false;
    /**
     * Constant <code>DISABLE_PATH_STROKE_FILL=false</code>
     */
    public static final boolean DISABLE_PATH_STROKE_FILL = false;
    /**
     * Constant <code>DISABLE_CLIP=false</code>
     */
    public static final boolean DISABLE_CLIP = false;
    /**
     * Constant <code>DISABLE_FORMS=false</code>
     */
    public static final boolean DISABLE_FORMS = false;
    /**
     * Constant <code>DISABLE_SHADER=false</code>
     */
    public static final boolean DISABLE_SHADER = false;
    /**
     * Constant <code>SHOW_TEXT_REGIONS=false</code>
     */
    public static final boolean SHOW_TEXT_REGIONS = false;
    /**
     * Constant <code>SHOW_TEXT_ANCHOR=false</code>
     */
    public static final boolean SHOW_TEXT_ANCHOR = false;
    /**
     * Constant <code>DISABLE_THUMBNAILS=false</code>
     */
    public static final boolean DISABLE_THUMBNAILS = false;
    /**
     * Constant <code>DRAW_DELAY=0</code>
     */
    public static final long DRAW_DELAY = 0;

    /**
     * Constant <code>debuglevel=4000</code>
     */
    public static int debuglevel = 4000;

    /**
     * <p>debugImage.</p>
     *
     * @param image a {@link java.awt.image.BufferedImage} object.
     * @param name  a {@link java.lang.String} object.
     */
    public static void debugImage(final BufferedImage image, final String name) {
        if (PDFDebugger.DEBUG_IMAGES) {
            if (image == null) {
                return;
            }
            try {
                // retrieve image
                final File outputfile = new File("D:/tmp/PDFimages/" + name + ".png");
                ImageIO.write(image, "png", outputfile);
            } catch (final IOException e) {
                BaseWatchable.getErrorHandler().publishException(e);
            }
        }
    }

    /**
     * <p>debug.</p>
     *
     * @param msg   a {@link java.lang.String} object.
     * @param level a {@link java.lang.Integer} object.
     */
    public static void debug(final String msg, final int level) {
        if (level > debuglevel) {
            log.info(escape(msg));
        }
    }

    // TODO: add debug level and print it? 

    /**
     * <p>debug.</p>
     *
     * @param msg a {@link java.lang.String} object.
     */
    public static void debug(final String msg) {
        debug(msg, debuglevel);
    }

    // TODO: add debug level and print it? 

    /**
     * <p>escape.</p>
     *
     * @param msg a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String escape(final String msg) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if (c != '\n' && (c < 32 || c >= 127)) {
                c = '?';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * <p>setDebugLevel.</p>
     *
     * @param level a {@link java.lang.Integer} object.
     */
    public static void setDebugLevel(final int level) {
        debuglevel = level;
    }

    /**
     * <p>dumpStream.</p>
     *
     * @param stream an array of {@link byte} objects.
     * @return a {@link java.lang.String} object.
     */
    public static String dumpStream(final byte[] stream) {
        return PDFDebugger.escape(new String(stream).replace('\r', '\n'));
    }

    /**
     * <p>logPath.</p>
     *
     * @param path      a {@link java.awt.geom.GeneralPath} object.
     * @param operation a {@link java.lang.String} object.
     */
    public static void logPath(final GeneralPath path, final String operation) {
        if (PDFDebugger.DEBUG_PATH) {
            if (operation != null) {
                log.info("Operation: {} ;", operation);
            }
            log.info("Current path: ");
            final Rectangle b = path.getBounds();
            if (b != null)
                log.info("        Bounds [x={},y={},width={},height={}]", b.x, b.y, b.width, b.height);
            final Point2D p = path.getCurrentPoint();
            if (p != null)
                log.info("        Point  [x={},y={}]", p.getX(), p.getY());
        }
    }

    /**
     * <p>dump.</p>
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public static void dump(final PDFObject obj) throws IOException {
        PDFDebugger.debug("dumping PDF object: " + obj);
        if (obj == null) {
            return;
        }
        final Map<String, PDFObject> dict = obj.getDictionary();
        PDFDebugger.debug("   dict = " + dict);
        for (final Map.Entry<String, PDFObject> entry : dict.entrySet()) {
            final String key = entry.getKey();
            PDFDebugger.debug("key = " + key + " value = " + dict.get(key));
        }
    }

    @SuppressWarnings("serial")
    public static class DebugStopException extends Exception {
        // nothing to do
    }

}
