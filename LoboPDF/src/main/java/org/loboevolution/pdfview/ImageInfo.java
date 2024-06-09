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
package org.loboevolution.pdfview;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * <p>ImageInfo class.</p>
 */
public class ImageInfo {

    final int width;
    final int height;
    final Rectangle2D clip;
    Color bgColor;

    /**
     * <p>Constructor for ImageInfo.</p>
     *
     * @param width  a {@link java.lang.Integer} object.
     * @param height a {@link java.lang.Integer} object.
     * @param clip   a {@link java.awt.geom.Rectangle2D} object.
     */
    public ImageInfo(final int width, final int height, final Rectangle2D clip) {
        this(width, height, clip, Color.WHITE);
    }

    /**
     * <p>Constructor for ImageInfo.</p>
     *
     * @param width   a {@link java.lang.Integer} object.
     * @param height  a {@link java.lang.Integer} object.
     * @param clip    a {@link java.awt.geom.Rectangle2D} object.
     * @param bgColor a {@link java.awt.Color} object.
     */
    public ImageInfo(final int width, final int height, final Rectangle2D clip, final Color bgColor) {
        this.width = width;
        this.height = height;
        this.clip = clip;
        this.bgColor = bgColor;
    }

    // a hashcode that uses width, height and clip to generate its number

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int code = (this.width ^ this.height << 16);

        if (this.clip != null) {
            code ^= ((int) this.clip.getWidth() | (int) this.clip.getHeight()) << 8;
            code ^= ((int) this.clip.getMinX() | (int) this.clip.getMinY());
        }

        return code;
    }

    // an equals method that compares values

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ImageInfo ii)) {
            return false;
        }

        if (this.width != ii.width || this.height != ii.height) {
            return false;
        } else if (this.clip != null && ii.clip != null) {
            return this.clip.equals(ii.clip);
        } else return this.clip == null && ii.clip == null;
    }
}
