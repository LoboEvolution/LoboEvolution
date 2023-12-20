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

package org.loboevolution.pdfview.decode;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * The abstract superclass of various predictor objects that undo well-known
 * prediction algorithms.
 */
public abstract class Predictor {
    /**
     * well known algorithms
     */
    public static final int TIFF = 0;
    /**
     * Constant <code>PNG=1</code>
     */
    public static final int PNG = 1;

    /**
     * the algorithm to use
     */
    private final int algorithm;

    /**
     * the number of colors per sample
     */
    private int colors = 1;

    /**
     * the number of bits per color component
     */
    private int bpc = 8;

    /**
     * the number of columns per row
     */
    private int columns = 1;

    /**
     * Create an instance of a predictor.  Use <code>getPredictor()</code>
     * instead of this.
     *
     * @param algorithm a {@link java.lang.Integer} object.
     */
    protected Predictor(final int algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Get an instance of a predictor
     *
     * @param params the filter parameters
     * @return a {@link org.loboevolution.pdfview.decode.Predictor} object.
     * @throws java.io.IOException if any.
     */
    public static Predictor getPredictor(final PDFObject params)
            throws IOException {
        // get the algorithm (required)
        final PDFObject algorithmObj = params.getDictRef("Predictor");
        if (algorithmObj == null) {
            // no predictor
            return null;
        }
        final int algorithm = algorithmObj.getIntValue();

        // create the predictor object
        Predictor predictor = null;
        switch (algorithm) {
            case 1:
                // no predictor
                return null;
            case 2:
                predictor = new TIFFPredictor();
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                predictor = new PNGPredictor();
                break;
            default:
                throw new PDFParseException("Unknown predictor: " + algorithm);
        }

        // read the colors (optional)
        final PDFObject colorsObj = params.getDictRef("Colors");
        if (colorsObj != null) {
            predictor.setColors(colorsObj.getIntValue());
        }

        // read the bits per component (optional)
        final PDFObject bpcObj = params.getDictRef("BitsPerComponent");
        if (bpcObj != null) {
            predictor.setBitsPerComponent(bpcObj.getIntValue());
        }

        // read the columns (optional)
        final PDFObject columnsObj = params.getDictRef("Columns");
        if (columnsObj != null) {
            predictor.setColumns(columnsObj.getIntValue());
        }

        // all set
        return predictor;
    }

    /**
     * Actually perform this algorithm on decoded image data.
     * Subclasses must implement this method
     *
     * @param imageData a {@link java.nio.ByteBuffer} object.
     * @return a {@link java.nio.ByteBuffer} object.
     * @throws java.io.IOException if any.
     */
    public abstract ByteBuffer unpredict(ByteBuffer imageData)
            throws IOException;

    /**
     * Get the algorithm in use
     *
     * @return one of the known algorithm types
     */
    public int getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Get the number of colors per sample
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getColors() {
        return this.colors;
    }

    /**
     * Set the number of colors per sample
     *
     * @param colors a {@link java.lang.Integer} object.
     */
    protected void setColors(final int colors) {
        this.colors = colors;
    }

    /**
     * Get the number of bits per color component
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getBitsPerComponent() {
        return this.bpc;
    }

    /**
     * Set the number of bits per color component
     *
     * @param bpc a {@link java.lang.Integer} object.
     */
    public void setBitsPerComponent(final int bpc) {
        this.bpc = bpc;
    }

    /**
     * Get the number of columns
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Set the number of columns
     *
     * @param columns a {@link java.lang.Integer} object.
     */
    public void setColumns(final int columns) {
        this.columns = columns;
    }
}
