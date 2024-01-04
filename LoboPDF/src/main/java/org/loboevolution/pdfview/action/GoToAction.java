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

package org.loboevolution.pdfview.action;

import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * An action which specifies going to a particular destination
 */
public class GoToAction extends PDFAction {
    /**
     * the destination to go to
     */
    private final PDFDestination dest;

    /**
     * Creates a new instance of GoToAction from an object
     *
     * @param obj  the PDFObject with the action information
     * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public GoToAction(final PDFObject obj, final PDFObject root) throws IOException {
        super("GoTo");

        // find the destination
        final PDFObject destObj = obj.getDictRef("D");
        if (destObj == null) {
            throw new PDFParseException("No destination in GoTo action " + obj);
        }

        // parse it
        this.dest = PDFDestination.getDestination(destObj, root);
    }

    /**
     * Create a new GoToAction from a destination
     *
     * @param dest a {@link org.loboevolution.pdfview.PDFDestination} object.
     */
    public GoToAction(final PDFDestination dest) {
        super("GoTo");

        this.dest = dest;
    }

    /**
     * Get the destination this action refers to
     *
     * @return a {@link org.loboevolution.pdfview.PDFDestination} object.
     */
    public PDFDestination getDestination() {
        return this.dest;
    }
}
