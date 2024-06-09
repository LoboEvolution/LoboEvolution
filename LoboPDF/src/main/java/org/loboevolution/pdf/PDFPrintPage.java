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

package org.loboevolution.pdf;

import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFPage;
import org.loboevolution.pdfview.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.Serial;

/**
 * A class representing a print job for a particular PDFFile. The class
 * maintains a status dialog as it prints, allowing the user to cancel the print
 * job.
 */
public class PDFPrintPage implements Printable {

    /**
     * The PDFFile to be printed.
     */
    private final PDFFile file;

    /**
     * The PrinterJob for this print job.
     */
    private PrinterJob pjob;

    /**
     * A dialog box indicating printing status, with cancel button.
     */
    private JDialog pd;

    /**
     * The text in the progress dialog indicating the current page.
     */
    private JLabel pagenumlabel;

    /**
     * The cancel button in the progress dialog.
     */
    private JButton cancel;

    /**
     * Create a new PDFPrintPage object for a particular PDFFile.
     *
     * @param file the PDFFile to be printed.
     */
    public PDFPrintPage(final PDFFile file) {
        this.file = file;
    }

    /**
     * Generates the status dialog with cancel button.
     */
    private void createPrintDialog() {
        pd = new JDialog((Frame) null, "Printing...", false);
        final Container top = pd.getContentPane();
        final Box lines = Box.createVerticalBox();
        Box line = Box.createHorizontalBox();
        line.add(new JLabel("Now printing: "));
        final JLabel title = new JLabel("file.pdf");
        line.add(title);
        lines.add(line);

        line = Box.createHorizontalBox();
        line.add(Box.createHorizontalStrut(10));
        line.add(new JLabel("page "));
        pagenumlabel = new JLabel("1");
        line.add(pagenumlabel);
        line.add(new JLabel(" of "));
        final JLabel totalpages = new JLabel(String.valueOf(file.getNumPages()));
        line.add(totalpages);
        lines.add(line);

        top.add(lines, BorderLayout.CENTER);

        final Box cancelbox = Box.createHorizontalBox();
        cancelbox.add(Box.createHorizontalGlue());
        cancel = new JButton(new AbstractAction("Cancel") {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent evt) {
                doCancel();
            }
        });
        cancelbox.add(cancel);
        top.add(cancelbox, BorderLayout.SOUTH);
    }

    /**
     * Show the progress dialog for this print job
     *
     * @param pjob the PrinterJob representing the print job
     */
    public void show(final PrinterJob pjob) {
        this.pjob = pjob;
        if (pd == null) {
            createPrintDialog();
        }
        pd.pack();
        pd.setVisible(true);
    }

    /**
     * Close the progress dialog. Don't use this method to cancel the print job;
     * use {@link #doCancel doCancel} instead.
     */
    public void hide() {
        pd.dispose();
    }

    /**
     * Cancel the print job. Disables the cancel button, as it might take a
     * while for the cancel to take effect.
     */
    public void doCancel() {
        cancel.setEnabled(false);
        pjob.cancel();
    }

    // from Printable interface: prints a single page, given a Graphics
    // to draw into, the page format, and the page number.

    /**
     * {@inheritDoc}
     */
    @Override
    public int print(final Graphics g, final PageFormat format, final int index) throws PrinterException {
        final int pagenum = index + 1;

        // don't bother if the page number is out of range.
        if (pagenum >= 1 && pagenum <= file.getNumPages()) {

            // update the page number in the progress dialog
            if (pagenumlabel != null) {
                pagenumlabel.setText(String.valueOf(pagenum));
            }

            // fit the PDFPage into the printing area
            final Graphics2D g2 = (Graphics2D) g;
            final PDFPage page = file.getPage(pagenum);
            double pwidth = format.getImageableWidth();
            double pheight = format.getImageableHeight();

            final double aspect = page.getAspectRatio();

            // handle page orientation matching
            double paperaspect = pwidth / pheight;
            if (paperaspect < 1.0) {
                switch (format.getOrientation()) {
                    case PageFormat.REVERSE_LANDSCAPE:
                    case PageFormat.LANDSCAPE:
                        format.setOrientation(PageFormat.PORTRAIT);
                        break;
                    case PageFormat.PORTRAIT:
                        format.setOrientation(PageFormat.LANDSCAPE);
                        break;
                    default:
                        break;
                }
                pwidth = format.getImageableWidth();
                pheight = format.getImageableHeight();
                paperaspect = pwidth / pheight;
            }

            final Rectangle imgbounds;
            final int width;
            final int height;
            if (aspect > paperaspect) {
                // paper is too tall / pdfpage is too wide
                height = (int) (pwidth / aspect);
                width = (int) pwidth;
            } else {
                // paper is too wide / pdfpage is too tall
                width = (int) (pheight * aspect);
                height = (int) pheight;
            }
            imgbounds = new Rectangle((int) format.getImageableX(), (int) format.getImageableY(), width, height);

            // render the page
            final PDFRenderer pgs = new PDFRenderer(page, g2, imgbounds, null, null);
            try {
                page.waitForFinish();
                pgs.run();
            } catch (final InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
