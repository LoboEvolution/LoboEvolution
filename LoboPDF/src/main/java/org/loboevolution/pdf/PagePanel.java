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

import lombok.Getter;
import org.loboevolution.pdfview.PDFPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

/**
 * A Swing-based panel that displays a PDF page image.  If the zoom tool
 * is in use, allows the user to select a particular region of the image to
 * be zoomed.
 */
public class PagePanel extends JPanel implements ImageObserver, MouseListener {


    private static final long serialVersionUID = 1L;
    /**
     * a flag indicating whether the current page is done or not.
     */
    private final Flag flag = new Flag();
    /**
     * The vertical offset of the image from the top of the panel
     */
    int offy;
    /**
     * The image of the rendered PDF page being displayed
     */
    private Image image;
    /**
     * The current PDFPage that was rendered into image
     */
    @Getter
    private PDFPage page;
    /**
     * The horizontal offset of the image from the left edge of the panel
     */
    private int offx;
    /**
     * the size of the image
     */
    private Dimension size;

    /**
     * Create a new PDFPanel, with a default size of 800 by 600 pixels.
     */
    public PagePanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addMouseListener(this);
    }

    /**
     * Stop the generation of any previous page, and draw the new one.
     *
     * @param page the PDFPage to draw.
     */
    public synchronized void showPage(final PDFPage page) {
        // stop drawing the previous page
        if (page != null && size != null) {
            page.stop(size.width, size.height, null);
        }

        // set up the new page
        this.page = page;

        if (page == null) {
            // no page
            image = null;
            repaint();
        } else {
            flag.clear();

            // image should fit preferred size
            // image should fit size if panel is smaller than preferred size
            final int width = Math.min(getPreferredSize().width, getSize().width);
            final int height = Math.min(getPreferredSize().height, getSize().height);

            if (width + height > 0) {
                this.size = page.getUnstretchedSize(width, height, null);
                this.image = page.getImage(size.width, size.height, null, this);
                repaint();
            }
        }
    }

    /**
     * <p>getPreferredSize.</p>
     *
     * @return a {@link java.awt.Dimension} object.
     */
    public Dimension getPreferredSize() {
        return size == null ? super.getPreferredSize() : this.size;
    }

    /**
     * {@inheritDoc}
     */
    public void setPreferredSize(final Dimension size) {
        if (super.getPreferredSize() != size) {
            super.setPreferredSize(size);
            this.image = null;
            this.size = null;
            repaint();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Draw the image.
     */
    public void paint(final Graphics g) {
        final Dimension sz = getSize();
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (image == null) {
            if (page == null) {
                g.setColor(Color.black);
                g.drawString("No page selected", getWidth() / 2 - 30, getHeight() / 2);
            } else {
                showPage(page);
            }
        } else {
            // draw the image
            final int imageWidth = image.getWidth(null);
            final int imageHeight = image.getHeight(null);

            if (imageWidth <= sz.width && imageHeight <= sz.height) {
                // draw it centered within the panel
                this.offx = (sz.width - imageWidth) / 2;
                this.offy = (sz.height - imageHeight) / 2;
                g.drawImage(image, offx, offy, this);

            } else {
                // the image size is wrong.  try again, or give up.
                if (page != null) {
                    showPage(page);
                }
            }
        }
    }

    /**
     * Gets the size of the image currently being displayed
     *
     * @return a {@link java.awt.Dimension} object.
     */
    public Dimension getCurSize() {
        return size;
    }

    /**
     * Waits until the page is either complete or had an error.
     */
    public void waitForCurrentPage() {
        flag.waitForFlag();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles notification of the fact that some part of the image
     * changed.  Repaints that portion.
     */
    public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y, final int width, final int height) {
        if ((infoflags & (SOMEBITS | ALLBITS)) != 0) {
            repaint(x + offx, y + offy, width, height);
        }
        if ((infoflags & (ALLBITS | ERROR | ABORT)) != 0) {
            flag.set();
            return false;
        } else {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void mouseClicked(final MouseEvent e) {
        requestFocus();
    }

    /**
     * {@inheritDoc}
     */
    public void mouseEntered(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    public void mouseExited(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    public void mousePressed(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    public void mouseReleased(final MouseEvent e) {
    }

}
