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
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.loboevolution.common.Strings;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>ImgControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ImgControl extends BaseControl {

	private static final long serialVersionUID = 1L;

	private final Image image;

	private final String alt;

	private Dimension preferredSize;

	private int valign = AlignValues.BASELINE.getValue();
	
	private boolean mouseBeingPressed;
	
	/**
	 * <p>Constructor for ImgControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLImageElementImpl} object.
	 */
	public ImgControl(HTMLImageElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		alt = modelNode.getAlt() != null ? modelNode.getAlt() : "";
		image = HttpNetwork.getImage(modelNode.getSrc(), modelNode.getOwnerDocument().getBaseURI());
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

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		final Dimension ps = this.preferredSize;
		return ps == null ? new Dimension(0, 0) : ps;
	}

	/** {@inheritDoc} */
	@Override
	public int getVAlign() {
		return this.valign;
	}

	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Dimension size = this.getSize();
		final Insets insets = this.getInsets();
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

	/** {@inheritDoc} */
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
			valign = AlignValues.MIDDLE.getValue();
			break;
		case "absmiddle":
			valign = AlignValues.ABSMIDDLE.getValue();
			break;
		case "top":
			valign = AlignValues.TOP.getValue();
			break;
		case "bottom":
			valign = AlignValues.BOTTOM.getValue();
			break;
		case "baseline":
			valign = AlignValues.BASELINE.getValue();
			break;
		case "absbottom":
			valign = AlignValues.ABSBOTTOM.getValue();
			break;
		default:
			valign = AlignValues.BASELINE.getValue();
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
