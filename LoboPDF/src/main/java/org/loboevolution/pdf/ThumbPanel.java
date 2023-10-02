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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A panel of thumbnails, one for each page of a PDFFile. You can add a
 * PageChangeListener to be informed of when the user clicks one of the pages.
 */
@Slf4j
public class ThumbPanel extends JPanel implements Runnable, Scrollable, ImageObserver {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -6761217072379594185L;
    /**
     * Size of the border between images.
     */
    private final int border = 2;
    /**
     * Height of each line. Thumbnails will be scaled to this height (minus the
     * border).
     */
    private final int lineheight = 96 + border;
    /**
     * Guesstimate of the width of a thumbnail that hasn't been processed yet.
     */
    private int defaultWidth = (lineheight - border) * 4 / 3;
    /**
     * The PDFFile being displayed.
     */
    private transient PDFFile file;
    /**
     * Array of images, one per page in the file.
     */
    private transient Image[] images;
    /**
     * Array of the x locations of each of the thumbnails. Every 0 stored in
     * this array indicates the start of a new line of thumbnails.
     */
    private int[] xloc;

    /**
     * Thread that renders each thumbnail in turn.
     */
    private transient Thread anim;

    /**
     * Which thumbnail is selected, or -1 if no thumbnail selected.
     */
    private int showing = -1;

    /**
     * Which thumbnail needs to be drawn next, or -1 if the previous needy
     * thumbnail is being processed.
     */
    private int needdrawn = -1;

    /**
     * Whether the default width has been guesstimated for this PDFFile yet.
     */
    private boolean defaultNotSet = true;

    /**
     * The PageChangeListener that is listening for page changes.
     */
    private transient PageChangeListener listener;

    /**
     * Creates a new ThumbPanel based on a PDFFile. The file may be null.
     * Automatically starts rendering thumbnails for that file.
     *
     * @param file a {@link org.loboevolution.pdfview.PDFFile} object.
     */
    public ThumbPanel(final PDFFile file) {
        createAndShowGUI(file);
    }

