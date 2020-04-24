/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.pdf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import org.loboevolution.pdfview.PDFPage;

/**
 * A Swing-based panel that displays a PDF page image. If the zoom tool is in
 * use, allows the user to select a particular region of the image to be zoomed.
 */
public class PagePanel extends JPanel implements ImageObserver {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The image of the rendered PDF page being displayed. */
	private transient Image currentImage;

	/** The current PDFPage that was rendered into currentImage. */
	private transient PDFPage currentPage;

	/** The current xform. */
	private AffineTransform currentXform;

	/** The horizontal offset of the image from the left edge of the panel. */
	private int offx;

	/** The vertical offset of the image from the top of the panel. */
	private int offy;

	/** the current clip, in device space. */
	private transient Rectangle2D clip;

	/** the clipping region used for the image. */
	private transient Rectangle2D prevClip;

	/** the size of the image. */
	private Dimension prevSize;

	/** the zooming marquee. */
	private Rectangle zoomRect;

	/** whether the zoom tool is enabled. */
	private boolean useZoom = false;
	
	/** a flag indicating whether the current page is done or not. */
	private transient Flag flag = new Flag();

	/** x location of the mouse-down event. */
	private int downx;

	/** y location of the mouse-down event. */
	private int downy;
	
	/**
	 * Create a new PagePanel, with a default size of 800 by 600 pixels.
	 */
	public PagePanel() {
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		setPreferredSize(new Dimension(800, 600));
		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			
			/** Handles a mousePressed event */
			@Override
			public void mousePressed(MouseEvent evt) {
				downx = evt.getX();
				downy = evt.getY();
			}

			/**
			 * Handles a mouseReleased event. If zooming is turned on and there's a
			 * valid zoom rectangle, set the image clip to the zoom rect.
			 */
			@Override
			public void mouseReleased(MouseEvent evt) {
				// calculate new clip
				if (!useZoom || zoomRect == null || zoomRect.width == 0 || zoomRect.height == 0) {
					zoomRect = null;
					return;
				}

				setClip(new Rectangle2D.Double(zoomRect.x - offx, zoomRect.y - offy, zoomRect.width, zoomRect.height));

				zoomRect = null;
			}			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			/**
			 * Handles a mouseDragged event. Constrains the zoom rect to the aspect
			 * ratio of the panel unless the shift key is down.
			 */
			@Override
			public void mouseDragged(MouseEvent evt) {
				if (useZoom) {
					int x = evt.getX();
					int y = evt.getY();
					int dx = Math.abs(x - downx);
					int dy = Math.abs(y - downy);
					// constrain to the aspect ratio of the panel
					if ((evt.getModifiers() & InputEvent.SHIFT_MASK) == 0) {
						float aspect = (float) dx / (float) dy;
						float waspect = (float) getWidth() / (float) getHeight();
						if (aspect > waspect) {
							dy = (int) (dx / waspect);
						} else {
							dx = (int) (dy * waspect);
						}
					}
					if (x < downx) {
						x = downx - dx;
					}
					if (y < downy) {
						y = downy - dy;
					}
					Rectangle old = zoomRect;
					// ignore small rectangles
					if (dx < 5 || dy < 5) {
						zoomRect = null;
					} else {
						zoomRect = new Rectangle(Math.min(downx, x), Math.min(downy, y), dx, dy);
					}
					// calculate the repaint region. Should be the union of the
					// old zoom rect and the new one, with an extra pixel on the
					// bottom and right because of the way rectangles are drawn.
					if (zoomRect != null) {
						if (old != null) {
							old.add(zoomRect);
						} else {
							old = new Rectangle(zoomRect);
						}
					}
					if (old != null) {
						old.width++;
						old.height++;
					}
					if (old != null) {
						repaint(old);
					}
				}
			}
		});
	}

	/**
	 * Stop the generation of any previous page, and draw the new one.
	 *
	 * @param page
	 *            the PDFPage to draw.
	 */
	public synchronized void showPage(PDFPage page) {
		// stop drawing the previous page
		if (currentPage != null && prevSize != null) {
			currentPage.stop(prevSize.width, prevSize.height, prevClip);
		}

		// set up the new page
		currentPage = page;

		if (page == null) {
			// no page
			currentImage = null;
			clip = null;
			currentXform = null;
			repaint();
		} else {
			// start drawing -- clear the flag to indicate we're in progress.
			flag.clear();

			Dimension sz = getSize();
			if (sz.width + sz.height == 0) {
				// no image to draw.
				return;
			}

			// calculate the clipping rectangle in page space from the
			// desired clip in screen space.
			Rectangle2D useClip = clip;
			if (clip != null && currentXform != null) {
				useClip = currentXform.createTransformedShape(clip).getBounds2D();
			}

			Dimension pageSize = page.getUnstretchedSize(sz.width, sz.height, useClip);

			// get the new image
			currentImage = page.getImage(pageSize.width, pageSize.height, useClip, this);

			// calculate the transform from screen to page space
			currentXform = page.getInitialTransform(pageSize.width, pageSize.height, useClip);
			try {
				currentXform = currentXform.createInverse();
			} catch (NoninvertibleTransformException nte) {
				nte.printStackTrace();
			}

			prevClip = useClip;
			prevSize = pageSize;

			repaint();
		}
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public synchronized void flush() {
		// images.clear();
		// lruPages.clear();
		// nextPage= null;
		// nextImage= null;
	}

	/**
	 * Draw the image.
	 */
	@Override
	public void paint(Graphics g) {
		Dimension sz = getSize();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if (currentImage == null) {
			// No image -- draw an empty box
			// [[MW: remove the scary red X]]
			// g.setColor(Color.red);
			// g.drawLine(0, 0, getWidth(), getHeight());
			// g.drawLine(0, getHeight(), getWidth(), 0);
			g.setColor(Color.black);
			g.drawString("No page selected", getWidth() / 2 - 30, getHeight() / 2);
		} else {
			// draw the image
			int imwid = currentImage.getWidth(null);
			int imhgt = currentImage.getHeight(null);

			// draw it centered within the panel
			offx = (sz.width - imwid) / 2;
			offy = (sz.height - imhgt) / 2;

			if (imwid == sz.width && imhgt <= sz.height || imhgt == sz.height && imwid <= sz.width) {

				g.drawImage(currentImage, offx, offy, this);

			} else {
				// the image is bogus. try again, or give up.
				flush();
				if (currentPage != null) {
					showPage(currentPage);
				}
				g.setColor(Color.red);
				g.drawLine(0, 0, getWidth(), getHeight());
				g.drawLine(0, getHeight(), getWidth(), 0);
			}
		}
		// draw the zoomrect if there is one.
		if (zoomRect != null) {
			g.setColor(Color.red);
			g.drawRect(zoomRect.x, zoomRect.y, zoomRect.width, zoomRect.height);
		}
		// debugging: draw a rectangle around the portion that just changed.
		// g.setColor(boxColor);
		// Rectangle r= g.getClipBounds();
		// g.drawRect(r.x, r.y, r.width-1, r.height-1);
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public PDFPage getPage() {
		return currentPage;
	}

	/**
	 * Gets the cur size.
	 *
	 * @return the cur size
	 */
	public Dimension getCurSize() {
		return prevSize;
	}

	/**
	 * Gets the cur clip.
	 *
	 * @return the cur clip
	 */
	public Rectangle2D getCurClip() {
		return prevClip;
	}

	/**
	 * Waits until the page is either complete or had an error.
	 */
	public void waitForCurrentPage() {
		flag.waitForFlag();
	}

	/**
	 * Handles notification of the fact that some part of the image changed.
	 * Repaints that portion.
	 *
	 * @return true if more updates are desired.
	 */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		if ((infoflags & (SOMEBITS | ALLBITS)) != 0) {
			// [[MW: dink this rectangle by 1 to handle antialias issues]]
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
	 * Turns the zoom tool on or off. If on, mouse drags will draw the zooming
	 * marquee. If off, mouse drags are ignored.
	 */
	public void useZoomTool(boolean use) {
		useZoom = use;
	}

	/**
	 * Sets the current clip, in device space.
	 *
	 * @param clip
	 *            the new current clip, in device space
	 */
	public void setClip(Rectangle2D clip) {
		this.clip = clip;
		showPage(currentPage);
	}
}
