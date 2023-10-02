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

import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFPage;

import java.awt.*;

/**
 * A class to pre-cache the next page for better UI response.
 */
class PagePreparer extends Thread {

    /**
     * The prep page.
     */
    private final int prepPage;
    private final PDFViewer PDFViewer;
    /**
     * The waitfor page.
     */
    private int waitforPage;

    /**
     * Creates a new PagePreparer to prepare the page after the current one.
     *
     * @param waitforPage the current page number, 0 based
     * @param PDFViewer   a {@link org.loboevolution.pdf.PDFViewer} object.
     */
    public PagePreparer(final int waitforPage, final PDFViewer PDFViewer) {
        setDaemon(true);
        setName(getClass().getName());
        this.waitforPage = waitforPage;
        this.prepPage = waitforPage + 1;
        this.PDFViewer = PDFViewer;
    }

    /**
     * Quit.
     */
    public void quit() {
        waitforPage = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        Dimension size = null;
        final PagePanel fspp = PDFViewer.fspp;
        final PagePanel page = PDFViewer.page;
        final int curpage = PDFViewer.curpage;
        final PDFFile curFile = PDFViewer.curFile;

        if (fspp != null) {
            fspp.waitForCurrentPage();
            size = fspp.getCurSize();
        } else if (page != null) {
            page.waitForCurrentPage();
            size = page.getCurSize();
        }
        if (waitforPage == curpage) {
            final PDFPage pdfPage = curFile.getPage(prepPage + 1, true);
            if (pdfPage != null && waitforPage == curpage) {
                pdfPage.getImage(size.width, size.height, null, null, true, true);
            }
        }
    }
}
