/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.ImageObserver;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;
import org.lobobrowser.html.domimpl.ImageEvent;
import org.lobobrowser.html.domimpl.ImageListener;
import org.lobobrowser.html.style.FontValues;
import org.lobobrowser.html.style.HtmlValues;

class ImgControl extends BaseControl implements ImageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int declaredHeight;
	private int declaredWidth;

	private volatile Image image;

	// private final UserAgentContext browserContext;
	private String lastSrc;

	private Dimension preferredSize;
	private int valign = RElement.VALIGN_BASELINE;

	public ImgControl(HTMLImageElementImpl modelNode) {
		super(modelNode);
		// this.browserContext = pcontext;
		modelNode.addImageListener(this);
	}

	private final boolean checkPreferredSizeChange() {
		final Dimension newPs = createPreferredSize(this.declaredWidth, this.declaredHeight);
		final Dimension ps = this.preferredSize;
		if (ps == null) {
			return true;
		}
		if (ps.width != newPs.width || ps.height != newPs.height) {
			this.preferredSize = newPs;
			return true;
		} else {
			return false;
		}
	}

	public Dimension createPreferredSize(int dw, int dh) {
		final Image img = this.image;
		if (dw == -1) {
			if (dh != -1) {
				final int iw = img == null ? -1 : img.getWidth(this);
				final int ih = img == null ? -1 : img.getHeight(this);
				if (ih == 0) {
					dw = iw == -1 ? 0 : iw;
				} else if (iw == -1 || ih == -1) {
					dw = 0;
				} else {
					dw = dh * iw / ih;
				}
			} else {
				dw = img == null ? -1 : img.getWidth(this);
				if (dw == -1) {
					dw = 0;
				}
			}
		}
		if (dh == -1) {
			if (dw != -1) {
				final int iw = img == null ? -1 : img.getWidth(this);
				final int ih = img == null ? -1 : img.getHeight(this);
				if (iw == 0) {
					dh = ih == -1 ? 0 : ih;
				} else if (iw == -1 || ih == -1) {
					dh = 0;
				} else {
					dh = dw * ih / iw;
				}
			} else {
				dh = img == null ? -1 : img.getHeight(this);
				if (dh == -1) {
					dh = 0;
				}
			}
		}
		return new Dimension(dw, dh);
	}

	@Override
	public Dimension getPreferredSize() {
		final Dimension ps = this.preferredSize;
		return ps == null ? new Dimension(0, 0) : ps;
	}

	@Override
	public int getVAlign() {
		return this.valign;
	}

	@Override
	public void imageLoaded(ImageEvent event) {
		// Implementation of ImageListener. Invoked in a request thread most likely.
		final Image image = event.image;
		this.image = image;
		final int width = image.getWidth(this);
		final int height = image.getHeight(this);
		if (width != -1 && height != -1) {
			this.imageUpdate(image, width, height);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	public void imageUpdate(Image img, final int w, final int h) {
		EventQueue.invokeLater(() -> {
			if (!checkPreferredSizeChange()) {
				repaint();
			} else {
				ImgControl.this.ruicontrol.preferredSizeInvalidated();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, final int w, final int h) {
		if ((infoflags & ImageObserver.ALLBITS) != 0 || (infoflags & ImageObserver.FRAMEBITS) != 0) {
			EventQueue.invokeLater(() -> {
				if (!checkPreferredSizeChange()) {
					repaint();
				} else {
					ImgControl.this.ruicontrol.preferredSizeInvalidated();
				}
			});
		}
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Dimension size = this.getSize();
		final Insets insets = this.getInsets();
		synchronized (this) {
		}
		final Image image = this.image;
		if (image != null) {
			g.drawImage(image, insets.left, insets.top, size.width - insets.left - insets.right,
					size.height - insets.top - insets.bottom, this);
		} else {
			// TODO: alt
		}
	}

	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		// Expected in the GUI thread.
		final HTMLElementImpl element = this.controlElement;
		final int dw = FontValues.getPixelSize(element.getAttribute("width"), null, -1, availWidth);
		final int dh = FontValues.getPixelSize(element.getAttribute("height"), null, -1, availWidth);
		this.declaredWidth = dw;
		this.declaredHeight = dh;
		this.preferredSize = createPreferredSize(dw, dh);
		int valign;
		String alignText = element.getAttribute("align");
		if (alignText == null) {
			valign = RElement.VALIGN_BASELINE;
		} else {
			alignText = alignText.toLowerCase().trim();
			if ("middle".equals(alignText)) {
				valign = RElement.VALIGN_MIDDLE;
			} else if ("absmiddle".equals(alignText)) {
				valign = RElement.VALIGN_ABSMIDDLE;
			} else if ("top".equals(alignText)) {
				valign = RElement.VALIGN_TOP;
			} else if ("bottom".equals(alignText)) {
				valign = RElement.VALIGN_BOTTOM;
			} else if ("baseline".equals(alignText)) {
				valign = RElement.VALIGN_BASELINE;
			} else if ("absbottom".equals(alignText)) {
				valign = RElement.VALIGN_ABSBOTTOM;
			} else {
				valign = RElement.VALIGN_BASELINE;
			}
		}
		this.valign = valign;
	}

	@Override
	public String toString() {
		return "ImgControl[src=" + this.lastSrc + "]";
	}
}
