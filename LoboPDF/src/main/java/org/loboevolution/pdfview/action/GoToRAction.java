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

package org.loboevolution.pdfview.action;

import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;

import java.io.IOException;

/**
 * **************************************************************************
 * Action directing to a location within another PDF document
 * <p>
 * Author  Katja Sondermann
 *
 * @since 07.07.2009
 * **************************************************************************
 */
public class GoToRAction extends PDFAction {

    /**
     * the destination within the remote PDF file
     */
    private final PDFDestination destination;
    /**
     * the remote file this action refers to
     */
    private final String file;
    /**
     * Should the remote file be opened in a new window? (optional)
     */
    private boolean newWindow = false;

    /**
     * Creates a new instance of GoToRAction from an object
     *
     * @param obj  the PDFObject with the action information
     * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public GoToRAction(final PDFObject obj, final PDFObject root) throws IOException {
        super("GoToR");
        // find the destination and parse it
        this.destination = PdfObjectParseUtil.parseDestination("D", obj, root, true);

        // find the remote file and parse it
        this.file = PdfObjectParseUtil.parseStringFromDict("F", obj, true);

        // find the new window attribute and parse it if available
        this.newWindow = PdfObjectParseUtil.parseBooleanFromDict("NewWindow", obj, false);
    }

    /**
     * **********************************************************************
     * Create a new GoToRAction from the given attributes
     *
     * @param dest      a {@link org.loboevolution.pdfview.PDFDestination} object.
     * @param file      a {@link java.lang.String} object.
     * @param newWindow a {@link java.lang.String} object.
     *                  **********************************************************************
     */
    public GoToRAction(final PDFDestination dest, final String file, final boolean newWindow) {
        super("GoToR");
        this.file = file;
        this.destination = dest;
        this.newWindow = newWindow;
    }

    /**
     * **********************************************************************
     * Get the destination this action refers to
     *
     * @return PDFDestination
     * **********************************************************************
     */
    public PDFDestination getDestination() {
        return this.destination;
    }

    /**
     * **********************************************************************
     * Get the file this action refers to
     *
     * @return PDFDestination
     * **********************************************************************
     */
    public String getFile() {
        return this.file;
    }

    /**
     * **********************************************************************
     * Should the remote file be opened in a new window?
     *
     * @return boolean
     * **********************************************************************
     */
    public boolean isNewWindow() {
        return this.newWindow;
    }
}
