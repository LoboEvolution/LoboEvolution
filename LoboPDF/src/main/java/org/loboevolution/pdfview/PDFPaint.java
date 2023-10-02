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
package org.loboevolution.pdfview;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * PDFPaint is some kind of shader that knows how to fill a path.
 * At the moment, only a solid color is implemented, but gradients
 * and textures should be possible, too.
 * <p>
 * Author Mike Wessler
 */
public class PDFPaint {

    private final Paint mainPaint;

    /**
     * create a new PDFPaint based on a solid color
     *
     * @param p a {@link java.awt.Paint} object.
     */
    protected PDFPaint(final Paint p) {
        this.mainPaint = p;
    }

    /**
     * get the PDFPaint representing a solid color
     *
     * @param c a {@link java.awt.Color} object.
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public static PDFPaint getColorPaint(final Color c) {
        return getPaint(c);
    }

    /**
     * get the PDFPaint representing a generic paint
     *
     * @param p a {@link java.awt.Paint} object.
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public static PDFPaint getPaint(final Paint p) {
        return new PDFPaint(p);
    }

    /**
     * fill a path with the paint, and record the dirty area.
     *
     * @param state the current graphics state
     * @param g     the graphics into which to draw
     * @param s     the path to fill
     * @return a {@link java.awt.geom.Rectangle2D} object.
     */
    public Rectangle2D fill(final PDFRenderer state, final Graphics2D g,
                            final GeneralPath s) {
        g.setPaint(this.mainPaint);
        g.fill(s);

        return s.createTransformedShape(g.getTransform()).getBounds2D();
    }

    /**
     * get the primary color associated with this PDFPaint.
     *
     * @return a {@link java.awt.Paint} object.
     */
    public Paint getPaint() {
        return this.mainPaint;
    }
}
