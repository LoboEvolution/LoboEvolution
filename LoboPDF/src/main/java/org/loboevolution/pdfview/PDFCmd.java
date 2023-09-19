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

import java.awt.geom.Rectangle2D;

/**
 * The abstract superclass of all drawing commands for a PDFPage.
 *
 * Author Mike Wessler
  *
 */
public abstract class PDFCmd {

    /**
     * mark the page or change the graphics state
     *
     * @param state the current graphics state;  may be modified during
     * execution.
     * @return the region of the page made dirty by executing this command
     *         or null if no region was touched.  Note this value should be
     *         in the coordinates of the image touched, not the page.
     */
    public abstract Rectangle2D execute(PDFRenderer state);

    /**
     * {@inheritDoc}
     *
     * a human readable representation of this command
     */
    @Override
    public String toString() {
        String name = getClass().getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot >= 0) {
            return name.substring(lastDot + 1);
        } else {
            return name;
        }
    }

    /**
     * the details of this command
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDetails() {
        return super.toString();
    }
}
