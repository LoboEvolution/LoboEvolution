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

package org.loboevolution.pdfview.colorspace;

import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.function.PDFFunction;

import java.awt.color.ColorSpace;

/**
 * A color space that uses another color space to return values, and a
 * function to map between values in the input and input values to the
 * alternate color space
 */
public class AlternateColorSpace extends PDFColorSpace {
    /**
     * The alternate color space
     */
    private final PDFColorSpace alternate;

    /**
     * The function
     */
    private final PDFFunction function;

    private AltColorSpace altcolorspace;

    /**
     * Creates a new instance of AlternateColorSpace
     *
     * @param alternate a {@link org.loboevolution.pdfview.colorspace.PDFColorSpace} object.
     * @param function  a {@link org.loboevolution.pdfview.function.PDFFunction} object.
     */
    public AlternateColorSpace(final PDFColorSpace alternate, final PDFFunction function) {
        super(null);

        this.alternate = alternate;
        this.function = function;
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the number of components expected in the getPaint command
     */
    @Override
    public int getNumComponents() {
        if (this.function != null) {
            return this.function.getNumInputs();
        } else {
            return this.alternate.getNumComponents();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the PDFPaint representing the color described by the
     * given color components
     */
    @Override
    public PDFPaint getPaint(float[] components) {
        if (this.function != null) {
            // translate values using function
            components = this.function.calculate(components);
        }

        return this.alternate.getPaint(components);
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the original Java ColorSpace.
     */
    @Override
    public ColorSpace getColorSpace() {
        if (altcolorspace == null) altcolorspace = new AltColorSpace(function, alternate.getColorSpace());
        return altcolorspace;
        //return this.alternate.getColorSpace();
    }

    /**
     * **********************************************************************
     * Get the PDF function
     *
     * @return PDFFunction
     * **********************************************************************
     */
    public PDFFunction getFunktion() {
        return this.function;
    }


}
