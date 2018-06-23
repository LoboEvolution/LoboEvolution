/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.primary.clientlets.img;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * The <code>JComponent</code> implementing an overlay.
 * 
 * @author Kazo Csaba
 */
public class OverlayComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private transient Overlay overlay;
	private transient ImageComponent theImage;

	public OverlayComponent(Overlay overlay, ImageComponent image) {
		this.overlay = overlay;
		this.theImage = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage image = theImage.getImage();
		if (image != null) {
			Graphics2D gg = (Graphics2D) g.create();
			overlay.paint(gg, image, theImage.getImageTransform());
			gg.dispose();
		}
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * @return the overlay
	 */
	public Overlay getOverlay() {
		return overlay;
	}

	/**
	 * @param overlay the overlay to set
	 */
	public void setOverlay(Overlay overlay) {
		this.overlay = overlay;
	}

	/**
	 * @return the theImage
	 */
	public ImageComponent getTheImage() {
		return theImage;
	}

	/**
	 * @param theImage the theImage to set
	 */
	public void setTheImage(ImageComponent theImage) {
		this.theImage = theImage;
	}
}