    private void createAndShowGUI(final PDFFile file) {
        this.file = file;
        if (file != null) {
            final int count = file.getNumPages();
            images = new Image[count];
            xloc = new int[count];
            setPreferredSize(new Dimension(defaultWidth, 200));
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent evt) {
                    handleClick(evt.getX(), evt.getY());
                }
            });
            anim = new Thread(this);
            anim.setName(getClass().getName());
            anim.start();
        } else {
            images = new Image[0];
            setPreferredSize(new Dimension(defaultWidth, 200));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Renders each of the pages in the PDFFile into a thumbnail. Preferentially
     * works on the needdrawn thumbnail, otherwise, go in order.
     */
    @Override
    public void run() {
        int workingon = 0; // the thumbnail we'll be rendering next.

        while (anim == Thread.currentThread()) {

            if (needdrawn >= 0) {
                workingon = needdrawn;
                needdrawn = -1;
            }

            // find an unfinished page
            int loop;
            for (loop = images.length; loop > 0; loop--) {
                if (images[workingon] == null) {
                    break;
                }
                workingon++;
                if (workingon >= images.length) {
                    workingon = 0;
                }
            }
            if (loop == 0) {
                // done all pages.
                break;
            }

            // build the page
            try {
                final int pagetoread = workingon + 1;
                final PDFPage p = file.getPage(pagetoread, true);
                final int wid = (int) Math.ceil((lineheight - border) * p.getAspectRatio());
                final int pagetowrite = workingon;

                final Image i = p.getImage(wid, lineheight - border, null, this, true, true);
                images[pagetowrite] = i;

                if (defaultNotSet) {
                    defaultNotSet = false;
                    setDefaultWidth(wid);
                }
                repaint();
            } catch (final Exception e) {
                log.error(e.getMessage(), e);

                final int size = lineheight - border;
                images[workingon] = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_BINARY);
            }
        }
    }

    /**
     * Adds a PageChangeListener to receive notification of page clicks.
     *
     * @param pl a {@link org.loboevolution.pdf.PageChangeListener} object.
     */
    public void addPageChangeListener(final PageChangeListener pl) {
        // [[MW: should be an array list instead of only one]]
        setListener(pl);
    }

    /**
     * Removes a PageChangeListener from the notification list.
     */
    public void removePageChangeListener() {
        // [[MW: should be an array list instead of only one]]
        setListener(null);
    }

    /**
     * Stops the render thread. Be sure to call this before dropping a
     * ThumbPanel.
     */
    public void stop() {
        anim = null;
    }

    /**
     * Sets the guesstimate of the width of a thumbnail that hasn't been
     * processed yet.
     *
     * @param width the new guesstimate of the width of a thumbnail that hasn't
     *              been processed yet
     */
    public void setDefaultWidth(final int width) {
        defaultWidth = width;
        // setPreferredSize(new Dimension(width, lineheight));
    }

    /**
     * Handles a mouse click in the panel. Figures out which page was clicked,
     * and calls showPage.
     *
     * @param x the x coordinate of the mouse click
     * @param y the y coordinate of the mouse click
     */
    public void handleClick(final int x, final int y) {
        int linecount = -1;
        final int line = y / lineheight;
        // run through the thumbnail locations, counting new lines
        // until the appropriate line is reached.
        for (int i = 0; i < xloc.length; i++) {
            if (xloc[i] == 0) {
                linecount++;
            }
            if (line == linecount && xloc[i] + (images[i] != null ? images[i].getWidth(null) : defaultWidth) > x) {
                showPage(i);
                break;
            }
        }
    }

    /**
     * Sets the currently viewed page, indicates it with a highlight border, and
     * makes sure the thumbnail is visible.
     *
     * @param pagenum a int.
     */
    public void pageShown(final int pagenum) {
        if (showing != pagenum) {
            // FIND THE SELECTION RECTANGLE
            // getViewPort.scrollRectToVisible(r);
            if (pagenum >= 0 && getParent() instanceof JViewport) {
                int y = -lineheight;
                for (int i = 0; i <= pagenum; i++) {
                    if (xloc[i] == 0) {
                        y += lineheight;
                    }
                }
                final Rectangle r = new Rectangle(xloc[pagenum], y,
                        images[pagenum] == null ? defaultWidth : images[pagenum].getWidth(null), lineheight);
                scrollRectToVisible(r);
            }
            showing = pagenum;
            repaint();
        }
    }

    /**
     * Notifies the listeners that a page has been selected. Performs the
     * notification in the AWT thread. Also highlights the selected page. Does
     * this first so that feedback is immediate.
     *
     * @param pagenum a int.
     */
    public void showPage(final int pagenum) {
        pageShown(pagenum);
        SwingUtilities.invokeLater(new GotoLater(pagenum, this));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the positions of the thumbnails, and draws them to the screen.
     */
    @Override
    public void paint(final Graphics g) {
        int x = 0;
        int y = 0;
        int maxwidth = 0;
        final Rectangle clip = g.getClipBounds();
        g.setColor(Color.gray);
        final int width = getWidth();
        g.fillRect(0, 0, width, getHeight());

        for (int i = 0; i < images.length; i++) {
            // calculate the x location of the thumbnail, based on its width
            int w = defaultWidth + 2;
            if (images[i] != null) {
                w = images[i].getWidth(null) + 2;
            }
            // need a new line?
            if (x + w > width && x != 0) {
                x = 0;
                y += lineheight;
            }
            // if the thumbnail is visible, draw it.
            if (clip.intersects(new Rectangle(x, y, w, lineheight))) {
                if (images[i] != null) {
                    // thumbnail is ready.
                    g.drawImage(images[i], x + 1, y + 1, this);
                } else {
                    // thumbnail isn't ready. Remember that we need it...
                    if (needdrawn == -1) {
                        needdrawn = i;
                    }
                    // ... and draw a blank thumbnail.
                    g.setColor(Color.lightGray);
                    g.fillRect(x + 1, y + 1, w - border, lineheight - border);
                    g.setColor(Color.darkGray);
                    g.drawRect(x + 1, y + 1, w - border - 1, lineheight - border - 1);
                }
                // draw the selection highlight if needed.
                if (i == showing) {
                    g.setColor(Color.red);
                    g.drawRect(x, y, w - 1, lineheight - 1);
                    g.drawRect(x + 1, y + 1, w - 3, lineheight - 3);
                }
            }
            // save the x location of this thumbnail.
            xloc[i] = x;
            x += w;
            // remember the longest line
            if (x > maxwidth) {
                maxwidth = x;
            }
        }
        // if there weren't any thumbnails, make a default line width
        if (maxwidth == 0) {
            maxwidth = defaultWidth;
        }
        final Dimension d = getPreferredSize();
        if (d.height != y + lineheight || d.width != maxwidth) {
            setPreferredSize(new Dimension(maxwidth, y + lineheight));
            revalidate();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles notification of any image updates. Not used any more.
     */
    @Override
    public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y, final int width, final int height) {
        // if ((infoflags & ALLBITS)!=0) {
        // flag.set();
        // }
        return (infoflags & (ALLBITS | ERROR | ABORT)) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScrollableBlockIncrement(final Rectangle visrect, final int orientation, final int direction) {
        return Math.max(lineheight, visrect.height / lineheight * lineheight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScrollableUnitIncrement(final Rectangle visrect, final int orientation, final int direction) {
        return lineheight;
    }

    /**
     * <p>Getter for the field <code>listener</code>.</p>
     *
     * @return the listener
     */
    public PageChangeListener getListener() {
        return listener;
    }

    /**
     * <p>Setter for the field <code>listener</code>.</p>
     *
     * @param listener the listener to set
     */
    public void setListener(final PageChangeListener listener) {
        this.listener = listener;
    }
}
