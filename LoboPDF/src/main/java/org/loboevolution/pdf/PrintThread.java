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
package org.loboevolution.pdf;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * A thread for printing in.
 */
public class PrintThread extends Thread {

    /**
     * The pt pages.
     */
    private final PDFPrintPage ptPages;

    /**
     * The pt pjob.
     */
    private final PrinterJob ptPjob;

    private final PDFViewer dialog;

    /**
     * Instantiates a new prints the thread.
     *
     * @param pages  the pages
     * @param pjob   the pjob
     * @param dialog a {@link org.loboevolution.pdf.PDFViewer} object.
     */
    public PrintThread(final PDFPrintPage pages, final PrinterJob pjob, final PDFViewer dialog) {
        ptPages = pages;
        ptPjob = pjob;
        setName(getClass().getName());
        this.dialog = dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            ptPages.show(ptPjob);
            ptPjob.print();
        } catch (final PrinterException pe) {
            JOptionPane.showMessageDialog(dialog, "Printing Error: " + pe.getMessage(), "Print Aborted",
                    JOptionPane.ERROR_MESSAGE);
        }
        ptPages.hide();
    }
}
