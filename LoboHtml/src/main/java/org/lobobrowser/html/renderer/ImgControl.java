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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.lobo.common.Strings;
import org.lobo.common.WrapperLayout;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;
import org.lobobrowser.html.domimpl.ImageEvent;
import org.lobobrowser.html.domimpl.ImageListener;
import org.lobobrowser.html.renderer.HtmlController;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.style.HtmlValues;

public class ImgControl extends BaseControl implements ImageListener {

	private static final long serialVersionUID = 1L;

	private volatile Image image;

	private String alt;

	private Dimension preferredSize;

	private int valign = RElement.VALIGN_BASELINE;
	
	private boolean mouseBeingPressed;
	
	public ImgControl(HTMLImageElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		alt = modelNode.getAlt() != null ? modelNode.getAlt() : "";
		modelNode.addImageListener(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseBeingPressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseBeingPressed = false;
				repaint();
				HtmlController.getInstance().onPressed(modelNode, e, e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					HtmlController.getInstance().onContextMenu(modelNode, e, e.getX(), e.getY());
				}
			}
		});
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
		final Image image = event.image;
		this.image = image;
		if (image != null) {
			final int width = image.getWidth(this);
			final int height = image.getHeight(this);
			if (width != -1 && height != -1) {
				repaint();
			}
		}
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
			g.drawString(alt, 10, 10);
		}
		
		if (this.mouseBeingPressed) {
			final Color over = new Color(255, 100, 100, 64);
			final Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, size.width, size.height);
			} finally {
				g.setColor(oldColor);
			}
		}
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final HTMLElementImpl element = this.controlElement;
		final int dw = HtmlValues.getPixelSize(element.getAttribute("width"), null, -1, availWidth);
		final int dh = HtmlValues.getPixelSize(element.getAttribute("height"), null, -1, availHeight);
		this.preferredSize = createPreferredSize(dw, dh);
		int valign;
		String alignText = element.getAttribute("align");
		alignText = Strings.isNotBlank(alignText) ? alignText.toLowerCase().trim() : "";

		switch (alignText) {
		case "middle":
			valign = RElement.VALIGN_MIDDLE;
			break;
		case "absmiddle":
			valign = RElement.VALIGN_ABSMIDDLE;
			break;
		case "top":
			valign = RElement.VALIGN_TOP;
			break;
		case "bottom":
			valign = RElement.VALIGN_BOTTOM;
			break;
		case "baseline":
			valign = RElement.VALIGN_BASELINE;
			break;
		case "absbottom":
			valign = RElement.VALIGN_ABSBOTTOM;
			break;
		default:
			valign = RElement.VALIGN_BASELINE;
			break;
		}
		this.valign = valign;
	}

	private Dimension createPreferredSize(int dw, int dh) {
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
}
