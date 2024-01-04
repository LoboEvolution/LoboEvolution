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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.pdfview.PDFFile;

import javax.swing.*;
import java.awt.*;

/**
 * <p>NavigationToolbar class.</p>
 */
@Slf4j
public class NavigationToolbar extends JToolBar {

    private static final long serialVersionUID = 1L;

    private static final int FIRSTPAGE = 0;

    private static final int FBACKPAGE = 1;

    private static final int BACKPAGE = 2;

    private static final int FORWARDPAGE = 3;

    private static final int FFORWARDPAGE = 4;

    private static final int LASTPAGE = 5;

    private static final int SETPAGE = 6;

    /**
     * the current page number text field.
     */
    protected final JTextField currentPageBox = new JTextField(4);

    private final JLabel totalNoOfPages = new JLabel();

    private final PDFViewer PDFViewer;

    /**
     * <p>Constructor for NavigationToolbar.</p>
     *
     * @param PDFViewer a {@link org.loboevolution.pdf.PDFViewer} object.
     */
    public NavigationToolbar(final PDFViewer PDFViewer) {
        this.PDFViewer = PDFViewer;

        add(Box.createHorizontalGlue());

        addButton("Rewind To Start", "start.gif", FIRSTPAGE);
        addButton("Back 5 Pages", "fback.gif", FBACKPAGE);
        addButton("Back", "back.gif", BACKPAGE);

        add(new JLabel("Page"));
        currentPageBox.setText("1");
        currentPageBox.setMaximumSize(new Dimension(5, 50));
        currentPageBox.addActionListener(actionEvent -> executeCommand(SETPAGE));
        add(currentPageBox);
        add(totalNoOfPages);

        addButton("Forward", "forward.gif", FORWARDPAGE);
        addButton("Forward 5 Pages", "fforward.gif", FFORWARDPAGE);
        addButton("Fast Forward To End", "end.gif", LASTPAGE);

        add(Box.createHorizontalGlue());

    }

    /**
     * <p>Setter for the field <code>totalNoOfPages</code>.</p>
     *
     * @param noOfPages a {@link java.lang.Integer} object.
     */
    public void setTotalNoOfPages(final int noOfPages) {
        totalNoOfPages.setText("of " + noOfPages);
    }

    /**
     * <p>setCurrentPage.</p>
     *
     * @param currentPage a {@link java.lang.Integer} object.
     */
    public void setCurrentPage(final int currentPage) {
        currentPageBox.setText(String.valueOf(currentPage));
    }

    private void addButton(final String tooltip, final String url, final int type) {
        final JButton button = new JButton();
        button.setIcon(new ImageIcon(DesktopConfig.getResourceFile(url,DesktopConfig.PATH_PDF_IMAGE)));
        button.setToolTipText(tooltip);
        button.addActionListener(actionEvent -> executeCommand(type));
        add(button);
    }

    /**
     * <p>executeCommand.</p>
     *
     * @param type a {@link java.lang.Integer} object.
     */
    public void executeCommand(final int type) {
        switch (type) {
            case FIRSTPAGE:
                PDFViewer.doFirst();
                break;
            case FBACKPAGE:
                PDFViewer.gotoPage(PDFViewer.curpage - 5);
                break;
            case BACKPAGE:
                PDFViewer.doPrev();
                break;
            case FORWARDPAGE:
                PDFViewer.doNext();
                break;
            case FFORWARDPAGE:
                PDFViewer.gotoPage(PDFViewer.curpage + 5);
                break;
            case LASTPAGE:
                PDFViewer.doLast();
                break;
            case SETPAGE:
                int pagenum = -1;
                final PDFFile curFile = PDFViewer.curFile;
                final int curpage = PDFViewer.curpage;
                try {
                    pagenum = Integer.parseInt(currentPageBox.getText()) - 1;
                } catch (final NumberFormatException e) {
                    log.info(e.getMessage());
                }
                if (pagenum >= curFile.getNumPages()) {
                    pagenum = curFile.getNumPages() - 1;
                }
                if (pagenum >= 0) {
                    if (pagenum != curpage) {
                        PDFViewer.gotoPage(pagenum);
                    }
                } else {
                    currentPageBox.setText(String.valueOf(curpage));
                }
                break;
            default:
                break;
        }
        currentPageBox.setText(String.valueOf(PDFViewer.curpage + 1));
    }
}
