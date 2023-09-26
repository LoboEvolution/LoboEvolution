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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A BufferedImage subclass that holds a strong reference to its graphics
 * object.  This means that the graphics will never go away as long as
 * someone holds a reference to this image, and createGraphics() and
 * getGraphics() can be called multiple times safely, and will always return
 * the same graphics object.
 */
public class RefImage extends BufferedImage {

    /** a strong reference to the graphics object */
    private Graphics2D g;

    /**
     * Creates a new instance of RefImage
     *
     * @param width a int.
     * @param height a int.
     * @param type a int.
     */
    public RefImage(final int width, final int height, final int type) {
        super(width, height, type);
    }

	/**
	 * {@inheritDoc}
	 *
	 * Create a graphics object only if it is currently null, otherwise
	 * return the existing graphics object.
	 */
    @Override
	public Graphics2D createGraphics() {
        if (this.g == null) {
            this.g = super.createGraphics();
        }

        return this.g;
    }
}
