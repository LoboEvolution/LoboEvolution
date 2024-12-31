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

package org.loboevolution.pdfview.annotation;

import org.loboevolution.pdfview.PDFObject;

import java.io.IOException;

/**
 * **************************************************************************
 * PDF annotation describing a free text
 * Currently only supports the XObjects which can be found in the path AP N
 * of the annotation object (same implementation as the stamp annotation)
 * <p>
 * Author Katja Sondermann
 *
 * @since 28.03.2012
 * **************************************************************************
 */
public class FreetextAnnotation extends StampAnnotation {

    /**
     * **********************************************************************
     * Constructor
     *
     * @param annotObject a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public FreetextAnnotation(final PDFObject annotObject) throws IOException {
        super(annotObject, ANNOTATION_TYPE.FREETEXT);

    }
}
